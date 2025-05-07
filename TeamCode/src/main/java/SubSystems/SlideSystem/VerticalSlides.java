package SubSystems.SlideSystem;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

public class VerticalSlides extends Subsystem {

    public static final VerticalSlides INSTANCE = new VerticalSlides();

    private VerticalSlides() {
    }

    public MotorEx verticalSlide1, verticalSlide2;
    public MotorGroup verticalSlides;

    public PIDFController controller = new PIDFController(0.008, 0.0, 0.0025,  new StaticFeedforward(0.08));

    public String verticalSlide1Name = "verticalSlide1";
    public String verticalSlide2Name = "verticalSlide2";

    public Command VSToScoring() {
        return new RunToPosition(verticalSlides, 765, controller, this);
    }

    public Command VSToCollection() {
        return new RunToPosition(verticalSlides, 0, controller, this);
    }

    public Command VSUp() {
        return new SetPower(verticalSlides, 1.0, this);
    }

    public Command VSDown() {
        return new SetPower(verticalSlides, -1.0, this);
    }

    public Command VSNull() {
        return new SetPower(verticalSlides, 0.0, this);
    }

    public Command getDefaultCommand() {
        return new HoldPosition(verticalSlides, controller, this);
    }

    @Override
    public void initialize() {
        verticalSlide1 = new MotorEx(verticalSlide1Name);
        verticalSlide1.setDirection(DcMotorSimple.Direction.REVERSE);
        verticalSlide2 = new MotorEx(verticalSlide2Name);
        verticalSlide2.setDirection(DcMotorSimple.Direction.FORWARD);
        verticalSlides = new MotorGroup(verticalSlide1, verticalSlide2);
    }
}
