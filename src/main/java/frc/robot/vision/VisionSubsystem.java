/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.awt.Color;

import frc.robot.util.ShuffleTabs;

public class VisionSubsystem extends SubsystemBase {
    private final VisionCore vision;

    private final SendableChooser<Color> colorChooser = new SendableChooser<>();

    /**
     * Creates a new VisionSubsystem.
     */
    public VisionSubsystem(final VisionCore vision) {
        this.vision = vision;

        colorChooser.setDefaultOption("Black", Color.BLACK);
        colorChooser.addOption("Red", Color.RED);
        colorChooser.addOption("Green", Color.GREEN);
        colorChooser.addOption("Blue", Color.BLUE);

        ShuffleTabs.onDebugInit(() -> {
            ShuffleTabs.PROGRAMER.add("LED Color", colorChooser).withWidget(BuiltInWidgets.kSplitButtonChooser);
        });
    }

    public void updateLED() {
        vision.getPixy().setLED(colorChooser.getSelected());
    }
}
