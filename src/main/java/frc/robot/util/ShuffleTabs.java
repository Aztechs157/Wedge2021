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

    /**
     * @return If we are in debug mode or not
     */
    public static boolean isDebugMode() {
        // If debug switch is on and not connected to a field
        return debugModeEntry.getBoolean(false) && (!DriverStation.getInstance().isFMSAttached());
    }

    public static void tossError(final Error error) {
        if (isDebugMode()) {
            throw error;
        } else {
            DriverStation.reportWarning(error.toString(), false);
        }
    }

    private static final ArrayList<Runnable> debugInitCallbacks = new ArrayList<>();

    /**
     * Register a callback for the first time debug mode is switched on
     *
     * @param callback The callback to register
     */
    public static void onDebugInit(final Runnable callback) {
        debugInitCallbacks.add(callback);
    }

    private static boolean hasInitedAlready = false;

    public static void check() {
        // Don't init more then once
        if (hasInitedAlready) {
            return;
        }

        // Check conditions
        hasInitedAlready = isDebugMode();

        // Run callbacks if check passed
        if (hasInitedAlready) {
            for (var callback : debugInitCallbacks) {
                callback.run();
            }
        }
    }
}
