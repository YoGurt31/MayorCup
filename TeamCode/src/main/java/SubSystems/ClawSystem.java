package SubSystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToSeperatePositions;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class ClawSystem extends Subsystem {

    public static final ClawSystem INSTANCE = new ClawSystem();
    private ClawSystem() { }

    public Servo clawPrimaryPivot, clawSecondaryPivot, clawState;

    public String clawPrimaryPivotName = "clawPrimaryPivot";
    public String clawSecondaryPivotName = "clawSecondaryPivot";
    public String clawStateName = "clawState";

    public Command ClawOpen() {
        return new ServoToPosition(clawState, 0.425, this);
    }

    public Command ClawClose() {
        return new ServoToPosition(clawState, 0.60, this);
    }

    public Command PrimaryPivotToScoring() {
        return new ServoToPosition(clawPrimaryPivot, 1.00, this);
    }

    public Command SecondaryPivotToScoring() {
        return new ServoToPosition(clawSecondaryPivot, 1.00, this);
    }

    public Command PrimaryPivotToCollection() {
        return new ServoToPosition(clawPrimaryPivot, 0.00, this);
    }

    public Command SecondaryPivotToCollection() {
        return new ServoToPosition(clawSecondaryPivot, 0.075, this);
    }

    @Override
    public void initialize() {
        clawPrimaryPivot = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, clawPrimaryPivotName);
        clawSecondaryPivot = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, clawSecondaryPivotName);
        clawState = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, clawStateName);
    }
}
