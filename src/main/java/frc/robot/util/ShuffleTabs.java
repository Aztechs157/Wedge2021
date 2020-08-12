/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * A barrel class to organize shuffleboard tabs
 */
public class ShuffleTabs {

    public static final ShuffleboardTab DRIVER = Shuffleboard.getTab("Driver");
    public static final ShuffleboardTab PROGRAMER = Shuffleboard.getTab("Programer");
    public static final ShuffleboardTab ROBOT_CONFIG = Shuffleboard.getTab("Robot Config");

    private static final NetworkTableEntry debugModeEntry = ShuffleTabs.ROBOT_CONFIG.add("Debug Mode", false)
            .withWidget(BuiltInWidgets.kToggleSwitch).getEntry();

    private static final ArrayList<Runnable> debugCallbackList = new ArrayList<>();

    public static void ifDebug(final Runnable func) {
        debugCallbackList.add(func);
    }

    private static boolean shouldCheckForTrue = false;

    public static void check() {
        var currentStatus = debugModeEntry.getBoolean(false) || DriverStation.getInstance().isFMSAttached();

        // Run code on the first true, and ignore any trues after it
        if (shouldCheckForTrue && currentStatus) {
            for (var func : debugCallbackList) {
                func.run();
            }
            shouldCheckForTrue = false;
        }
        // If we hit a single false, start looking for trues again
        else if (!currentStatus) {
            shouldCheckForTrue = true;
        }
    }
}
