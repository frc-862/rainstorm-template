package frc.robot.subsystems.led;

public abstract class LEDProgramConstruct {

    public interface IRGBMethod {
        void set(RGBConstruct rgb);
    }

    // public interface IHSVMethod {
    //     void set(double h, double s, double v);
    // }

    protected IRGBMethod setRGB;

    protected RGBConstruct sampleRgbConstruct;

    public LEDProgramConstruct() {
        sampleRgbConstruct = LED.getShuffleboardRGB();
    }
    
    public void supplyRGBMethod(IRGBMethod setRGB) {
        this.setRGB = setRGB;
    }

    public boolean supplied() {
        return this.setRGB != null;
    }

    public boolean hasRGBMethod() {
        return this.setRGB != null;
    }

    public void init() {
    }

    public void run() {
    }

    public void end() {
    }

}
