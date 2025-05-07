package SubSystems.IntakeSystem;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class IntakePivot extends Subsystem {

    public static final IntakePivot INSTANCE = new IntakePivot();

    private IntakePivot() {
    }

    public Servo intakePivot;
    public String intakePivotName = "intakePivot";

    public Command IntakeRotateUp() {
        return new ServoToPosition(intakePivot, 0.80, this);
    }

    public Command IntakeRotateDown() {
        return new ServoToPosition(intakePivot, 0.30, this);
    }

    @Override
    public void initialize() {
        intakePivot = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, intakePivotName);
    }
}
