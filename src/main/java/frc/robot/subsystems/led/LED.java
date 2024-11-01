// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.led;

import com.ctre.phoenix.led.CANdle;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.rainstorm.shuffleboard.LightningShuffleboard;
import frc.robot.Constants.RobotMap;
import frc.robot.commands.ledprograms.*;

public class LED extends SubsystemBase {

    private CANdle candle;

    private LEDProgramConstruct defaultProgram;
    private LEDProgramConstruct currentProgram;
    private boolean hasInit = false;

    private boolean manualEnabled = false;

    public LED() {
        this.candle = new CANdle(RobotMap.CANDLE);

        candle.configV5Enabled(true);

        initShuffleboard();
    }

    private void initShuffleboard() {
        SendableChooser<LEDProgramConstruct> programChooser = new SendableChooser<>();
        programChooser.setDefaultOption("None", null);
        programChooser.addOption("Pulse", new Pulse());
        programChooser.addOption("Flash", new Flash());
        programChooser.addOption("Rainbow", new Rainbow());
        // programChooser.addOption("Solid", new Solid(() -> getShuffleboardRGB()));

        LightningShuffleboard.setDouble("LED", "Red", 0);
        LightningShuffleboard.setDouble("LED", "Green", 0);
        LightningShuffleboard.setDouble("LED", "Blue", 0);

        LightningShuffleboard.setStringSupplier("LED", "Current Program",
                () -> currentProgram == null ? "None" : currentProgram.getClass().getSimpleName());
        LightningShuffleboard.setBool("LED", "Manual Enable", manualEnabled);
        LightningShuffleboard.set("LED", "Clear Program",
                new InstantCommand(() -> clearProgram()).ignoringDisable(true));
        LightningShuffleboard.set("LED", "Set Program",
                new InstantCommand(() -> setProgramWithManual(programChooser.getSelected())).ignoringDisable(true));
        LightningShuffleboard.set("LED", "Programs", programChooser);
    }

    public void setProgram(LEDProgramConstruct program) {
        if (manualEnabled)
            return;
        setProgramInternal(program);
    }

    private void setProgramWithManual(LEDProgramConstruct program) {
        if (!manualEnabled)
            return;
        setProgramInternal(program);
    }

    private void setProgramInternal(LEDProgramConstruct program) {
        if (program == null) {
            clearProgram();
            return;
        }

        if (!program.supplied()) {
            program.supplyRGBMethod(this::setRGB);
        }

        this.currentProgram = program;
        this.hasInit = false;
    }

    public void clearProgram() {
        if (currentProgram != null) {
            this.currentProgram.end();
        }

        this.currentProgram = null;
        this.hasInit = false;

        reportRGB(new RGBConstruct());
        setRGB(new RGBConstruct());
    }

    private void reportRGB(RGBConstruct rgb) {
        LightningShuffleboard.setDouble("LED", "OutR", rgb.getR());
        LightningShuffleboard.setDouble("LED", "OutG", rgb.getG());
        LightningShuffleboard.setDouble("LED", "OutB", rgb.getB());
        LightningShuffleboard.setString("LED", "OutHex", rgb.getHex());
    }

    private void setRGB(RGBConstruct rgb) {
        candle.setLEDs(rgb.getR(), rgb.getG(), rgb.getB());
        reportRGB(rgb);
    }

    public void setDefaultProgram(LEDProgramConstruct program) {
        this.defaultProgram = program;
    }

    public Command set(LEDProgramConstruct program, boolean returnToDefault) {
        Command command = new InstantCommand(() -> setProgram(program)).ignoringDisable(true);
        if (returnToDefault) {
            if (defaultProgram != null) {
                return command.andThen(set(defaultProgram));
            }
        }
        return command;
    }
    public Command set(LEDProgramConstruct program) {
        return set(program, false);
    }

    public Command returnDefault() {
        if (defaultProgram == null) {
            return new InstantCommand().ignoringDisable(true);
        }
        return new InstantCommand(() -> setProgram(defaultProgram)).ignoringDisable(true);
    }

    @Override
    public void periodic() {
        manualEnabled = LED.isManualEnabled();

        if (currentProgram == null) {
            if (manualEnabled) {
                setRGB(LED.getShuffleboardRGB());
            }
            return;
        }

        if (!hasInit) {
            currentProgram.init();
            hasInit = true;
            return;
        }

        currentProgram.run();
    }

    public static RGBConstruct getShuffleboardRGB() {
        return new RGBConstruct(
            (int) LightningShuffleboard.getDouble("LED", "Red", 0),
            (int) LightningShuffleboard.getDouble("LED", "Green", 0),
            (int) LightningShuffleboard.getDouble("LED", "Blue", 0));
    }

    public static boolean isManualEnabled() {
        return LightningShuffleboard.getBool("LED", "Manual Enable", false);
    }

}
