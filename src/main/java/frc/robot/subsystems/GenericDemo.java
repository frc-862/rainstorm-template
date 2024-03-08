// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.rainstorm.subsystem.GenericSubsystem;
import frc.robot.Constants.DemoConstants;
import frc.robot.Constants.RobotMap;

public class GenericDemo extends GenericSubsystem {

    public GenericDemo() {
        super("Demo Subsystem", DemoConstants.GENERIC_SUB_SPEED,
                new CANSparkMax(RobotMap.GENERIC_SUB, MotorType.kBrushed));
    }

}
