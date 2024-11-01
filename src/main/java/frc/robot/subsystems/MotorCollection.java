// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;
import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.rainstorm.shuffleboard.LightningShuffleboard;

public class MotorCollection extends SubsystemBase {
    private List<TalonGeneric> talons = new ArrayList<>();
    private List<CSMGeneric> csms = new ArrayList<>();

    private double power;

    public MotorCollection(TalonGeneric[] talons, CSMGeneric[] csms) {
        this();
        
        addTalons(talons);
        addCSMs(csms);
    }
    public MotorCollection() {
        LightningShuffleboard.setDoubleSupplier("MotorCollection", "Power", this::getPower);
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;

        for (TalonGeneric talon : talons) {
            talon.setPower(power);
        }

        for (CSMGeneric csm : csms) {
            csm.setPower(power);
        }
    }

    public void stop() {
        setPower(0d);
    }

    public Command stopCmd() {
        return runOnce(this::stop);
    }

    public void addTalon(TalonGeneric talon) {
        talons.add(talon);
    }

    public void addCSM(CSMGeneric csm) {
        csms.add(csm);
    }

    public void addTalons(TalonGeneric... talon) {
        for (TalonGeneric t : talon) {
            addTalon(t);
        }
    }

    public void addCSMs(CSMGeneric... csm) {
        for (CSMGeneric c : csm) {
            addCSM(c);
        }
    }

    @Override
    public void periodic() {

    }
}
