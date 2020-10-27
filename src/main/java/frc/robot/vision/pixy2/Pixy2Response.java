/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision.pixy2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import edu.wpi.first.wpilibj.I2C;
import frc.robot.vision.pixy2.Pixy2.RequestResponseType;

import static frc.robot.util.ShuffleTabs.tossError;
import static frc.robot.util.NumberUtil.unsign;

/**
 * Add your docs here.
 */
public class Pixy2Response {
    private static final int RESPONSE_HEADER_SIZE = 6;
    private static final int RESPONSE_SYNC = 0xc1af;
    private static final int MAX_UNSIGNED_SHORT = 0xffff;

    public final ByteBuffer buffer;

    public Pixy2Response(final I2C pixy, final RequestResponseType type) {
        // Read just the header of the response
        final var header = ByteBuffer.allocate(RESPONSE_HEADER_SIZE);
        header.order(ByteOrder.LITTLE_ENDIAN);
        pixy.readOnly(header, RESPONSE_HEADER_SIZE);

        // Check the sync value
        {
            final var sync = unsign(header.getShort());
            if (sync != RESPONSE_SYNC) {
                tossError(new Error("Pixy2: Fetched response has an invalid sync (expected: " + RESPONSE_SYNC + ", got:"
                        + sync + ")"));
            }
        }

        // Check the received type
        {
            final var receivedType = unsign(header.get());
            if (receivedType != type.responseOpcode) {
                tossError(new Error("Pixy2: Fetched response type didn't match (expected: " + type.responseOpcode
                        + ", got:" + receivedType + ")"));
            }
        }

        final var length = unsign(header.get());
        final var checksum = unsign(header.getShort());

        // Read the rest of the response
        buffer = ByteBuffer.allocate(length);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // If no payload then don't bother reading/checksum-ing
        if (length != 0) {
            pixy.readOnly(buffer, length);

            // Checksum
            var sum = 0;

            // Checksum is defined as sum of all bytes of payload
            for (final var part : buffer.array()) {
                sum += unsign(part);
                sum %= MAX_UNSIGNED_SHORT;
            }

            if (checksum != sum) {
                tossError(new Error(
                        "Pixy2: Response checksum didn't match (expected: " + checksum + ", got:" + sum + ")"));
            }

        }
    }

    public int readByte() {
        return unsign(buffer.get());
    }

    public int readShort() {
        return unsign(buffer.getShort());
    }

    public long readInt() {
        return unsign(buffer.getInt());
    }
}
