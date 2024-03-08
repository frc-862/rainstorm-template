// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.rainstorm.subsystem.DemoDrivetrain;
import frc.rainstorm.subsystem.DemoSubsystemBase;
import frc.robot.Constants.DemoConstants;
import frc.robot.Constants.RobotMap;

public class Drivetrain extends DemoSubsystemBase implements DemoDrivetrain {
    
    /* Generic MotorController */
    private TalonFX leftMotor;
    private TalonFX rightMotor;

    private DifferentialDrive drive;

    public Drivetrain() {
        super(DemoConstants.DRIVETRAIN_SPEED);

        this.leftMotor = new TalonFX(RobotMap.DRIVE_LEFT);
        this.rightMotor = new TalonFX(RobotMap.DRIVE_RIGHT);

        this.drive = new DifferentialDrive(leftMotor, rightMotor);

        CommandScheduler.getInstance().registerSubsystem(this);
    }

    @Override
    public void arcadeDrive(double speed, double rotation) {
        drive.arcadeDrive(parsePowerLimit(speed), parsePowerLimit(rotation));
    }

    @Override
    public void tankDrive(double leftPower, double rightPower) {
        drive.tankDrive(parsePowerLimit(leftPower), parsePowerLimit(rightPower));
    }

}
