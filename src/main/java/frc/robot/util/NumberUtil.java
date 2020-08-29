/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

/**
 * Add your docs here.
 */
public class NumberUtil {
    public static int unsign(final byte num) {
        if (num < 0) {
            return (1 << Byte.SIZE) + num;
        } else {
            return num;
        }
    }

    public static int unsign(final short num) {
        if (num < 0) {
            return (1 << Short.SIZE) + num;
        } else {
            return num;
        }
    }

    public static long unsign(final int num) {
        if (num < 0) {
            return (1 << Integer.SIZE) + num;
        } else {
            return num;
        }
    }
}
