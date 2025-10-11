package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name= "DriveProgram_FieldCentric", group= "Primary")
public class DriveProgram_FieldCentric extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontRight = null;
    private DcMotor frontLeft = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    double speed = 0.75;
    private DcMotor catpult = null;
    private int headingResetCount = 0;
    BHI260IMU imu;
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

        imu = hardwareMap.get(BHI260IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
        imu.resetYaw();
    }
    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        if (gamepad1.dpadLeftWasPressed() && speed == 0.75) {
             speed =  0.4;
        }
        if (gamepad1.dpad_left && speed == 0.4) {
            speed = 0.75;
        }

        double heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        double driveInput = -gamepad1.left_stick_y;
        double strafeInput =gamepad1.left_stick_x;
        double drive = Range.clip(driveInput * Math.sqrt(2), -speed, speed);
        double strafe = Range.clip(strafeInput * Math.sqrt(2), -speed, speed);
        double turn = gamepad1.right_stick_x;

        double rotationX = strafe * Math.cos(-heading) - drive * Math.sin(-heading);
        double rotationY = strafe * Math.sin(-heading) + drive * Math.cos(-heading);

        double frontRightPower;
        double frontLeftPower;
        double backRightPower;
        double backLeftPower;
        double catpultPower = 0;

        frontRightPower = Range.clip(rotationY - rotationX - turn, -1, 1);
        frontLeftPower = Range.clip( rotationY + rotationX + turn, -1, 1);
        backRightPower = Range.clip( rotationY + rotationX - turn, -1, 1);
        backLeftPower = Range.clip(rotationY - rotationX + turn, -1, 1);

        if (gamepad1.a && catcounter != 1) {
            catpultPower = 1;
            catcounter = 1;
        }
        else if (gamepad1.a) {
            catpultPower = -1;
            catcounter = 0;
        }

        frontRight.setPower(frontRightPower);
        frontLeft.setPower(frontLeftPower);
        backRight.setPower(backRightPower);
        backLeft.setPower(backLeftPower);
        catpult.setPower(catpultPower);

        telemetry.addLine("Status---");
        telemetry.addData("Run Time", runtime.toString());
        telemetry.addData("Heading", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
        telemetry.addData("Input ", "Y (%.2f), X (%.2f), RX (%.2f)", driveInput, strafeInput, turn);
        telemetry.addData("Speed", speed);
        telemetry.addData("Power ","FR (%.2f), FL (%.2f), BR (%.2f), BL (%.2f)", frontRightPower, frontLeftPower, backRightPower, backLeftPower);
        telemetry.addData("Times Yaw has been Reset", headingResetCount);
        telemetry.addData("Speed", speed);

        if (gamepad1.y) {
            telemetry.addData("Status Update", "Yaw has been reset");
            imu.resetYaw();
        }
        if (gamepad1.yWasPressed()) {
            headingResetCount++;
        }
    }
    @Override
    public void stop() {
    }
}