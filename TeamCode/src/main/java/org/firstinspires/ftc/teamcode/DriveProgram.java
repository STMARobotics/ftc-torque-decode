package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="DriveProgram", group="Tests")
public class DriveProgram extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontRight = null;
    private DcMotor frontLeft = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    double speed = 0.75;
    private DcMotor catpult = null;
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Torque", "MAXIMUM");

        frontRight = hardwareMap.get(DcMotor.class, "frontright");
        frontLeft = hardwareMap.get(DcMotor.class, "frontleft");
        backRight = hardwareMap.get(DcMotor.class, "backright");
        backLeft = hardwareMap.get(DcMotor.class, "backleft");
        catpult = hardwareMap.get(DcMotor.class, "catpult");

        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        catpult.setDirection(DcMotor.Direction.FORWARD);
    }
    @Override
    public void init_loop() {
    }

    @Override
    public void start() {runtime.reset(); }

    @Override
    public void loop() {
        if (gamepad1.dpad_left && speed == 0.75) {
            speed =  0.4;
        }
        if (gamepad1.dpad_left && speed == 0.4) {
            speed = 0.75;
        }

        double frontRightPower;
        double frontLeftPower;
        double backRightPower;
        double backLeftPower;
        double catpultPower = 0;

        double driveInput = -gamepad1.left_stick_y;
        double strafeInput =gamepad1.left_stick_x;
        double drive = Range.clip(driveInput * Math.sqrt(2), -0.75, 0.75);
        double strafe = Range.clip(strafeInput * Math.sqrt(2), -0.75, 0.75);
        double turn = gamepad1.right_stick_x;

        frontRightPower = Range.clip(drive - strafe - turn, -1, 1);
        frontLeftPower = Range.clip(drive + strafe + turn, -1, 1);
        backRightPower = Range.clip(drive + strafe - turn, -1, 1);
        backLeftPower = Range.clip(drive - strafe + turn, -1, 1);

        if (gamepad1.y) {
            catpultPower = 1;
        }
        if (gamepad1.a) {
            catpultPower = -1;
        }

        frontRight.setPower(frontRightPower);
        frontLeft.setPower(frontLeftPower);
        backRight.setPower(backRightPower);
        backLeft.setPower(backLeftPower);
        catpult.setPower(catpultPower);

        // displays active telemetry data
        telemetry.addLine("Status---");
        telemetry.addData("Run Time", runtime.toString());
        telemetry.addData("Input ", "Y (%.2f), X (%.2f), RX (%.2f)", driveInput, strafeInput, turn);
        telemetry.addData("Power ","FR (%.2f), FL (%.2f), BR (%.2f), BL (%.2f)", frontRightPower, frontLeftPower, backRightPower, backLeftPower);
    }
    @Override
    public void stop() {
    }
}