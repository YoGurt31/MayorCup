package PedroPathing.constants;

import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.PINPOINT;

        FollowerConstants.leftFrontMotorName = "frontLeft";
        FollowerConstants.rightFrontMotorName = "frontRight";
        FollowerConstants.leftRearMotorName = "backLeft";
        FollowerConstants.rightRearMotorName = "backRight";

        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.FORWARD;

        FollowerConstants.mass = (31.4 /*lbs*/ / 2.205); // Robot Weight (KG)

        FollowerConstants.xMovement = (72.90505960005652 + 73.97666620517695 + 72.09219874809517 + 73.50117813793766 + 73.24853894489365) / 5;
        FollowerConstants.yMovement = (56.28452714203708 + 56.93007233055285 + 57.15234009091290 + 56.99899027701787 + 56.82031238315881) / 5;

        FollowerConstants.forwardZeroPowerAcceleration = -((36.95695199540094 + 38.42242604223152 + 39.95976019530011 + 38.11621389091128 + 40.54273567029372) / 5);
        FollowerConstants.lateralZeroPowerAcceleration = -((83.50523417993492 + 79.52053063590397 + 72.65048813153122 + 80.56476398483584 + 85.51112565281674) / 5);

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(0.25,0.00005,0.02,0);
        FollowerConstants.useSecondaryTranslationalPID = false;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(0.1,0,0.01,0); // Not being used, @see useSecondaryTranslationalPID

        FollowerConstants.headingPIDFCoefficients.setCoefficients(1.8,0.005,0.15,0);
        FollowerConstants.useSecondaryHeadingPID = false;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(2,0,0.1,0); // Not being used, @see useSecondaryHeadingPID

        FollowerConstants.drivePIDFCoefficients.setCoefficients(0.0175,0.00025,0.000025,0.75,0);
        FollowerConstants.useSecondaryDrivePID = false;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(0.1,0,0,0.6,0); // Not being used, @see useSecondaryDrivePID

        FollowerConstants.zeroPowerAccelerationMultiplier = 0.8;
        FollowerConstants.centripetalScaling = 0.0005; //625

        FollowerConstants.pathEndTimeoutConstraint = 50;
        FollowerConstants.pathEndTValueConstraint = 0.995;
        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;
    }
}
