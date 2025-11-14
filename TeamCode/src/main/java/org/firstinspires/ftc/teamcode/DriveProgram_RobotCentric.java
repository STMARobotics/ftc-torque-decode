package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name= "DriveProgram_RobotCentric", group= "Primary")
public class DriveProgram_RobotCentric extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontRight = null;
    private DcMotor frontLeft = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor port = null;
    private DcMotor mast = null;
    private DcMotor starboard = null;
    double speed = 0.75;
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Torque", "MAXIMUM");

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
    }
    @Override
    public void init_loop() {
    }

    @Override
    public void start() {runtime.reset(); }

    @Override
    public void loop() {
        if (gamepad1.dpadLeftWasPressed()) {
            speed =  0.4;
        } else if (gamepad1.dpadRightWasPressed()) {
            speed = 0.75;
        }

        double frontRightPower;
        double frontLeftPower;
        double backRightPower;
        double backLeftPower;
        double catpultPower;
        double portPower;
        double mastPower;
        double starboardPower;

        double driveInput = -gamepad1.left_stick_y;
        double strafeInput =gamepad1.left_stick_x;
        double drive = Range.clip(driveInput * Math.sqrt(2), -speed, speed);
        double strafe = Range.clip(strafeInput * Math.sqrt(2), -speed, speed);
        double turn = Range.clip(gamepad1.right_stick_x, -speed, speed);

        frontRightPower = Range.clip(drive - strafe - turn, -1, 1);
        frontLeftPower = Range.clip(drive + strafe + turn, -1, 1);
        backRightPower = Range.clip(drive + strafe - turn, -1, 1);
        backLeftPower = Range.clip(drive - strafe + turn, -1, 1);

        frontRight.setPower(frontRightPower);
        frontLeft.setPower(frontLeftPower);
        backRight.setPower(backRightPower);
        backLeft.setPower(backLeftPower);

        int targetPosition = -460;
        int portCurrentPosition = port.getCurrentPosition();
        int mastCurrentPostition = mast.getCurrentPosition();
        int starboardCurrentPosition = starboard.getCurrentPosition();
        port.setTargetPosition(targetPosition);
        mast.setTargetPosition(targetPosition);
        starboard.setTargetPosition(targetPosition);

        if (gamepad1.dpadDownWasPressed()) {
            port.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            mast.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            starboard.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        if (gamepad1.dpad_down) {
            catpultPower = -0.6;
            port.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            mast.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            starboard.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else {
            catpultPower = 0;
        }

        if (gamepad1.a) {
            mastPower = -1;
            mast.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } else {
            mastPower = catpultPower;
        }

        mast.setPower(mastPower);

        telemetry.addLine("Status:");
        telemetry.addData("Run Time", runtime.toString());
        telemetry.addLine("Motor Data:");
        telemetry.addData("Input ", "Y (%.2f), X (%.2f), RX (%.2f)", driveInput, strafeInput, turn);
        telemetry.addData("Speed", speed);
        telemetry.addData("Power ","FR (%.2f), FL (%.2f), BR (%.2f), BL (%.2f)", frontRightPower, frontLeftPower, backRightPower, backLeftPower);
        telemetry.addLine("Catapult Data:");
        telemetry.addData("Port Encoder",0);
        telemetry.addData("Mast Encoder",mastCurrentPostition);
        telemetry.addData("Starboard Encoder", 0);

        if (gamepad1.back) {
            telemetry.addLine("Boneless Chicken");
        }
    }
    @Override
    public void stop() {
    }
}