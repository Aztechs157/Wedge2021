package frc.robot.util.controller;

public class PlaneController extends ControllerModel {

    public PlaneController(final int port1, final int port2) {
        super(port1, port2);
    }

    // #region Left hand button defs
    public static final ButtonDef LEFT_HAND_BUMPER = new ButtonDef(0, 1);

    public static final ButtonDef LEFT_HAND_MID_DOWN = new ButtonDef(0, 2);
    public static final ButtonDef LEFT_HAND_MID_UP = new ButtonDef(0, 3);
    public static final ButtonDef LEFT_HAND_MID_LEFT = new ButtonDef(0, 4);
    public static final ButtonDef LEFT_HAND_MID_RIGHT = new ButtonDef(0, 5);

    public static final ButtonDef LEFT_HAND_TOP_LEFT = new ButtonDef(0, 6);
    public static final ButtonDef LEFT_HAND_BOTTOM_LEFT = new ButtonDef(0, 7);

    public static final ButtonDef LEFT_HAND_FAR_BOTTOM_LEFT = new ButtonDef(0, 8);
    public static final ButtonDef LEFT_HAND_FAR_BOTTOM_RIGHT = new ButtonDef(0, 9);

    public static final ButtonDef LEFT_HAND_BOTTOM_RIGHT = new ButtonDef(0, 10);
    public static final ButtonDef LEFT_HAND_TOP_RIGHT = new ButtonDef(0, 11);
    // #endregion

    // #region Axis defs
    // TODO: Fix axis ids and add missing ones when we get back
    public static final AxisDef LEFT_HAND_STICK_X = new AxisDef(0, 0);
    public static final AxisDef LEFT_HAND_STICK_Y = new AxisDef(0, 1);
    public static final AxisDef LEFT_HAND_TRIGGER_HELD = new AxisDef(0, 3);

    public static final AxisDef RIGHT_HAND_STICK_X = new AxisDef(1, 0);
    public static final AxisDef RIGHT_HAND_STICK_Y = new AxisDef(1, 1);
    public static final AxisDef RIGHT_HAND_TRIGGER_HELD = new AxisDef(1, 3);
    // #endregion

    // #region Right hand button defs
    public static final ButtonDef RIGHT_HAND_BUMPER = new ButtonDef(1, 1);

    public static final ButtonDef RIGHT_HAND_MID_DOWN = new ButtonDef(1, 2);
    public static final ButtonDef RIGHT_HAND_MID_UP = new ButtonDef(1, 3);
    public static final ButtonDef RIGHT_HAND_MID_LEFT = new ButtonDef(1, 4);
    public static final ButtonDef RIGHT_HAND_MID_RIGHT = new ButtonDef(1, 5);

    public static final ButtonDef RIGHT_HAND_TOP_LEFT = new ButtonDef(1, 6);
    public static final ButtonDef RIGHT_HAND_BOTTOM_LEFT = new ButtonDef(1, 7);

    public static final ButtonDef RIGHT_HAND_FAR_BOTTOM_LEFT = new ButtonDef(1, 8);
    public static final ButtonDef RIGHT_HAND_FAR_BOTTOM_RIGHT = new ButtonDef(1, 9);

    public static final ButtonDef RIGHT_HAND_BOTTOM_RIGHT = new ButtonDef(1, 10);
    public static final ButtonDef RIGHT_HAND_TOP_RIGHT = new ButtonDef(1, 11);
    // #endregion
}
