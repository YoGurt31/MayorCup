package SubSystems.IntakeSystem;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class IntakePosition extends Subsystem {

    public static final IntakePosition INSTANCE = new IntakePosition();

    private IntakePosition() {
    }

    public Servo intakePosition;
    public String intakePositionName = "intakePosition";

    public Command IntakePositionUp() {
        return new ServoToPosition(intakePosition, 1.0, this);
    }

    public Command IntakePositionDown() {
        return new ServoToPosition(intakePosition, 0.55, this);
    }

    @Override
    public void initialize() {
        intakePosition = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, intakePositionName);
    }
}
