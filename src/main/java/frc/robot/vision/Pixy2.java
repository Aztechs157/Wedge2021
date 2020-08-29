/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.awt.Color;
import java.net.Authenticator.RequestorType;

import edu.wpi.first.wpilibj.I2C;

import static frc.robot.util.ShuffleTabs.tossError;
import static frc.robot.util.NumberUtil.unsign;

/**
 * Add your docs here.
 */
public class Pixy2 {
    private final I2C pixy;

    public Pixy2(final I2C pixy) {
        this.pixy = pixy;
    }

    private enum RequestResponseType {
        GetVersion(14, 15), GetResolution(12, 14), SetCameraBrightness(16, 1), SetServos(18, 1), SetLED(20, 1),
        SetLamp(22, 1), GetFPS(24, 1), GetBlocks(32, 33);

        public final byte requestOpcode;
        public final byte responseOpcode;

        private RequestResponseType(final int requestOpcode, final int responseOpcode) {
            this.requestOpcode = (byte) requestOpcode;
            this.responseOpcode = (byte) responseOpcode;
        }
    }

    private ByteBuffer createRequest(final RequestResponseType type, final int length) {
        // Create a buffer for the header
        final var HEADER_SIZE = 4;
        final var request = ByteBuffer.allocate(HEADER_SIZE + length);
        request.order(ByteOrder.LITTLE_ENDIAN);

        // Fill in the buffer
        request.putShort((short) 0xc1ae); // Magic number
        request.put((byte) type.requestOpcode); // Request opcode
        request.put((byte) length); // Length

        return request;
    }

    private ByteBuffer readResponse(final RequestResponseType type) {
        // Read just the header of the response
        final var HEADER_SIZE = 6;
        final var header = ByteBuffer.allocate(HEADER_SIZE);
        header.order(ByteOrder.LITTLE_ENDIAN);
        pixy.readOnly(header, HEADER_SIZE);

        // Check the sync value
        {
            final var sync = header.getShort();
            if (unsign(sync) != 0xc1af) {
                tossError(new Error("Pixy2: Fetched response has an invalid sync."));
            }
        }

        // Check the recevied type
        {
            final var receivedType = header.get();
            if (receivedType != type.responseOpcode) {
                tossError(new Error("Pixy2: Fetched response has a type that didn't match what was expected."));
            }
        }

        final var length = header.get();
        final var checksum = header.getShort();

        // Read the rest of the response
        final var response = ByteBuffer.allocate(length);
        response.order(ByteOrder.LITTLE_ENDIAN);
        pixy.readOnly(response, length);

        // Checksum
        {
            short sum = 0;

            // Checksum is defined as sum of all bytes of payload
            for (final var part : response.array()) {
                sum += part;
            }

            if (unsign(checksum) != sum) {
                tossError(new Error("Pixy2: Response checksum didn't match what was expected."));
            }
        }

        return response;
    }

    private ByteBuffer sendThenReceive(final ByteBuffer request, final RequestResponseType type) {
        pixy.writeBulk(request, request.capacity());
        return readResponse(type);
    }

    public static class Pixy2Version {
        public final int hardwareVersion;
        public final int firmwareVersionMajor;
        public final int firmwareVersionMinor;
        public final int firmwareBuild;
        public final String firmwareString;

        private Pixy2Version(final ByteBuffer response) {
            this.hardwareVersion = unsign(response.getShort());
            this.firmwareVersionMajor = unsign(response.get());
            this.firmwareVersionMinor = unsign(response.get());
            this.firmwareBuild = unsign(response.getShort());

            final var restBytes = new byte[response.remaining()];
            response.get(restBytes);
            this.firmwareString = new String(restBytes);
        }
    }

    public Pixy2Version getVersion() {
        final var request = createRequest(RequestResponseType.GetVersion, 0);
        final var response = sendThenReceive(request, RequestResponseType.GetVersion);

        return new Pixy2Version(response);
    }

    public static class Pixy2Resolution {
        public final int width;
        public final int height;

        private Pixy2Resolution(final ByteBuffer response) {
            this.width = response.getShort();
            this.height = response.getShort();
        }
    }

    public Pixy2Resolution getResolution() {
        final var request = createRequest(RequestResponseType.GetResolution, 1);
        request.put((byte) 0); // Unused, reserved for future pixy versions

        final var response = sendThenReceive(request, RequestResponseType.GetResolution);
        return new Pixy2Resolution(response);
    }

    public long setCameraBrightness(final short brightness) {
        final var request = createRequest(RequestResponseType.SetCameraBrightness, 1);
        request.putShort(brightness);

        final var response = sendThenReceive(request, RequestResponseType.SetCameraBrightness);
        return unsign(response.getInt());
    }

    public long setServos(final short servo1, final short servo2) {
        final var request = createRequest(RequestResponseType.SetServos, 4);
        request.putShort(servo1);
        request.putShort(servo2);

        final var response = sendThenReceive(request, RequestResponseType.SetServos);
        return unsign(response.getInt());
    }

    public long setLED(final Color color) {
        final var request = createRequest(RequestResponseType.SetLED, 3);

        request.put((byte) color.getRed());
        request.put((byte) color.getGreen());
        request.put((byte) color.getBlue());

        final var response = sendThenReceive(request, RequestResponseType.SetLED);
        return unsign(response.getInt());
    }

    public long setLamp(final boolean upper, final boolean lower) {
        final var request = createRequest(RequestResponseType.SetLamp, 2);
        request.put((byte) (upper ? 1 : 0));
        request.put((byte) (lower ? 1 : 0));

        final var response = sendThenReceive(request, RequestResponseType.SetLamp);
        return unsign(response.getInt());
    }

    public long getFPS() {
        final var request = createRequest(RequestResponseType.GetFPS, 0);
        final var response = sendThenReceive(request, RequestResponseType.GetFPS);
        return unsign(response.getInt());
    }
}
