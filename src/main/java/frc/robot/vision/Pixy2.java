/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import java.nio.ByteBuffer;
import java.util.Arrays;
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

    public void setLED(final Color color) {
        var request = ByteBuffer.allocate(7);
        request.putShort((short) 0xaec1); // Request header
        request.put((byte) 20); // setLED opcode
        request.put((byte) 3); // Length
        request.put((byte) color.getRed());
        request.put((byte) color.getGreen());
        request.put((byte) color.getBlue());

        pixy.writeBulk(request, request.capacity());

        var responce = ByteBuffer.allocate(10);

        pixy.readOnly(responce, 10);
    }
}
