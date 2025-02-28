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
//import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
//import com.rowanmcalpin.nextftc.pedro.PedroOpMode;
//import com.rowanmcalpin.nextftc.core.command.Command;
//import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
//import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
//import com.rowanmcalpin.nextftc.pedro.FollowPath;
//
//import PedroPathing.constants.FConstants;
//import PedroPathing.constants.LConstants;
//import SubSystems.ClawSystem;
//import SubSystems.IntakeSystem;
//import SubSystems.SlideSystem;
//
//@Autonomous(name = "5-Specimen Auto", group = "Autonomous")
//public class FiveSpecAuto extends PedroOpMode {
//
//    public FiveSpecAuto() {
//        super(IntakeSystem.INSTANCE, ClawSystem.INSTANCE, SlideSystem.INSTANCE);
//    }
//
//    /* ------- Define Robot Poses ------- */
//    private final Pose startPose = new Pose(7, 72, Math.toRadians(0));  // Start Position
//    private final Pose scorePose1 = new Pose(24, 72, Math.toRadians(0));
//    private final Pose scorePose2 = new Pose(24, 78, Math.toRadians(0));
//    private final Pose scorePose3 = new Pose(24, 76, Math.toRadians(0));
//    private final Pose scorePose4 = new Pose(24, 74, Math.toRadians(0));
//    private final Pose scorePose5 = new Pose(24, 72, Math.toRadians(0));
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
//        targetSample1 = new Path(new BezierCurve(new Point(scorePose1), new Point(22.000, 48.000, Point.CARTESIAN), new Point(42.000, 40.000, Point.CARTESIAN)));
//        targetSample1.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(150));
//
//        retrieveSample1 = new Path(new BezierLine(new Point(42.000, 40.000, Point.CARTESIAN), new Point(30.000, 40.000, Point.CARTESIAN)));
//        retrieveSample1.setLinearHeadingInterpolation(Math.toRadians(150), Math.toRadians(80));
//
//        targetSample2 = new Path(new BezierLine(new Point(30.000, 40.000, Point.CARTESIAN), new Point(42.000, 30.000, Point.CARTESIAN)));
//        targetSample2.setLinearHeadingInterpolation(Math.toRadians(80), Math.toRadians(150));
//
//        retrieveSample2 = new Path(new BezierLine(new Point(42.000, 30.000, Point.CARTESIAN), new Point(30.000, 30.000, Point.CARTESIAN)));
//        retrieveSample2.setLinearHeadingInterpolation(Math.toRadians(150), Math.toRadians(80));
//
//        targetSample3 = new Path(new BezierLine(new Point(30.000, 30.000, Point.CARTESIAN), new Point(42.000, 22.000, Point.CARTESIAN)));
//        targetSample3.setLinearHeadingInterpolation(Math.toRadians(80), Math.toRadians(150));
//
//        retrieveSample3 = new Path(new BezierLine(new Point(42.000, 22.000, Point.CARTESIAN), new Point(30.000, 22.000, Point.CARTESIAN)));
//        retrieveSample3.setLinearHeadingInterpolation(Math.toRadians(150), Math.toRadians(80));
//
//        /* -------- Score Specimens Path -------- */
//        collectSpecimen2 = new Path(new BezierCurve(new Point(30.000, 22.000, Point.CARTESIAN), new Point(30.000, 32.000, Point.CARTESIAN), new Point(collectPose)));
//        collectSpecimen2.setLinearHeadingInterpolation(Math.toRadians(80), Math.toRadians(0));
//
//        scoreSpecimen2 = new Path(new BezierCurve(new Point(collectPose), new Point(4.000, 78.000, Point.CARTESIAN), new Point(8.000, 78.000, Point.CARTESIAN), new Point(scorePose2)));
//        scoreSpecimen2.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        collectSpecimen3 = new Path(new BezierCurve(new Point(scorePose2), new Point(24.000, 32.000, Point.CARTESIAN), new Point(collectPose)));
//        collectSpecimen3.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        scoreSpecimen3 = new Path(new BezierCurve(new Point(collectPose), new Point(4.000, 76.000, Point.CARTESIAN), new Point(8.000, 76.000, Point.CARTESIAN), new Point(scorePose3)));
//        scoreSpecimen3.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        collectSpecimen4 = new Path(new BezierCurve(new Point(scorePose3), new Point(24.000, 32.000, Point.CARTESIAN), new Point(collectPose)));
//        collectSpecimen4.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        scoreSpecimen4 = new Path(new BezierCurve(new Point(collectPose), new Point(4.000, 74.000, Point.CARTESIAN), new Point(8.000, 74.000, Point.CARTESIAN), new Point(scorePose4)));
//        scoreSpecimen4.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        collectSpecimen5 = new Path(new BezierCurve(new Point(scorePose4), new Point(24.000, 32.000, Point.CARTESIAN), new Point(collectPose)));
//        collectSpecimen5.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        scoreSpecimen5 = new Path(new BezierCurve(new Point(collectPose), new Point(4.000, 72.000, Point.CARTESIAN), new Point(8.000, 72.000, Point.CARTESIAN), new Point(scorePose5)));
//        scoreSpecimen5.setConstantHeadingInterpolation(Math.toRadians(0));
//
//        /* -------- Park Path -------- */
//        park = new Path(new BezierLine(new Point(collectPose), new Point(parkPose)));
//        park.setConstantHeadingInterpolation(Math.toRadians(0));
//    }
//
//    public Command CollectSpecimen() {
//        return new SequentialGroup(
//                ClawSystem.INSTANCE.ClawOpen(),
//                new ParallelGroup(
//                        ClawSystem.INSTANCE.PrimaryPivotToCollection(),
//                        ClawSystem.INSTANCE.SecondaryPivotToCollection()
//                ),
//                new Delay(0.25),
//                SlideSystem.INSTANCE.VSToCollection()
//        );
//    }
//
//    public Command ScoreSpecimen() {
//        return new SequentialGroup(
//                ClawSystem.INSTANCE.ClawClose(),
//                new ParallelGroup(
//                        ClawSystem.INSTANCE.PrimaryPivotToScoring(),
//                        ClawSystem.INSTANCE.SecondaryPivotToScoring(),
//                        SlideSystem.INSTANCE.VSToScoring()
//                )
//        );
//    }
//
//    public Command AutonRoutine() {
//        return new SequentialGroup(
//                ScoreSpecimen(),
//                new FollowPath(scorePreload),
//                CollectSpecimen(),
//
//                new FollowPath(targetSample1),
//                IntakeSystem.INSTANCE.SweeperDown(),
//                new FollowPath(retrieveSample1),
//                IntakeSystem.INSTANCE.SweeperUp(),
//
//                new FollowPath(targetSample2),
//                IntakeSystem.INSTANCE.SweeperDown(),
//                new FollowPath(retrieveSample2),
//                IntakeSystem.INSTANCE.SweeperUp(),
//
//                new FollowPath(targetSample3),
//                IntakeSystem.INSTANCE.SweeperDown(),
//                new FollowPath(retrieveSample3),
//                IntakeSystem.INSTANCE.SweeperUp(),
//
//                new FollowPath(collectSpecimen2),
//                ScoreSpecimen(),
//                new FollowPath(scoreSpecimen2),
//                CollectSpecimen(),
//
//                new FollowPath(collectSpecimen3),
//                ScoreSpecimen(),
//                new FollowPath(scoreSpecimen3),
//                CollectSpecimen(),
//
//                new FollowPath(collectSpecimen4),
//                ScoreSpecimen(),
//                new FollowPath(scoreSpecimen4),
//                CollectSpecimen(),
//
//                new FollowPath(collectSpecimen5),
//                ScoreSpecimen(),
//                new FollowPath(scoreSpecimen5),
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
//        follower.setStartingPose(startPose);
//        buildPaths();
//    }
//
//    @Override
//    public void onStartButtonPressed() {
//        AutonRoutine().invoke();
//    }
//}