/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import java.nio.ByteBuffer;
import java.awt.Color;

import edu.wpi.first.wpilibj.I2C;

import static frc.robot.util.ShuffleTabs.tossError;

/**
 * Add your docs here.
 */
public class Pixy2 {
    private final I2C pixy;

    public Pixy2(final I2C pixy) {
        this.pixy = pixy;
    }

    private enum RequestResponseType {
        SetLED(20, 1);

        public final byte requestOpcode;
        public final byte responseOpcode;

        private RequestResponseType(final int requestOpcode, final int responseOpcode) {
            this.requestOpcode = (byte) requestOpcode;
            this.responseOpcode = (byte) responseOpcode;
        }
    }

    private ByteBuffer createRequest(final RequestResponseType type, final int length) {
        final var HEADER_SIZE = 4;
        final var request = ByteBuffer.allocate(HEADER_SIZE + length);
        request.putShort((short) 0xaec1); // Magic number
        request.put((byte) type.requestOpcode); // Request opcode
        request.put((byte) length); // Length
        return request;
    }

    private ByteBuffer fetchResponse(final RequestResponseType type) {
        final var HEADER_SIZE = 6;
        final var header = ByteBuffer.allocate(HEADER_SIZE);
        pixy.readOnly(header, HEADER_SIZE);

        final var sync = header.getShort();
        if (sync != 0xafc1) {
            tossError(new Error("Pixy2: Fetched response has an invalid sync."));
        }

        final var receivedType = header.get();
        if (receivedType != type.responseOpcode) {
            tossError(new Error("Pixy2: Fetched response has a type that didn't match what was expected."));
        }

        final var length = header.get();
        final var checksum = header.getShort();

        final var response = ByteBuffer.allocate(length);
        pixy.readOnly(response, length);

        return response;
    }

    public void setLED(final Color color) {
        final var request = createRequest(RequestResponseType.SetLED, 3);
        request.put((byte) color.getRed());
        request.put((byte) color.getGreen());
        request.put((byte) color.getBlue());

        pixy.writeBulk(request, request.capacity());

        final var response = fetchResponse(RequestResponseType.SetLED);

        System.out.println(response.getInt());
    }
}
