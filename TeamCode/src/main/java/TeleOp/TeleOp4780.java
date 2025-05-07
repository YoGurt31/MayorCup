// Coded By Gurtej Singh

package TeleOp;

import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import SubSystems.ClawSystem.ClawPrimaryPivot;
import SubSystems.ClawSystem.ClawSecondaryPivot;
import SubSystems.ClawSystem.ClawState;

import SubSystems.IntakeSystem.IntakeColor;
import SubSystems.IntakeSystem.IntakePivot;
import SubSystems.IntakeSystem.IntakePosition;
import SubSystems.IntakeSystem.IntakeRollers;
import SubSystems.IntakeSystem.Sweeper;

import SubSystems.SlideSystem.HorizontalSlides;
import SubSystems.SlideSystem.VerticalSlides;

@TeleOp(name = "TeleOp")
public class TeleOp4780 extends NextFTCOpMode {

    public TeleOp4780() {
        super(
                ClawPrimaryPivot.INSTANCE,
                ClawSecondaryPivot.INSTANCE,
                ClawState.INSTANCE,
                IntakeColor.INSTANCE,
                IntakePivot.INSTANCE,
                IntakePosition.INSTANCE,
                IntakeRollers.INSTANCE,
                Sweeper.INSTANCE,
                HorizontalSlides.INSTANCE,
                VerticalSlides.INSTANCE
        );
    }

    public MotorEx frontLeft, frontRight, backLeft, backRight;
    public MotorEx[] DriveMotors;
    public Command DriverControl;

    @Override
    public void onInit() {
        frontLeft = new MotorEx("frontLeft");
        frontRight = new MotorEx("frontRight");
        backLeft = new MotorEx("backLeft");
        backRight = new MotorEx("backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        DriveMotors = new MotorEx[]{frontLeft, frontRight, backLeft, backRight};

        OpModeData.INSTANCE.setTelemetry(telemetry);
    }

    @Override
    public void onStartButtonPressed() {

        // INIT
        Sweeper.INSTANCE.SweeperUp().invoke();

        ClawState.INSTANCE.ClawOpen().invoke();
        ClawPrimaryPivot.INSTANCE.PrimaryPivotToCollection().invoke();
        ClawSecondaryPivot.INSTANCE.SecondaryPivotToCollection().invoke();

        IntakePivot.INSTANCE.IntakeRotateUp().invoke();
        IntakePosition.INSTANCE.IntakePositionUp().invoke();

        VerticalSlides.INSTANCE.VSToCollection().invoke();
        HorizontalSlides.INSTANCE.HSToBase().invoke();


        // Driving
        DriverControl = new MecanumDriverControlled(DriveMotors, gamepadManager.getGamepad1());
        DriverControl.invoke();


        // Toggle Claw
        gamepadManager.getGamepad1().getX().setPressedCommand(ClawState.INSTANCE::toggleClaw);


        // Horizontal Slide Automation
        gamepadManager.getGamepad1().getB().setPressedCommand(HorizontalSlides.INSTANCE::toggleHS);

        gamepadManager.getGamepad1().getLeftBumper().setPressedCommand(
                () -> new ParallelGroup(
                        IntakePosition.INSTANCE.IntakePositionUp(),
                        IntakePivot.INSTANCE.IntakeRotateUp(),
                        IntakeRollers.INSTANCE.Reverse()
                )
        );

        gamepadManager.getGamepad1().getRightBumper().setPressedCommand(
                () -> new ParallelGroup(
                        IntakePosition.INSTANCE.IntakePositionDown(),
                        IntakePivot.INSTANCE.IntakeRotateDown(),
                        IntakeRollers.INSTANCE.Active()
                )
        );

        gamepadManager.getGamepad1().getLeftBumper().setReleasedCommand(
                () -> new ParallelGroup(
                        IntakePosition.INSTANCE.IntakePositionUp(),
                        IntakePivot.INSTANCE.IntakeRotateUp(),
                        IntakeRollers.INSTANCE.Idle()
                )
        );

        gamepadManager.getGamepad1().getRightBumper().setReleasedCommand(
                () -> new ParallelGroup(
                        IntakePosition.INSTANCE.IntakePositionUp(),
                        IntakePivot.INSTANCE.IntakeRotateUp(),
                        IntakeRollers.INSTANCE.Idle()
                )
        );


       gamepadManager.getGamepad1().getRightTrigger().setPressedCommand(
                value -> new SequentialGroup(
                        ClawState.INSTANCE.ClawClose(),
                        new Delay(0.05),
                         new ParallelGroup(
                                ClawPrimaryPivot.INSTANCE.PrimaryPivotToScoring(),
                                ClawSecondaryPivot.INSTANCE.SecondaryPivotToScoring()
                        ),
                        VerticalSlides.INSTANCE.VSToScoring()
                )
        );

        gamepadManager.getGamepad1().getRightTrigger().setReleasedCommand(
                value -> new SequentialGroup(
                        ClawState.INSTANCE.ClawOpen(),
                        new Delay(0.05),
                        new ParallelGroup(
                                ClawPrimaryPivot.INSTANCE.PrimaryPivotToCollection(),
                                ClawSecondaryPivot.INSTANCE.SecondaryPivotToCollection()
                        ),
                        VerticalSlides.INSTANCE.VSToCollection()
                )
        );
    }

    public void onUpdate() {
    }
}