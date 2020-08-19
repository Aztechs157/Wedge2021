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
public class Pixy {
    private final I2C pixy;

    public Pixy(final I2C pixy) {
        this.pixy = pixy;
    }

    public void setLED(final Color color) {
        var buffer = ByteBuffer.allocate(6);
        buffer.putShort((short) 0xc1ae); // Request header
        buffer.put((byte) 20); // setLED opcode
        buffer.put((byte) 3); // Length
        buffer.put((byte) color.getRed());
        buffer.put((byte) color.getGreen());
        buffer.put((byte) color.getBlue());

        pixy.writeBulk(buffer, buffer.capacity());
    }
}
