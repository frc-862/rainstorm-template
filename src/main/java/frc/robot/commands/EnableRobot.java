// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.DSSim;
import frc.robot.commands.ledprograms.Pulse;
import frc.robot.commands.ledprograms.Solid;
import frc.robot.subsystems.ButtonControl;
import frc.robot.subsystems.led.LED;
import frc.robot.subsystems.led.RGBConstruct;

public class EnableRobot extends Command {
    
    private DSSim driverStation;
    private LED led;
    private ButtonControl buttons;

    private boolean readyToEnable;
    private boolean hasEnabled;

    public EnableRobot(DSSim driverStation, LED led, ButtonControl buttons) {
        this.driverStation = driverStation;
        this.led = led;
        this.buttons = buttons;
    }

    @Override
    public void initialize() {
        this.readyToEnable = DriverStation.isEnabled();
        this.hasEnabled = DriverStation.isEnabled();     

        if (readyToEnable) {
            driverStation.disable();
            readyToEnable = false;
            hasEnabled = true;
            return;
        }

        led.set(new Pulse(RGBConstruct.YELLOW).withPulseSpeed(3d)).schedule();
        driverStation.enable();
        readyToEnable = true;
        hasEnabled = true;

        buttons.setAllLow();
        buttons.setLock(false);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        new WaitCommand(2d).andThen(() -> {
            if (!DriverStation.isEnabled()) {
                led.set(new Solid(RGBConstruct.RED)).schedule();
            }
            buttons.setLock(true);
        }).schedule();
    }

    @Override
    public boolean isFinished() {
        return hasEnabled;
    }
}
