package Auton;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelRaceGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.pedro.FollowPath;

import PedroPathing.constants.FConstants;
import PedroPathing.constants.LConstants;

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

@Disabled
@Autonomous(name = "BetterAuto")
public class BetterAuto extends PedroOpMode {

    public BetterAuto() {
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

    /* ------- Define Robot Poses ------- */
    private final Pose startPose = new Pose(7, 48, Math.toRadians(0));  // Start Position
    private final Pose scorePose1 = new Pose(34, 72, Math.toRadians(0));
    private final Pose scorePose2 = new Pose(34, 70, Math.toRadians(0));
    private final Pose scorePose3 = new Pose(34, 68, Math.toRadians(0));
    private final Pose scorePose4 = new Pose(34, 66, Math.toRadians(0));
    private final Pose collectPose = new Pose(5, 32, Math.toRadians(0));
    private final Pose parkPose = new Pose(12, 24, Math.toRadians(0));  // Parking Position

    /* ------- Define Paths and PathChains ------- */
    private Path
            towardSample1, infrontSample1, retrieveSample1,
            towardSample2, infrontSample2, retrieveSample2,
            towardSample3, infrontSample3, retrieveSample3,
            collectSpecimen1, scoreSpecimen1,
            collectSpecimen2, scoreSpecimen2,
            collectSpecimen3, scoreSpecimen3,
            collectSpecimen4, scoreSpecimen4,
            collectSpecimen5, scoreSpecimen5,
            park;

    public void buildPaths() {
        if (follower == null) return;

        /* -------- Collect Samples Path -------- */
        towardSample1 = new Path(new BezierCurve(new Point(startPose), new Point(55.00, 43.000, Point.CARTESIAN), new Point(55.00, 23.000, Point.CARTESIAN)));
        towardSample1.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90));

        retrieveSample1 = new Path(new BezierLine(new Point(55.00, 23.000, Point.CARTESIAN), new Point(19.000, 23.000, Point.CARTESIAN)));
        retrieveSample1.setConstantHeadingInterpolation(Math.toRadians(90));

        towardSample2 = new Path(new BezierCurve(new Point(19.000, 23.000, Point.CARTESIAN), new Point(72.000, 24.000, Point.CARTESIAN), new Point(55.00, 13.000, Point.CARTESIAN)));
        towardSample2.setConstantHeadingInterpolation(Math.toRadians(90));

        retrieveSample2 = new Path(new BezierLine(new Point(55.00, 13.000, Point.CARTESIAN), new Point(19.000, 13.000, Point.CARTESIAN)));
        retrieveSample2.setConstantHeadingInterpolation(Math.toRadians(90));

