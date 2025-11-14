package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous(name = "Auto BN", group = "Tests")
public class AutoBN extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontRight = null;
    private DcMotor frontLeft = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor port = null;
    private DcMotor mast = null;
    private DcMotor starboard = null;

    @Override
    public void runOpMode() {
        frontRight = hardwareMap.get(DcMotor.class, "frontright");
        frontLeft = hardwareMap.get(DcMotor.class, "frontleft");
        backRight = hardwareMap.get(DcMotor.class, "backright");
        backLeft = hardwareMap.get(DcMotor.class, "backleft");
        port = hardwareMap.get(DcMotor.class, "port");
        mast = hardwareMap.get(DcMotor.class, "mast");
        starboard = hardwareMap.get(DcMotor.class, "starboard");

        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        port.setDirection(DcMotor.Direction.FORWARD);
        port.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        port.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mast.setDirection(DcMotor.Direction.FORWARD);
        mast.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mast.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        starboard.setDirection(DcMotor.Direction.FORWARD);
        starboard.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        starboard.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        int targetPosition = -460;
        int portCurrentPosition = port.getCurrentPosition();
        int mastCurrentPostition = mast.getCurrentPosition();
        int starboardCurrentPosition = starboard.getCurrentPosition();
        port.setTargetPosition(targetPosition);
        mast.setTargetPosition(targetPosition);
        starboard.setTargetPosition(targetPosition);

        while (opModeInInit()) { // activates while initialized and waits until start
            telemetry.addData("Status", "Initialized");
            telemetry.update();
        }

        drivestraight(.85, 285);
        turn(-.75, 260);
        turn(.1, 100);
        Stop(500);
        armlunch();
    }
    public void armlunch() {
        port.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mast.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        starboard.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        mast.setPower(-0.6);
        port.setPower(-0.6);
        starboard.setPower(-0.6);

        port.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mast.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        starboard.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        sleep(1000);

        while (mast.isBusy()) {

        }

        mast.setPower(0);
        port.setPower(0);
        starboard.setPower(0);
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
    public void strafe(double power, long time) { // Will: i corrected the word "straif" to "strafe"
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
