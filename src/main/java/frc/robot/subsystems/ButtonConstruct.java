// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import frc.robot.subsystems.ButtonControl.ButtonOverrideState;

public class ButtonConstruct {

    private DigitalInput device;
    private DIOSim sim;

    private ButtonOverrideState overrideState = ButtonOverrideState.NORMAL;

    public ButtonConstruct(int port, boolean isSimulation) {
        device = new DigitalInput(port);

        if (isSimulation) {
            sim = new DIOSim(device);
        }
    }

    public boolean get() {
        if (overrideState != ButtonOverrideState.NORMAL) {
            return overrideState == ButtonOverrideState.OVERRIDE_HIGH;
        }
        return !device.get();
    }

    public void setSim(boolean value) {
        if (sim == null) {
            return;
        }
        sim.setValue(!value);
    }

    public ButtonOverrideState getOverrideState() {
        return overrideState;
    }

    public void setOverrideState(ButtonOverrideState state) {
        overrideState = state;
    }

}
