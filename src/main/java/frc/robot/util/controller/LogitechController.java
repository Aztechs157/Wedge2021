package frc.robot.util.controller;

public class LogitechController extends ControllerModel {

    public LogitechController(final int port) {
        super(port);
    }

    // #region Button defs
    public static final ButtonDef A = new ButtonDef(0, 1);
    public static final ButtonDef B = new ButtonDef(0, 2);
    public static final ButtonDef X = new ButtonDef(0, 3);
    public static final ButtonDef Y = new ButtonDef(0, 4);

    public static final ButtonDef LEFT_BUMPER = new ButtonDef(0, 5);
    public static final ButtonDef RIGHT_BUMPER = new ButtonDef(0, 6);

    public static final ButtonDef BACK = new ButtonDef(0, 7);
    public static final ButtonDef START = new ButtonDef(0, 8);

    public static final ButtonDef LEFT_STICK_PUSH = new ButtonDef(0, 9);
    public static final ButtonDef RIGHT_STICK_PUSH = new ButtonDef(0, 10);
    // #endregion

    // #region Axis defs
    public static final AxisDef LEFT_STICK_X = new AxisDef(0, 0);
    public static final AxisDef LEFT_STICK_Y = new AxisDef(0, 1);

    public static final AxisDef LEFT_TRIGGER_HELD = new AxisDef(0, 2);
    public static final AxisDef RIGHT_TRIGGER_HELD = new AxisDef(0, 3);

    public static final AxisDef RIGHT_STICK_X = new AxisDef(0, 4);
    public static final AxisDef RIGHT_STICK_Y = new AxisDef(0, 5);
    // #endregion
}
