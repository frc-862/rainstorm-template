package frc.robot.commands.ledprograms;

import frc.robot.subsystems.led.LEDProgramConstruct;
import frc.robot.subsystems.led.RGBConstruct;
import frc.robot.subsystems.led.RGBConstruct.RGBConstructSupplier;

public class Solid extends LEDProgramConstruct {

    private RGBConstruct rgb;
    private RGBConstructSupplier rgbSupplier;

    public Solid(RGBConstruct rgb) {
        this.rgb = rgb;
    }
    public Solid(RGBConstructSupplier rgb) {
        this.rgb = rgb.get();
        this.rgbSupplier = rgb;
    }

    @Override
    public void init() {
        if (rgbSupplier != null) {
            setRGB.set(rgbSupplier.get());
        } else {
            setRGB.set(rgb);
        }
    }
    
}
