package frc.robot.commands.ledprograms;

import frc.rainstorm.shuffleboard.LightningShuffleboard;
import frc.robot.subsystems.led.LED;
import frc.robot.subsystems.led.LEDProgramConstruct;
import frc.robot.subsystems.led.RGBConstruct;

public class Pulse extends LEDProgramConstruct {

    private static RGBConstruct RGB = new RGBConstruct();
    private static double staticPulseSpeed = 1d;

    private double pulseSpeed = -1;

    private double brightSF = 0;
    private double bright = 0;

    public Pulse(RGBConstruct rgb) {
        RGB = rgb;
    }
    public Pulse() {
    }

    public Pulse withPulseSpeed(double speed) {
        this.pulseSpeed = speed;
        return this;
    }

    @Override
    public void run() {
        if (LED.isManualEnabled()) {
            if (!LED.getShuffleboardRGB().compare(RGB))
                RGB = LED.getShuffleboardRGB();
            staticPulseSpeed = LightningShuffleboard.getDouble("LED", "PulseSpeed", staticPulseSpeed);
        }

        bright += 0.1 * (pulseSpeed == -1 ? staticPulseSpeed : pulseSpeed);

        brightSF = (Math.sin(bright) + 1) / 2d;

        RGBConstruct modifiedRGB = RGB.copy();
        
        setRGB.set(modifiedRGB.scale(brightSF));

        if (bright >= Math.PI * 2)
            bright = 0;
    }

    @Override
    public void end() {
        brightSF = 0;
    }

}
