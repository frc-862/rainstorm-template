// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.rainstorm.subsystem.GenericSubsystem;
import frc.robot.Robot;

public class CSMGeneric extends GenericSubsystem {

    private CANSparkMax motor;
    
    private double simPower;

    public CSMGeneric(int id) {
        super();
        
        this.motor = new CANSparkMax(id, MotorType.kBrushless);
    }

    public double getPower() {
        return Robot.isReal() ? motor.getAppliedOutput() : simPower;
    }

    
    @Override
    public void setPower(double power) {
        motor.set(power);
        if (Robot.isSimulation())
            simPower = power;
    }

    @Override
    public void stop() {
        this.setPower(0d);
    }

}
