// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.rainstorm.subsystem.GenericSubsystem;
import frc.robot.Robot;

public class TalonGeneric extends GenericSubsystem {

    private TalonSRX motor;

    private double simPower;
    
    public TalonGeneric(int id) {
        super();

        this.motor = new TalonSRX(id);
    }

    public double getPower() {
        return Robot.isReal() ? motor.getMotorOutputPercent() : simPower;
    }
    
    @Override
    public void setPower(double power) {
        motor.set(TalonSRXControlMode.PercentOutput, power);
        if (Robot.isSimulation())
            simPower = power;
    }

    @Override
    public void stop() {
        this.setPower(0d);
    }

}
