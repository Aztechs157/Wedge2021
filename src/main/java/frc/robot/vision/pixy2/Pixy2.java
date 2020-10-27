/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision.pixy2;

import java.awt.Color;

import edu.wpi.first.wpilibj.I2C;

/**
 * Add your docs here.
 */
public class Pixy2 {

    private static final int BYTES_PER_BLOCK = 14;

    private final I2C pixy;

    public Pixy2(final I2C pixy) {
        this.pixy = pixy;
    }

    public enum RequestResponseType {
        GetVersion(14, 15), GetResolution(12, 14), SetCameraBrightness(16, 1), SetServos(18, 1), SetLED(20, 1),
        SetLamp(22, 1), GetFPS(24, 1), GetBlocks(32, 33);

        public final byte requestOpcode;
        public final byte responseOpcode;

        private RequestResponseType(final int requestOpcode, final int responseOpcode) {
            this.requestOpcode = (byte) requestOpcode;
            this.responseOpcode = (byte) responseOpcode;
        }
    }

    private Pixy2Request createRequest(final RequestResponseType type, final int length) {
        return new Pixy2Request(pixy, type, length);
    }

    public static class Pixy2Version {
        public final int hardwareVersion;
        public final int firmwareVersionMajor;
        public final int firmwareVersionMinor;
        public final int firmwareBuild;
        public final String firmwareString;

        private Pixy2Version(final Pixy2Response response) {
            this.hardwareVersion = response.readShort();
            this.firmwareVersionMajor = response.readByte();
            this.firmwareVersionMinor = response.readByte();
            this.firmwareBuild = response.readShort();

            final var restBytes = new byte[response.buffer.remaining()];
            response.buffer.get(restBytes);
            this.firmwareString = new String(restBytes);
        }
    }

    public Pixy2Version getVersion() {
        final var request = createRequest(RequestResponseType.GetVersion, 0);
        final var response = request.send();

        return new Pixy2Version(response);
    }

    public static class Pixy2Resolution {
        public final int width;
        public final int height;

        private Pixy2Resolution(final Pixy2Response response) {
            this.width = response.readShort();
            this.height = response.readShort();
        }
    }

    public Pixy2Resolution getResolution() {
        final var request = createRequest(RequestResponseType.GetResolution, 1);
        request.buffer.put((byte) 0); // Unused, reserved for future pixy versions

        final var response = request.send();
        return new Pixy2Resolution(response);
    }

    public long setCameraBrightness(final short brightness) {
        final var request = createRequest(RequestResponseType.SetCameraBrightness, 1);
        request.buffer.putShort(brightness);

        final var response = request.send();
        return response.readShort();
    }

    public long setServos(final short servo1, final short servo2) {
        final var request = createRequest(RequestResponseType.SetServos, 4);
        request.buffer.putShort(servo1);
        request.buffer.putShort(servo2);

        final var response = request.send();
        return response.readShort();
    }

    public long setLED(final Color color) {
        final var request = createRequest(RequestResponseType.SetLED, 3);

        request.buffer.put((byte) color.getRed());
        request.buffer.put((byte) color.getGreen());
        request.buffer.put((byte) color.getBlue());

        final var response = request.send();
        return response.readShort();
    }

    public long setLamp(final boolean upper, final boolean lower) {
        final var request = createRequest(RequestResponseType.SetLamp, 2);
        request.buffer.put((byte) (upper ? 1 : 0));
        request.buffer.put((byte) (lower ? 1 : 0));

        final var response = request.send();
        return response.readShort();
    }

    public long getFPS() {
        final var request = createRequest(RequestResponseType.GetFPS, 0);
        final var response = request.send();
        return response.readShort();
    }

    public class Pixy2Block implements Comparable<Pixy2Block> {
        public final int colorCode;
        public final int centerXAxis;
        public final int centerYAxis;
        public final int width;
        public final int height;
        public final int angle;
        public final int trackingIndex;
        public final int age;

        private Pixy2Block(final Pixy2Response response) {
            this.colorCode = response.readShort();
            this.centerXAxis = response.readShort();
            this.centerYAxis = response.readShort();
            this.width = response.readShort();
            this.height = response.readShort();
            this.angle = response.readShort();
            this.trackingIndex = response.readByte();
            this.age = response.readByte();
        }

        @Override
        public int compareTo(final Pixy2Block other) {
            return Integer.valueOf(this.width * this.height).compareTo(other.width * other.height);
        }
    }

    public Pixy2Block[] getBlocks(final byte signatureBitField, final byte maxReturnBlocks) {
        final var request = createRequest(RequestResponseType.GetBlocks, 2);
        request.buffer.put(signatureBitField);
        request.buffer.put(maxReturnBlocks);

        final var response = request.send();

        final var numberOfBlocks = response.buffer.remaining() / BYTES_PER_BLOCK;
        final var blocks = new Pixy2Block[numberOfBlocks];

        for (var currentBlock = 0; currentBlock < numberOfBlocks; currentBlock++) {
            blocks[currentBlock] = new Pixy2Block(response);
        }

        return blocks;
    }
}
