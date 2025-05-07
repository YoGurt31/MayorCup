//package Auton;
//
//import com.pedropathing.follower.Follower;
//import com.pedropathing.localization.Pose;
//import com.pedropathing.pathgen.BezierCurve;
//import com.pedropathing.pathgen.BezierLine;
//import com.pedropathing.pathgen.Path;
//import com.pedropathing.pathgen.Point;
//import com.pedropathing.util.Constants;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
//import com.rowanmcalpin.nextftc.core.command.utility.delays.WaitUntil;
//import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
//import com.rowanmcalpin.nextftc.pedro.PedroOpMode;
//import com.rowanmcalpin.nextftc.core.command.Command;
//import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
//import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
//import com.rowanmcalpin.nextftc.pedro.FollowPath;
//
//import PedroPathing.constants.FConstants;
//import PedroPathing.constants.LConstants;
//
//import SubSystems.ClawSystem.ClawPrimaryPivot;
//import SubSystems.ClawSystem.ClawSecondaryPivot;
//import SubSystems.ClawSystem.ClawState;
//
//import SubSystems.IntakeSystem.IntakeColor;
//import SubSystems.IntakeSystem.IntakePivot;
//import SubSystems.IntakeSystem.IntakePosition;
//import SubSystems.IntakeSystem.IntakeRollers;
//import SubSystems.IntakeSystem.Sweeper;
//
//import SubSystems.SlideSystem.HorizontalSlides;
//import SubSystems.SlideSystem.VerticalSlides;
//
//@Disabled
//@Autonomous(name = "5-Specimen Auto")
//public class FiveSpecAuto extends PedroOpMode {
//
//    public FiveSpecAuto() {
//        super(
//                ClawPrimaryPivot.INSTANCE,
//                ClawSecondaryPivot.INSTANCE,
//                ClawState.INSTANCE,
//                IntakeColor.INSTANCE,
//                IntakePivot.INSTANCE,
//                IntakePosition.INSTANCE,
//                IntakeRollers.INSTANCE,
//                Sweeper.INSTANCE,
//                HorizontalSlides.INSTANCE,
//                VerticalSlides.INSTANCE
//        );
//    }
//
//    /* ------- Define Robot Poses ------- */
//    private final Pose startPose = new Pose(7, 72, Math.toRadians(0));  // Start Position
//    private final Pose scorePose1 = new Pose(32, 72, Math.toRadians(0));
//    private final Pose scorePose2 = new Pose(32, 77, Math.toRadians(0));
//    private final Pose scorePose3 = new Pose(32, 74.5, Math.toRadians(0));
//    private final Pose scorePose4 = new Pose(32, 69.5, Math.toRadians(0));
//    private final Pose scorePose5 = new Pose(32, 67, Math.toRadians(0));
//    private final Pose collectPose = new Pose(7, 32, Math.toRadians(0));
//    private final Pose parkPose = new Pose(12, 24, Math.toRadians(0));  // Parking Position
//
//    /* ------- Define Paths and PathChains ------- */
//    private Path scorePreload,
//            targetSample1, retrieveSample1,
//            targetSample2, retrieveSample2,
//            targetSample3, retrieveSample3,
//            collectSpecimen2, scoreSpecimen2,
//            collectSpecimen3, scoreSpecimen3,
//            collectSpecimen4, scoreSpecimen4,
//            collectSpecimen5, scoreSpecimen5,
//            park;
//
//    public void buildPaths() {
//        if (follower == null) return;
//
//        /* -------- Score Preload Path -------- */
//        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(scorePose1)));
//        scorePreload.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        /* -------- Collect Samples Path -------- */
//        targetSample1 = new Path(new BezierCurve(new Point(scorePose1), new Point(24.000, 24.000, Point.CARTESIAN), new Point(60.000, 48.000, Point.CARTESIAN), new Point(55.000, 24.000, Point.CARTESIAN)));
//        targetSample1.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-90));
//
//        retrieveSample1 = new Path(new BezierLine(new Point(55.000, 24.000, Point.CARTESIAN), new Point(20.000, 24.000, Point.CARTESIAN)));
//        retrieveSample1.setConstantHeadingInterpolation(Math.toRadians(-90));
//
//        targetSample2 = new Path(new BezierLine(new Point(20.000, 24.000, Point.CARTESIAN), new Point(55.000, 18.000, Point.CARTESIAN)));
//        targetSample2.setConstantHeadingInterpolation(Math.toRadians(-90));
//
//        retrieveSample2 = new Path(new BezierLine(new Point(55.000, 18.000, Point.CARTESIAN), new Point(20.000, 16.000, Point.CARTESIAN)));
//        retrieveSample2.setConstantHeadingInterpolation(Math.toRadians(-90));
//
//        targetSample3 = new Path(new BezierLine(new Point(20.000, 16.000, Point.CARTESIAN), new Point(55.000, 8.000, Point.CARTESIAN)));
//        targetSample3.setConstantHeadingInterpolation(Math.toRadians(-90));
//
//        retrieveSample3 = new Path(new BezierLine(new Point(55.000, 8.000, Point.CARTESIAN), new Point(20.000, 8.000, Point.CARTESIAN)));
//        retrieveSample3.setConstantHeadingInterpolation(Math.toRadians(-90));
//
//        /* -------- Score Specimens Path -------- */
//        collectSpecimen2 = new Path(new BezierCurve(new Point(20.000, 8.000, Point.CARTESIAN), new Point(24.000, 32.000, Point.CARTESIAN), new Point(collectPose)));
//        collectSpecimen2.setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(0));
//
//        scoreSpecimen2 = new Path(new BezierCurve(new Point(collectPose), new Point(6.000, 77.000, Point.CARTESIAN), new Point(scorePose2)));
//        scoreSpecimen2.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        collectSpecimen3 = new Path(new BezierCurve(new Point(scorePose2), new Point(24.000, 32.000, Point.CARTESIAN), new Point(collectPose)));
//        collectSpecimen3.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        scoreSpecimen3 = new Path(new BezierCurve(new Point(collectPose), new Point(6.000, 74.500, Point.CARTESIAN), new Point(scorePose3)));
//        scoreSpecimen3.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        collectSpecimen4 = new Path(new BezierCurve(new Point(scorePose3), new Point(24.000, 32.000, Point.CARTESIAN), new Point(collectPose)));
//        collectSpecimen4.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        scoreSpecimen4 = new Path(new BezierCurve(new Point(collectPose), new Point(6.000, 69.500, Point.CARTESIAN), new Point(scorePose4)));
//        scoreSpecimen4.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        collectSpecimen5 = new Path(new BezierCurve(new Point(scorePose4), new Point(24.000, 32.000, Point.CARTESIAN), new Point(collectPose)));
//        collectSpecimen5.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        scoreSpecimen5 = new Path(new BezierCurve(new Point(collectPose), new Point(6.000, 67.000, Point.CARTESIAN), new Point(scorePose5)));
//        scoreSpecimen5.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        /* -------- Park Path -------- */
//        park = new Path(new BezierLine(new Point(collectPose), new Point(parkPose)));
//        park.setConstantHeadingInterpolation(Math.toRadians(0));
//    }
//
//    public Command CollectSpecimen() {
//        return new SequentialGroup(
//                ClawState.INSTANCE.ClawOpen(),
//                new Delay(0.05),
//                new ParallelGroup(
//                        ClawPrimaryPivot.INSTANCE.PrimaryPivotToCollection(),
//                        ClawSecondaryPivot.INSTANCE.SecondaryPivotToCollection()
//                ),
//                VerticalSlides.INSTANCE.VSToCollection()
//        );
//    }
//
//    public Command ScoreSpecimen() {
//        return new SequentialGroup(
//                ClawState.INSTANCE.ClawClose(),
//                new Delay(0.05),
//                new ParallelGroup(
//                        ClawPrimaryPivot.INSTANCE.PrimaryPivotToScoring(),
//                        ClawSecondaryPivot.INSTANCE.SecondaryPivotAuton()
//                ),
//                 VerticalSlides.INSTANCE.VSToScoringAuto()
//        );
//    }
//
//    public Command AutonRoutine() {
//        return new SequentialGroup(
//
//                ScoreSpecimen(),
//                new FollowPath(scorePreload, true),
//
//                CollectSpecimen(),
//                new FollowPath(targetSample1,true),
//
//                new ParallelGroup(
//                        new FollowPath(retrieveSample1,true)
////                        Sweeper.INSTANCE.SweeperDown()
//                ),
//
////                Sweeper.INSTANCE.SweeperMid(),
//
//                new FollowPath(targetSample2,true),
//
//                new ParallelGroup(
//                        new FollowPath(retrieveSample2,true)
////                        Sweeper.INSTANCE.SweeperDown()
//                ),
//
////                Sweeper.INSTANCE.SweeperMid(),
//
//                new FollowPath(targetSample3,true),
//
//                new ParallelGroup(
//                        new FollowPath(retrieveSample3,true)
////                        Sweeper.INSTANCE.SweeperDown()
//                ),
//
////                Sweeper.INSTANCE.SweeperUp(),
//
//                new FollowPath(collectSpecimen2,true),
//
//                new ParallelGroup(
//                        new FollowPath(scoreSpecimen2,true),
//                        ScoreSpecimen()
//                ),
//
//
//                new ParallelGroup(
//                        CollectSpecimen(),
//                        new FollowPath(collectSpecimen3,true)
//                ),
//
//
//                new ParallelGroup(
//                        ScoreSpecimen(),
//                        new FollowPath(scoreSpecimen3,true)
//                ),
//
//                CollectSpecimen(),
//
//                new FollowPath(collectSpecimen4),
//
//                new ParallelGroup(
//                        new FollowPath(scoreSpecimen4),
//                        ScoreSpecimen()
//                ),
//
//                CollectSpecimen(),
//
//                new FollowPath(collectSpecimen5),
//
//                new ParallelGroup(
//                        new FollowPath(scoreSpecimen5),
//                        ScoreSpecimen()
//                ),
//
//                CollectSpecimen(),
//
//                new FollowPath(park)
//        );
//    }
//
//    @Override
//    public void onInit() {
//        Constants.setConstants(FConstants.class, LConstants.class);
//        follower = new Follower(hardwareMap);
//
//        follower.setMaxPower(1);
//        follower.setStartingPose(startPose);
//
//
//        buildPaths();
//    }
//
//    @Override
//    public void onUpdate() {
//        telemetry.addLine("----- Diagonal Slides Info -----");
//        telemetry.addData("Current Position: ", VerticalSlides.INSTANCE.verticalSlides.getCurrentPosition());
//        telemetry.addData("Target Position: ", VerticalSlides.INSTANCE.controller.getTarget());
//        telemetry.update();
//    }
//
//    @Override
//    public void onStartButtonPressed() {
//        IntakePivot.INSTANCE.IntakeRotateUp().invoke();
//        IntakePosition.INSTANCE.IntakePositionUp().invoke();
//        HorizontalSlides.INSTANCE.HSToBase().invoke();
//        AutonRoutine().invoke();
//    }
//}