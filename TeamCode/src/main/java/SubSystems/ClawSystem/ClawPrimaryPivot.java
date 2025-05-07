package SubSystems.ClawSystem;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class ClawPrimaryPivot extends Subsystem {

    public static final ClawPrimaryPivot INSTANCE = new ClawPrimaryPivot();
    private ClawPrimaryPivot() { }

    public Servo clawPrimaryPivot;
    public String clawPrimaryPivotName = "clawPrimaryPivot";

    public Command PrimaryPivotToScoring() {
        return new ServoToPosition(clawPrimaryPivot, 0.9, this);
    }

    public Command PrimaryPivotToCollection() {
        return new ServoToPosition(clawPrimaryPivot, 0.00, this);
    }

    @Override
    public void initialize() {
        clawPrimaryPivot = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, clawPrimaryPivotName);
    }
}
