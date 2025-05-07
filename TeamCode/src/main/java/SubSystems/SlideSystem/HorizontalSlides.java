package SubSystems.SlideSystem;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.conditionals.PassiveConditionalCommand;
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDCoefficients;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

public class HorizontalSlides extends Subsystem {

    public static final HorizontalSlides INSTANCE = new HorizontalSlides();

    private HorizontalSlides() {
    }

    public MotorEx horizontalSlide;

    public PIDFController controller = new PIDFController(0.005, 0.0, 0.0,  new StaticFeedforward(0.0));

    public String horizontalSlideName = "horizontalSlide";

    public Command HSToCollection() {
        return new RunToPosition(horizontalSlide, 900, controller, this);
    }

    public Command HSToBase() {
        return new RunToPosition(horizontalSlide, 0, controller, this);
    }

    public boolean hsSwitch = true;

    public Command toggleHS() {
        return new SequentialGroup(
                new InstantCommand(() -> {
                    hsSwitch = !hsSwitch;
                    return null;
                }),
                new PassiveConditionalCommand(
                        () -> hsSwitch,
                        this::HSToCollection,
                        this::HSToBase
                )
        );
    }

    public Command getDefaultCommand() {
        return new HoldPosition(horizontalSlide, controller, this);
    }

    @Override
    public void initialize() {
        horizontalSlide = new MotorEx(horizontalSlideName);
    }
}
