package frc.robot.subsystems.led;

import edu.wpi.first.wpilibj.util.Color;

public class RGBConstruct {
    
    public static final RGBConstruct RED = new RGBConstruct(255, 0, 0);
    public static final RGBConstruct GREEN = new RGBConstruct(0, 255, 0);
    public static final RGBConstruct BLUE = new RGBConstruct(0, 0, 255);
    public static final RGBConstruct YELLOW = new RGBConstruct(255, 255, 0);
    public static final RGBConstruct CYAN = new RGBConstruct(0, 255, 255);
    public static final RGBConstruct MAGENTA = new RGBConstruct(255, 0, 255);
    public static final RGBConstruct WHITE = new RGBConstruct(255, 255, 255);
    public static final RGBConstruct BLACK = new RGBConstruct(0, 0, 0);

    public static final RGBConstruct ORANGE = new RGBConstruct(255, 120, 0);

    public interface RGBConstructSupplier {
        RGBConstruct get();
    }

    private int r;
    private int g;
    private int b;

    public RGBConstruct(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public RGBConstruct() {
        this(0, 0, 0);
    }

    public boolean isEmpty() {
        return r == 0 && g == 0 && b == 0;
    }

    public int getR() {
        return r < 0 ? 0 : r;
    }

    public int getG() {
        return g < 0 ? 0 : g;
    }

    public int getB() {
        return b < 0 ? 0 : b;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setB(int b) {
        this.b = b;
    }

    public RGBConstruct set(int r, int g, int b) {
        setR(r);
        setG(g);
        setB(b);

        return this;
    }

    public void scaleR(double scale) {
        this.r = (int) Math.round((double) r * scale);
    }

    public void scaleG(double scale) {
        this.g = (int) Math.round((double) g * scale);
    }

    public void scaleB(double scale) {
        this.b = (int) Math.round((double) b * scale);
    }

    public RGBConstruct scale(double scale) {
        scaleR(scale);
        scaleG(scale);
        scaleB(scale);

        return this;
    }

    public Integer[] toArray() {
        return new Integer[] { r, g, b };
    }

    public boolean compare(RGBConstruct rgb) {
        return r == rgb.getR() && g == rgb.getG() && b == rgb.getB();
    }

    public String getHex() {
        return new Color(r, g, b).toHexString();
    }

    public RGBConstruct copy() {
        return new RGBConstruct(r, g, b);
    }
}
