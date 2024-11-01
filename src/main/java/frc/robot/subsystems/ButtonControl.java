// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.rainstorm.shuffleboard.LightningShuffleboard;
import frc.robot.Robot;
import frc.robot.Constants.RobotMap.DIO;

public class ButtonControl extends SubsystemBase {

    public enum ButtonOverrideState {
        NORMAL,
        OVERRIDE_HIGH,
        OVERRIDE_LOW
    }

    public ButtonConstruct red;
    public ButtonConstruct yellow;
    public ButtonConstruct green;
    public ButtonConstruct greenTop;

    private boolean buttonsEnabled = true;
    
    public ButtonControl() {
        initLogging();

        red = new ButtonConstruct(DIO.RED_BUTTON, Robot.isSimulation());
        yellow = new ButtonConstruct(DIO.YELLOW_BUTTON, Robot.isSimulation());
        green = new ButtonConstruct(DIO.GREEN_BUTTON, Robot.isSimulation());
        greenTop = new ButtonConstruct(DIO.SMALL_GREEN_BUTTON, Robot.isSimulation());

    }

    @Override
    public void simulationPeriodic() {
        if (LightningShuffleboard.getBool("ButtonControl", "Set Y and G", false)) {
            LightningShuffleboard.setBool("ButtonControl", "Set Yellow",
                    LightningShuffleboard.getBool("ButtonControl", "Set Y and G", false));
            LightningShuffleboard.setBool("ButtonControl", "Set Green",
                    LightningShuffleboard.getBool("ButtonControl", "Set Y and G", false));
        }

        red.setSim(LightningShuffleboard.getBool("ButtonControl", "Set Red", getRed()));
        yellow.setSim(LightningShuffleboard.getBool("ButtonControl", "Set Yellow", getYellow()));
        green.setSim(LightningShuffleboard.getBool("ButtonControl", "Set Green", getGreen()));
        greenTop.setSim(LightningShuffleboard.getBool("ButtonControl", "Set GreenTop", getGreenTop()));
    }

    private void initLogging() {
        LightningShuffleboard.setBoolSupplier("ButtonControl", "Red", this::getRed);
        LightningShuffleboard.setBoolSupplier("ButtonControl", "Yellow", this::getYellow);
        LightningShuffleboard.setBoolSupplier("ButtonControl", "Green", this::getGreen);
        LightningShuffleboard.setBoolSupplier("ButtonControl", "Green Top", this::getGreenTop);
    }

    public boolean getEnableState() {
        return buttonsEnabled;
    }

    public void setEnableState(boolean state) {
        buttonsEnabled = state;

        if (buttonsEnabled) {
            red.setOverrideState(ButtonOverrideState.NORMAL);
            yellow.setOverrideState(ButtonOverrideState.NORMAL);
            green.setOverrideState(ButtonOverrideState.NORMAL);
            greenTop.setOverrideState(ButtonOverrideState.NORMAL);
        } else {
            red.setOverrideState(red.get() ? ButtonOverrideState.OVERRIDE_HIGH : ButtonOverrideState.OVERRIDE_LOW);
            yellow.setOverrideState(yellow.get() ? ButtonOverrideState.OVERRIDE_HIGH : ButtonOverrideState.OVERRIDE_LOW);
            green.setOverrideState(green.get() ? ButtonOverrideState.OVERRIDE_HIGH : ButtonOverrideState.OVERRIDE_LOW);
            greenTop.setOverrideState(greenTop.get() ? ButtonOverrideState.OVERRIDE_HIGH : ButtonOverrideState.OVERRIDE_LOW);
        }
    }


    public boolean getRed() {
        return red.get();
    }

    public boolean getYellow() {
        return yellow.get();
    }

    public boolean getGreen() {
        return green.get();
    }

    public boolean getGreenTop() {
        return greenTop.get();
    }

    public boolean getRedOnly() {
        return getRed() && !getYellow() && !getGreen();
    }

    public boolean getYellowOnly() {
        return getYellow() && !getGreen() && !getRed();
    }

    public boolean getGreenOnly() {
        return !getYellow() && getGreen() && !getRed();
    }
    
    public boolean getYellowAndGreen() {
        return getYellow() && getGreen() && !getRed();
    }

    public boolean getRedAndGreen() {
        return !getYellow() && getGreen() && getRed();
    }

    @Override
    public void periodic() {
    }
}
