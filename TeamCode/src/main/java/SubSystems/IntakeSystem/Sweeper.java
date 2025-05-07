package SubSystems.IntakeSystem;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class Sweeper extends Subsystem {

    public static final Sweeper INSTANCE = new Sweeper();

    private Sweeper() {
    }

    public Servo sweeper;
    public String sweeperName = "sweeper";

    public Command SweeperUp() {
        return new ServoToPosition(sweeper, 0.00, this);
    }

    public Command SweeperMid() {
        return new ServoToPosition(sweeper, 0.50, this);
    }

    public Command SweeperDown() {
        return new ServoToPosition(sweeper, 0.75, this);
    }

    @Override
    public void initialize() {
        sweeper = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, sweeperName);
    }
}
