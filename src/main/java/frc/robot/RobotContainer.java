// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.rainstorm.RainstormContainer;
import frc.rainstorm.command.ArcadeDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GenericDemo;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer extends RainstormContainer {

    private final Drivetrain drivetrain = new Drivetrain();
    private final GenericDemo demoSubsystem = new GenericDemo();

    public RobotContainer() {
        super(new XboxController(Constants.OperatorConstants.CONTROLLER_PORT));

        configureButtonBindings();
        configureDefaultCommands();
    }

    @Override
    protected void configureButtonBindings() {
        new Trigger(controller::getAButton).whileTrue(demoSubsystem.getStartEndCommand(() -> 1d));
    }

    @Override
    protected void configureDefaultCommands() {
        drivetrain.setDefaultCommand(
                new ArcadeDrive(drivetrain, () -> controller.getLeftY(), () -> controller.getRightX()));
    }
}
