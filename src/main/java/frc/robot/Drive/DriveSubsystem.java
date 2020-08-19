/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Drive;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;

public class DriveSubsystem extends SubsystemBase {
    private final CANSparkMax frontleft = new CANSparkMax(Constants.DriveConstants.FrontLeft);
    private final CANSparkMax frontright = new CANSparkMax(Constants.DriveConstants.FrontRight);
    private final CANSparkMax backleft = new CANSparkMax(Constants.DriveConstants.BackLeft);
    private final CANSparkMax backright = new CANSparkMax(Constants.DriveConstants.BackRight);

    /**
     * Creates a new DriveSubsystem.
     */
    public DriveSubsystem() {

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
