// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.rainstorm.subsystem.GenericSubsystem;
import frc.robot.Constants.RobotMap;

public class RSL extends GenericSubsystem {

    protected TalonSRX motor;

    public RSL() {
        super(0.75, null);
        
        this.motor = new TalonSRX(RobotMap.RSL);
    }

    @Override
    public void setPower(double power) {
        motor.set(ControlMode.PercentOutput, power);
    }

    @Override
    public void stop() {
        setPower(0d);
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}
