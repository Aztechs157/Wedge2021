package frc.robot.util.controller;

import java.util.function.IntFunction;
import edu.wpi.first.wpilibj.Joystick;

public class ControllerModel {

    /**
     * A class representing a button definition
     */
    public static class ButtonDef {
        public final int joystickId;
        public final int buttonId;

        public ButtonDef(final int joystickId, final int buttonId) {
            this.joystickId = joystickId;
            this.buttonId = buttonId;
        }
    }

    /**
     * A class representing a axis definition
     */
    public static class AxisDef {
        public final int joystickId;
        public final int axisId;

        public AxisDef(final int joystickId, final int axisId) {
            this.joystickId = joystickId;
            this.axisId = axisId;
        }
    }

    public static final ButtonDef UNIMPLEMENTED_BUTTON = new ButtonDef(0, 99999);
    public static final AxisDef UNIMPLEMENTED_AXIS = new AxisDef(0, 99999);

    private final int[] joystickIds;

    public ControllerModel(final int... joystickIds) {
        this.joystickIds = joystickIds;
    }

    public boolean getRawButton(final IntFunction<Joystick> getJoystick, final ButtonDef def) {
        return getJoystick.apply(joystickIds[def.joystickId]).getRawButton(def.buttonId);
    }

    public double getRawAxis(final IntFunction<Joystick> getJoystick, final AxisDef def) {
        return getJoystick.apply(joystickIds[def.joystickId]).getRawAxis(def.axisId);
    }
}
