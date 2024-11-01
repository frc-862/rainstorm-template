package frc.robot.commands.ledprograms;

import frc.robot.subsystems.MotorCollection;
import frc.robot.subsystems.led.LEDProgramConstruct;
import frc.robot.subsystems.led.RGBConstruct;

public class MotorCollectionLED extends LEDProgramConstruct {

    public static double brightness = 0.5;

    private MotorCollection collection;

    private RGBConstruct color;
    private double powerVal;
    private double step;

    public MotorCollectionLED(MotorCollection collection) {
        this.collection = collection;
    }
    
    @Override
    public void init() {
        step = 0d;
    }

    @Override
    public void run() {
        powerVal = collection.getPower();

        if (powerVal > 0d) {
            color = RGBConstruct.GREEN.copy().scale(brightness);
        } else if (powerVal < 0d) {
            color = RGBConstruct.RED.copy().scale(brightness);
        } else if (powerVal == 0d) {
            color = RGBConstruct.ORANGE;
            setRGB.set(color);
            return;
        }

        step += 0.4 * Math.abs(powerVal);

        // I don't like the way this is handled, as it causes inconsistensies in LED switching
        // Try to use something like sin or cos to make it smoother
        if (Math.round(step) % 2 == 0) {
            color = RGBConstruct.BLACK;
        }

        setRGB.set(color);
    }

}
