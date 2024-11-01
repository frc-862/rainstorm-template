// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.IntSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.RSL;

public class FlashRSL extends Command {

    private RSL rsl;
    private IntSupplier countSupplier;

    private int count;
    private boolean state = true;

    public FlashRSL(RSL rsl, IntSupplier countSupplier) {
        this.rsl = rsl;
        this.countSupplier = countSupplier;

        addRequirements(rsl);
    }

    @Override
    public void initialize() {
        if (countSupplier.getAsInt() != 0) rsl.setPower(rsl.getPowerLimit());
        count = 0;
    }

    @Override
    public void execute() {
        if (countSupplier.getAsInt() == 0) { return; }

        count++;
        if (count >= countSupplier.getAsInt()) {
            state = !state;
            rsl.setPower(state ? rsl.getPowerLimit() : 0d);
            count = 0;
        } 
    }

    @Override
    public void end(boolean interrupted) {
        rsl.setPower(0d);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
