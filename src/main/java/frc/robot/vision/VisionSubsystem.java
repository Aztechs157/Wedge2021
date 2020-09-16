/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.awt.Color;
import java.util.Arrays;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import frc.robot.util.ShuffleTabs;
import frc.robot.vision.Pixy2.Pixy2Block;

public class VisionSubsystem extends SubsystemBase {
    private final VisionCore vision;

    private final SendableChooser<Color> colorChooser = new SendableChooser<>();

    private boolean enableFrames = false;

    public void enableFrames() {
        enableFrames = true;
    }

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

        setDefaultCommand(new RunCommand(() -> {
            if (!enableFrames) {
                return;
            }

            final var blocks = vision.getPixy().getBlocks((byte) 0b00000001, (byte) 255);

            renderFrame(blocks, 50);
        }, this));
    }

    public void updateLED() {
        vision.getPixy().setLED(colorChooser.getSelected());
    }

    public void printBlocks() {
        final var blocks = vision.getPixy().getBlocks((byte) 0b00000001, (byte) 255);

        for (final var block : blocks) {
            System.out.println("Block:");
            System.out.println("|  width:" + block.width);
            System.out.println("|  height:" + block.height);
        }
    }

    private int frameCounter;

    public void renderFrame(final Pixy2Block[] blocks, final int cyclesPerFrame) {

        if (frameCounter++ < cyclesPerFrame) {
            return;
        }

        frameCounter = 0;
        Arrays.sort(blocks);

        final var videoSource = vision.getVideoSource();
        final var image = new Mat(new Size(315, 207), CvType.CV_8UC3);
        image.setTo(new Scalar(255, 255, 255));

        for (final var block : blocks) {
            final var topLeft = new Point(block.centerXAxis - block.width / 2, block.centerYAxis - block.height / 2);
            final var bottomRight = new Point(block.centerXAxis + block.width / 2,
                    block.centerYAxis + block.height / 2);

            Imgproc.rectangle(image, topLeft, bottomRight, new Scalar(0, 0, 255));
        }

        videoSource.putFrame(image);
    }
}
