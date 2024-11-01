package frc.robot.commands.ledprograms;

import frc.robot.subsystems.led.LED;
import frc.robot.subsystems.led.LEDProgramConstruct;
import frc.robot.subsystems.led.RGBConstruct;

public class Flash extends LEDProgramConstruct {

    private static RGBConstruct RGB = new RGBConstruct();
    private static final RGBConstruct OFF = new RGBConstruct();

    private final int flashCount;
    private boolean on = false;
    private int count = 0;

    public Flash(RGBConstruct rgb, int flashCount) {
        RGB = rgb;
        this.flashCount = flashCount;
    }

    public Flash(RGBConstruct rgb) {
        RGB = rgb;
        this.flashCount = 8;
    }

    public Flash() {
        this.flashCount = 8;
    }

    @Override
    public void run() {
        if (LED.isManualEnabled()) {
            if (!LED.getShuffleboardRGB().compare(RGB)) 
                RGB = LED.getShuffleboardRGB();
        }

        count++;
        if (count >= flashCount) {
            count = 0;
            on = !on;
            setRGB.set(on ? RGB : OFF);
        }
    }

    @Override
    public void end() {
        on = false;
    }

}
