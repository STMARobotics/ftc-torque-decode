package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
@Autonomous(name="Auto RN", group = "Tests")
public class AutoRN extends LinearOpMode {
    private DcMotor frontRight = null;
    private DcMotor frontLeft = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;

    @Override
    public void runOpMode() {
        frontRight = hardwareMap.get(DcMotor.class, "frontright");
        frontLeft = hardwareMap.get(DcMotor.class, "frontleft");
        backRight = hardwareMap.get(DcMotor.class, "backright");
        backLeft = hardwareMap.get(DcMotor.class, "backleft");

        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        while (opModeInInit()) { // activates while initialized and waits until start
            telemetry.addData("Status", "Initialized");
        }
        drivestraight(.85, 275);
        turn(.75,100);
        Stop(500);
    }
    public void drivestraight(double power, long time) {
        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);
        sleep(time);
    }
    public void turn(double power, long time) {
        frontRight.setPower(-power);
        frontLeft.setPower(power);
        backRight.setPower(-power);
        backLeft.setPower(power);
        sleep(time);
    }
    public void straif(double power, long time) {
        frontRight.setPower(-power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(-power);
        sleep(time);
    }
    public void Stop(long time) {
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
        sleep(time);
    }
}
