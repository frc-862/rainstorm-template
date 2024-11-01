package frc.robot.commands.ledprograms;

import frc.robot.subsystems.led.HSVConstruct;
import frc.robot.subsystems.led.LEDProgramConstruct;

public class Rainbow extends LEDProgramConstruct {

    private double speed = 1;

    private int hue = 0;
    private int saturation = 255;
    private int value = 255;

    public Rainbow() {
    }

    public Rainbow withSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    @Override
    public void run() {
        hue += speed;

        setRGB.set(new HSVConstruct(hue, saturation, value).getRGB());

        if (hue >= 360)
            hue = 0;
    }

}
