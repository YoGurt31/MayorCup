package SubSystems;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToSeperatePositions;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

public class IntakeSystem extends Subsystem {

    public static final IntakeSystem INSTANCE = new IntakeSystem();
    private IntakeSystem() { }

    public MotorEx intakeRollers;
    public String intakeRollersName = "intakeRollers";

    public Servo intakePivot, sweeper;
    public String intakePivotName = "intakePivot";
    public String sweeperName = "sweeper";

    public ColorSensor colorSensor;
    public String colorSensorName = "colorSensor";

    public Command Active() {
        return new SetPower(intakeRollers, 1.00);
    }

    public Command Idle() {
        return new SetPower(intakeRollers, 0.00);
    }

    public Command IntakeUp() {
        return new ServoToPosition(intakePivot, 0.00, this);
    }

    public Command IntakeDown() {
        return new ServoToPosition(intakePivot, 0.40, this);
    }

    public Command SweeperUp() {
        return new ServoToPosition(sweeper, 0.00, this);
    }

    public Command SweeperDown() {
        return new ServoToPosition(sweeper, 0.85, this);
    }

    @Override
    public void initialize() {
        intakeRollers = new MotorEx(intakeRollersName);
        intakePivot = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, intakePivotName);
        sweeper = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, sweeperName);
        colorSensor = OpModeData.INSTANCE.getHardwareMap().get(ColorSensor.class, colorSensorName);
    }
}
