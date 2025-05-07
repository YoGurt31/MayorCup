package SubSystems.ClawSystem;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.conditionals.PassiveConditionalCommand;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;

import org.firstinspires.ftc.robotcore.external.StateMachine;

import SubSystems.SlideSystem.HorizontalSlides;

public class ClawState extends Subsystem {

    public static final ClawState INSTANCE = new ClawState();

    private ClawState() {
    }

    public Servo clawState;
    public String clawStateName = "clawState";

    public Command ClawOpen() {
        return new ServoToPosition(clawState, 0.42, this);
    }

    public Command ClawClose() {
        return new ServoToPosition(clawState, 0.62, this);
    }

    public boolean ClawSwitch = true;

    public Command toggleClaw() {
        return new SequentialGroup(
                new InstantCommand(() -> {
                    ClawSwitch = !ClawSwitch;
                    return null;
                }),
                new PassiveConditionalCommand(
                        () -> ClawSwitch,
                        this::ClawClose,
                        this::ClawOpen
                )
        );
    }

    @Override
    public void initialize() {
        clawState = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, clawStateName);
    }
}
