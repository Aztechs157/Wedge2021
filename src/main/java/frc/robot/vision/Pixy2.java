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

/**
 * Add your docs here.
 */
public class Pixy2 {
    private final I2C pixy;

    public Pixy2(final I2C pixy) {
        this.pixy = pixy;
    }

    public enum RequestType {
        SetLED(20);

        public final byte opcode;

        private RequestType(final int opcode) {
            this.opcode = (byte) opcode;
        }
    }

    public ByteBuffer createHeader(final RequestType type, final int length) {
        var request = ByteBuffer.allocate(length);
        request.putShort((short) 0xaec1); // Magic number
        request.put((byte) type.opcode); // Request opcode
        request.put((byte) length); // Length
        return request;
    }

    public void setLED(final Color color) {
        var request = createHeader(RequestType.SetLED, 3);
        request.put((byte) color.getRed());
        request.put((byte) color.getGreen());
        request.put((byte) color.getBlue());

        pixy.writeBulk(request, request.capacity());

        var response = ByteBuffer.allocate(10);

        pixy.readOnly(response, 10);
    }
}
