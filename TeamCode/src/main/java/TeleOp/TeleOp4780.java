// Coded By Gurtej Singh

package TeleOp;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.CommandManager;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.conditionals.BlockingConditionalCommand;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import SubSystems.ClawSystem;
import SubSystems.IntakeSystem;
import SubSystems.SlideSystem;

@TeleOp(name = "TeleOpB", group = "TeleOp")
public class TeleOp4780 extends NextFTCOpMode {

    public TeleOp4780() {
        super(IntakeSystem.INSTANCE, ClawSystem.INSTANCE, SlideSystem.INSTANCE);
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
    }

    @Override
    public void onStartButtonPressed() {
        IntakeSystem.INSTANCE.SweeperUp();

        int Red = IntakeSystem.INSTANCE.colorSensor.red();
        int Green = IntakeSystem.INSTANCE.colorSensor.green();
        int Blue = IntakeSystem.INSTANCE.colorSensor.blue();

        boolean isYellow = Red > 200 && Green > 200 && Blue > 200;
        boolean isRed = Red > 200 && Green < 400 && Blue < 200;
        boolean isBlue = Red < 200 && Green < 400 && Blue > 200;
        boolean isIgnored = (Red > 45 && Red < 65) && (Green > 100 && Green < 120) && (Blue > 100 && Blue < 120);

        DriverControl = new MecanumDriverControlled(DriveMotors, gamepadManager.getGamepad1());
        DriverControl.invoke();

//        CommandManager.INSTANCE.scheduleCommand(new DriverControlled(gamepadManager.getGamepad1(), false));

//        // Drive Telemetry
//        telemetry.addData("X-Pos", follower.getPose().getX());
//        telemetry.addData("Y-Pos", follower.getPose().getY());
//        telemetry.addData("Heading", Math.toDegrees(follower.getPose().getHeading()));
//        telemetry.addData("Target Pose", follower.getPose());
//        telemetry.addLine();
//
//        telemetry.addLine("\n");


        gamepadManager.getGamepad1().getX().setStateChangeCommand(ClawSystem.INSTANCE::ClawOpen);
        gamepadManager.getGamepad1().getX().setStateChangeCommand(ClawSystem.INSTANCE::ClawClose);

        CommandManager.INSTANCE.scheduleCommand(
                new BlockingConditionalCommand(
                        () -> IntakeSystem.INSTANCE.intakePivot.getPosition() == 0.0 && !isBlue,
                        IntakeSystem.INSTANCE::Active,
                        IntakeSystem.INSTANCE::Idle
                )
        );

        gamepadManager.getGamepad1().getY().setStateChangeCommand(
                () -> new SequentialGroup(
                        SlideSystem.INSTANCE.HSToCollection(),
                        new Delay(0.25),
                        new ParallelGroup(
                                IntakeSystem.INSTANCE.IntakeDown()
                        )
                )
        );

        gamepadManager.getGamepad1().getY().setStateChangeCommand(
                () -> new SequentialGroup(
                        new ParallelGroup(
                                IntakeSystem.INSTANCE.IntakeUp()
                        ),
                        new Delay(0.25),
                        SlideSystem.INSTANCE.HSToBase()
                )
        );

        telemetry.addLine("----- Color Sensor Info -----");
        telemetry.addData("Red: ", Red);
        telemetry.addData("Green: ", Green);
        telemetry.addData("Blue: ", Blue);
        telemetry.addLine();
        telemetry.addData("Is Yellow: ", isYellow);
        telemetry.addData("Is Blue: ", isBlue);
        telemetry.addData("Is Red: ", isRed);
        telemetry.addData("Is Ignored: ", isIgnored);

        telemetry.addLine("\n");


        gamepadManager.getGamepad1().getA().setStateChangeCommand(
                () -> new SequentialGroup(
                        ClawSystem.INSTANCE.ClawOpen(),
                        new ParallelGroup(
                                ClawSystem.INSTANCE.PrimaryPivotToCollection(),
                                ClawSystem.INSTANCE.SecondaryPivotToCollection()
                        ),
                        new Delay(0.25),
                        SlideSystem.INSTANCE.VSToCollection()
                )
        );

        gamepadManager.getGamepad1().getA().setStateChangeCommand(
                () -> new SequentialGroup(
                        ClawSystem.INSTANCE.ClawClose(),
                        new ParallelGroup(
                                ClawSystem.INSTANCE.PrimaryPivotToScoring(),
                                ClawSystem.INSTANCE.SecondaryPivotToScoring(),
                                SlideSystem.INSTANCE.VSToScoring())
                )
        );

        telemetry.addLine("----- Diagonal Slide Extension -----");
        telemetry.addData("Power", "%.2f", SlideSystem.INSTANCE.verticalSlide1.getPower());
        telemetry.addData("Position", "%d", SlideSystem.INSTANCE.verticalSlide1.getCurrentPosition());

        telemetry.addLine("\n");

        telemetry.addLine("----- Outtake System Status -----");
        telemetry.addData("Primary Pivot Position", "%.2f", ClawSystem.INSTANCE.clawPrimaryPivot.getPosition());
        telemetry.addData("Secondary Pivot Position", "%.2f", ClawSystem.INSTANCE.clawSecondaryPivot.getPosition());

        double clawPosition = ClawSystem.INSTANCE.clawState.getPosition();
        String clawState = clawPosition <= 0.425 ? "Open" : clawPosition > 0.425 ? "Closed" : "Partially Open";
        telemetry.addData("Claw State", clawState);
        telemetry.addData("Claw Position", "%.2f", clawPosition);

        telemetry.addLine("\n");

        telemetry.update();
    }
}