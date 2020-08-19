package frc.robot.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight {
    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    // #region Get Network Values
    public boolean hasValidTargets() {
        return table.getEntry("tv").getBoolean(false);
    }

    public double getXAxis() {
        return table.getEntry("tx").getDouble(0.0);
    }

    public double getYAxis() {
        return table.getEntry("ty").getDouble(0.0);
    }

    public double getTargetArea() {
        return table.getEntry("ta").getDouble(0.0);
    }

    public double getSkew() {
        return table.getEntry("ts").getDouble(0.0);
    }

    public double getLatency() {
        return table.getEntry("tl").getDouble(0.0);
    }

    public double getShortestSideLength() {
        return table.getEntry("tshort").getDouble(0.0);
    }

    public double getLongestSideLength() {
        return table.getEntry("tlong").getDouble(0.0);
    }

    public double getHorizontalSideLength() {
        return table.getEntry("thor").getDouble(0.0);
    }

    public double getVerticalSideLength() {
        return table.getEntry("tvert").getDouble(0.0);
    }
    // #endregion

    // #region Enum Gets/Sets
    public enum LightMode {
        PipelineDefault, ForceOff, ForceBlink, ForceOn,
    }

    public LightMode getLightMode() {
        return LightMode.values()[table.getEntry("ledMode").getNumber(0.0).intValue()];
    }

    public void setLightMode(final LightMode mode) {
        table.getEntry("ledMode").setNumber(mode.ordinal());
    }

    public enum CameraMode {
        VisionProcessor, DriverCamera,
    }

    public CameraMode getCameraMode() {
        return CameraMode.values()[table.getEntry("camMode").getNumber(0.0).intValue()];
    }

    public void setCameraMode(final CameraMode mode) {
        table.getEntry("camMode").setNumber(mode.ordinal());
    }

    public enum Pipeline {
        Pipe0, Pipe1, Pipe2, Pipe3, Pipe4, Pipe5, Pipe6, Pipe7, Pipe8, Pipe9,
    }

    public Pipeline getActivePipeline() {
        return Pipeline.values()[table.getEntry("getpipe").getNumber(0.0).intValue()];
    }

    public void setActivePipeline(final Pipeline mode) {
        table.getEntry("pipeline").setNumber(mode.ordinal());
    }

    public enum StreamingMode {
        Standard, PiPMain, PiPSecondary,
    }

    public StreamingMode getStreamingMode() {
        return StreamingMode.values()[table.getEntry("stream").getNumber(0.0).intValue()];
    }

    public void setStreamingMode(final StreamingMode mode) {
        table.getEntry("stream").setNumber(mode.ordinal());
    }

    public enum SnapshotMode {
        Standard, PiPMain, PiPSecondary,
    }

    public SnapshotMode getSnapshotMode() {
        return SnapshotMode.values()[table.getEntry("snapshot").getNumber(0.0).intValue()];
    }

    public void setSnapshotMode(final SnapshotMode mode) {
        table.getEntry("snapshot").setNumber(mode.ordinal());
    }
    // #endregion
}
