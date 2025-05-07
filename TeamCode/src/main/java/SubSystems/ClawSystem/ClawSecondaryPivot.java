package SubSystems.ClawSystem;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class ClawSecondaryPivot extends Subsystem {

    public static final ClawSecondaryPivot INSTANCE = new ClawSecondaryPivot();
    private ClawSecondaryPivot() { }

    public Servo clawSecondaryPivot;
    public String clawSecondaryPivotName = "clawSecondaryPivot";

    public Command SecondaryPivotToScoring() {
        return new ServoToPosition(clawSecondaryPivot, 0.85, this);
    }

    public Command SecondaryPivotAuton() {
        return new ServoToPosition(clawSecondaryPivot, 0.9, this);
    }

    public Command SecondaryPivotToCollection() {
        return new ServoToPosition(clawSecondaryPivot, 0.00, this);
    }

    @Override
    public void initialize() {
        clawSecondaryPivot = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, clawSecondaryPivotName);
    }
}
