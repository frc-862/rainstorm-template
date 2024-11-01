// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.rainstorm.RainstormContainer;
import frc.rainstorm.shuffleboard.LightningShuffleboard;
import frc.robot.commands.EnableRobot;
import frc.robot.commands.ledprograms.MotorCollectionLED;
import frc.robot.commands.ledprograms.Pulse;
import frc.robot.subsystems.ButtonControl;
import frc.robot.subsystems.CSMGeneric;
import frc.robot.subsystems.MotorCollection;
import frc.robot.subsystems.TalonGeneric;
import frc.robot.subsystems.led.LEDProgramConstruct;
import frc.robot.subsystems.led.RGBConstruct;
import frc.robot.subsystems.led.LED;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer extends RainstormContainer {

    private final LED led = new LED();
    private final ButtonControl buttons = new ButtonControl();

    private final MotorCollection motorCollection = new MotorCollection(new TalonGeneric[] {
            new TalonGeneric(0), new TalonGeneric(2), new TalonGeneric(3)
        }, new CSMGeneric[] {
            new CSMGeneric(4)
    });

    private final DSSim dsSim = new DSSim();

    public RobotContainer() {
        super();

        input.addXbox(new XboxController(Constants.CONTROLLER_PORT));

        DriverStation.silenceJoystickConnectionWarning(true);

        led.setDefaultProgram(new Pulse(RGBConstruct.CYAN).withPulseSpeed(2d));

        configureButtonBindings();
        configureDefaultCommands();
    }

    @Override
    protected void configureButtonBindings() {
        dsSim.init();

        LightningShuffleboard.setBoolSupplier("ButtonControl", "DS Enabled", () -> DriverStation.isEnabled());
        // LightningShuffleboard.setBoolSupplier("ButtonControl", "DS Attached", () -> DriverStation.isDSAttached());
        // LightningShuffleboard.setBoolSupplier("ButtonControl", "Fake DS Connected", dsSim::isFakeDS);

        // Disable Button (Y)
        new Trigger(() -> buttons.getGreenTop() || buttons.getYellowOnly()).onTrue(dsSim.disableCmd());

        // Enable Button (G + Y)
        new Trigger(buttons::getYellowAndGreen).whileTrue(
                new EnableRobot(dsSim, led, buttons).alongWith(motorCollection.stopCmd()).ignoringDisable(true));

        // Motor Negative (R)
        new Trigger(buttons::getRedOnly).whileTrue(new RunCommand(() -> motorCollection.setPower(
                        motorCollection.getPower() - 0.04 < -1d ? -1d : motorCollection.getPower() - 0.04),
                        motorCollection));

        // Motor Positive (G)
        new Trigger(buttons::getGreenOnly).whileTrue(new RunCommand(() -> motorCollection.setPower(
                        motorCollection.getPower() + 0.04 > 1d ? 1d : motorCollection.getPower() + 0.04),
                        motorCollection));

        // Motor Stop (R + G)
        new Trigger(buttons::getRedAndGreen).whileTrue(motorCollection.stopCmd());

        // LED States for Motors
        new Trigger(() -> DriverStation.isEnabled()).onTrue(led.set(new MotorCollectionLED(motorCollection)));
    }

    @Override
    protected void configureDefaultCommands() {
    }

    public void setLEDProgram(LEDProgramConstruct program) {
        led.setProgram(program);
    }

}
