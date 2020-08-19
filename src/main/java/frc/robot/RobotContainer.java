/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

import static frc.robot.Constants.ControllerConstants.*;
import frc.robot.colorwheel.ColorWheelCore;
import frc.robot.colorwheel.ColorWheelSubsystem;
import frc.robot.util.controller.ControllerSet;
import frc.robot.util.controller.LogitechController;
import frc.robot.vision.VisionCore;
import frc.robot.vision.VisionSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    private final ControllerSet controllerSet = new ControllerSet(new LogitechController(CONTROLLER_ID_ALPHA),
            new LogitechController(CONTROLLER_ID_BRAVO));

    private final ColorWheelSubsystem colorWheel = new ColorWheelSubsystem(new ColorWheelCore());
    private final VisionSubsystem vision = new VisionSubsystem(new VisionCore());

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        controllerSet.useButton(LogitechController.A, LogitechController.B).whenPressed(colorWheel::toggle, colorWheel);
        controllerSet.useButton(LogitechController.Y).whenPressed(vision::updateLED, vision);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return null;
    }
}
