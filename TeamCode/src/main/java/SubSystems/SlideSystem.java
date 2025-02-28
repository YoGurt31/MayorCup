package SubSystems;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDCoefficients;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

public class SlideSystem extends Subsystem {

    public static final SlideSystem INSTANCE = new SlideSystem();
    private SlideSystem() { }

    public MotorEx horizontalSlide, verticalSlide1, verticalSlide2;
    MotorGroup verticalSlides = new MotorGroup(verticalSlide1, verticalSlide2);

    public PIDFController controller = new PIDFController(new PIDCoefficients(0.005, 0.0, 0.0));

    public String horizontalSlideName = "horizontalSlide";
    public String verticalSlide1Name = "verticalSlide1";
    public String verticalSlide2Name = "verticalSlide2";

    public Command VSToScoring() {
        return new RunToPosition(verticalSlides, 850, controller, this);
    }

    public Command VSToCollection() {
        return new RunToPosition(verticalSlides, 0, controller, this);
    }

    public Command VSHold() {
        return new HoldPosition(verticalSlides, controller, this);
    }

    public Command HSToCollection() {
        return new RunToPosition(horizontalSlide, 500, controller, this);
    }

    public Command HSToBase() {
        return new RunToPosition(horizontalSlide, 0, controller, this);
    }

    public Command HSHold() {
        return new HoldPosition(horizontalSlide, controller, this);
    }

    @Override
    public void initialize() {
        verticalSlide1 = new MotorEx(verticalSlide1Name);
        verticalSlide2 = new MotorEx(verticalSlide2Name);
        horizontalSlide = new MotorEx(horizontalSlideName);
    }
}
