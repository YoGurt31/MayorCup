package SubSystems.IntakeSystem;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

public class IntakeRollers extends Subsystem {

    public static final IntakeRollers INSTANCE = new IntakeRollers();

    private IntakeRollers() {
    }

    public MotorEx intakeRollers;
    public String intakeRollersName = "intakeRollers";

    public Command Active() {
        return new SetPower(intakeRollers, -0.80);
    }

    public Command Idle() {
        return new SetPower(intakeRollers, 0.00);
    }

    public Command Reverse() {
        return new SetPower(intakeRollers, 0.80);
    }

    @Override
    public void initialize() {
        intakeRollers = new MotorEx(intakeRollersName);
    }
}
