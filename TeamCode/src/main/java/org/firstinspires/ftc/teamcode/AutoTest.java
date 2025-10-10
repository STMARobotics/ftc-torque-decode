package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
@Autonomous(name="Auto Test", group="Tests")
public class AutoTest extends LinearOpMode {
    private DcMotor frontRight = null;
    private DcMotor frontLeft = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    @Override
    public void runOpMode() { // runs the main public void
        // configures motors
        frontRight = hardwareMap.get(DcMotor.class, "frontright");
        frontLeft = hardwareMap.get(DcMotor.class, "frontleft");
        backRight = hardwareMap.get(DcMotor.class, "backright");
        backLeft = hardwareMap.get(DcMotor.class, "backleft");

        // sets the motor directions
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        while (opModeInInit()) { // activates while initialized and waits until start
            telemetry.addData("Status", "Initialized");
        }
        // drive forward
        frontRight.setPower(0.5);
        frontLeft.setPower(0.5);
        backRight.setPower(0.5);
        backLeft.setPower(0.5);
        sleep(500); // this just waits for the given milliseconds, 500 = 0.5 seconds
        // strafe right
        frontRight.setPower(-0.5);
        frontLeft.setPower(0.5);
        backRight.setPower(0.5);
        backLeft.setPower(-0.5);
        sleep(500);
        // stop
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
    }
}