        /* -------- Score Specimens Path -------- */
        collectSpecimen1 = new Path(new BezierCurve(new Point(19.000, 7.000, Point.CARTESIAN), new Point(24.000, 32.000, Point.CARTESIAN), new Point(collectPose)));
        collectSpecimen1.setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0));

        scoreSpecimen1 = new Path(new BezierCurve(new Point(collectPose), new Point(12.000, 72.000, Point.CARTESIAN), new Point(scorePose1)));
        scoreSpecimen1.setConstantHeadingInterpolation(Math.toRadians(0));

        collectSpecimen2 = new Path(new BezierCurve(new Point(scorePose1), new Point(24.000, 32.000, Point.CARTESIAN), new Point(collectPose)));
        collectSpecimen2.setConstantHeadingInterpolation(Math.toRadians(0));

        scoreSpecimen2 = new Path(new BezierCurve(new Point(collectPose), new Point(12.000, 70.000, Point.CARTESIAN), new Point(scorePose2)));
        scoreSpecimen2.setConstantHeadingInterpolation(Math.toRadians(0));

        collectSpecimen3 = new Path(new BezierCurve(new Point(scorePose2), new Point(24.000, 32.000, Point.CARTESIAN), new Point(collectPose)));
        collectSpecimen3.setConstantHeadingInterpolation(Math.toRadians(0));

        scoreSpecimen3 = new Path(new BezierCurve(new Point(collectPose), new Point(12.000, 68.000, Point.CARTESIAN), new Point(scorePose3)));
        scoreSpecimen3.setConstantHeadingInterpolation(Math.toRadians(0));

        collectSpecimen4 = new Path(new BezierCurve(new Point(scorePose3), new Point(24.000, 32.000, Point.CARTESIAN), new Point(collectPose)));
        collectSpecimen4.setConstantHeadingInterpolation(Math.toRadians(0));

        scoreSpecimen4 = new Path(new BezierCurve(new Point(collectPose), new Point(12.000, 66.000, Point.CARTESIAN), new Point(scorePose4)));
        scoreSpecimen4.setConstantHeadingInterpolation(Math.toRadians(0));

        /* -------- Park Path -------- */
        park = new Path(new BezierLine(new Point(collectPose), new Point(parkPose)));
        park.setConstantHeadingInterpolation(Math.toRadians(0));
    }

    public Command CollectSpecimen() {
        return new SequentialGroup(
                ClawState.INSTANCE.ClawOpen(),
                new Delay(0.05),
                new ParallelGroup(
                        ClawPrimaryPivot.INSTANCE.PrimaryPivotToCollection(),
                        ClawSecondaryPivot.INSTANCE.SecondaryPivotToCollection()
                ),
                new ParallelRaceGroup(
                        VerticalSlides.INSTANCE.VSToCollection(),
                        new Delay(2.0)
                )
        );
    }

    public Command ScoreSpecimen() {
        return new SequentialGroup(
                ClawState.INSTANCE.ClawClose(),
                new Delay(0.05),
                new ParallelGroup(
                        ClawPrimaryPivot.INSTANCE.PrimaryPivotToScoring(),
                        ClawSecondaryPivot.INSTANCE.SecondaryPivotToScoring()
                ),
                new ParallelRaceGroup(
                        VerticalSlides.INSTANCE.VSToScoring(),
                        new Delay(2.0)
                )
        );
    }

    public Command AutonRoutine() {
        return new SequentialGroup(
                new FollowPath(towardSample1,false),
                new FollowPath(retrieveSample1,false),

                new FollowPath(towardSample2,false),
                new FollowPath(retrieveSample2,false),

                new ParallelGroup(
                        CollectSpecimen(),
                        new FollowPath(collectSpecimen1,false)
                ),

                new ParallelGroup(
                        ScoreSpecimen(),
                        new FollowPath(scoreSpecimen1,false)
                ),

                new ParallelGroup(
                        CollectSpecimen(),
                        new FollowPath(collectSpecimen2,false)
                ),

                new ParallelGroup(
                        ScoreSpecimen(),
                        new FollowPath(scoreSpecimen2,false)
                ),

                new ParallelGroup(
                        CollectSpecimen(),
                        new FollowPath(collectSpecimen3,false)
                ),

                new ParallelGroup(
                        ScoreSpecimen(),
                        new FollowPath(scoreSpecimen3,false)
                ),

                new ParallelGroup(
                        CollectSpecimen(),
                        new FollowPath(collectSpecimen4,false)
                ),

                new ParallelGroup(
                        ScoreSpecimen(),
                        new FollowPath(scoreSpecimen4,false)
                ),

                new ParallelGroup(
                        CollectSpecimen(),
                        new FollowPath(park,false)
                )
        );
    }

    @Override
    public void onInit() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);

        follower.setMaxPower(1);
        follower.setStartingPose(startPose);


        buildPaths();
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onStartButtonPressed() {
        IntakePosition.INSTANCE.IntakePositionUp().invoke();
        HorizontalSlides.INSTANCE.HSToBase().invoke();
        AutonRoutine().invoke();
    }
}