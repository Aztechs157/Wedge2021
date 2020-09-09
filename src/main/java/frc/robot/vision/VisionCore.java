/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.I2C;
import static frc.robot.Constants.VisionConstants.*;

/**
 * Add your docs here.
 */
public class VisionCore {
    private final Pixy2 pixy = new Pixy2(new I2C(I2C_PORT, PIXY_I2C_ADDRESS));

    public Pixy2 getPixy() {
        return pixy;
    }

    private CvSource videoSource;

    public CvSource getCameraServer() {
        if (videoSource == null) {
            videoSource = CameraServer.getInstance().putVideo("Pixy", 315, 207);
        }
        return videoSource;
    }
}
