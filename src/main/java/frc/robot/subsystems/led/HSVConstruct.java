package frc.robot.subsystems.led;

public class HSVConstruct {
 
    private int hue;
    private int sat;
    private int val;

    public HSVConstruct(int hue, int sat, int val) {
        this.hue = hue;
        this.sat = sat;
        this.val = val;
    }

    public HSVConstruct() {
        this(0, 0, 0);
    }

    public int getHue() {
        return hue;
    }

    public int getSat() {
        return sat;
    }

    public int getVal() {
        return val;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public HSVConstruct set(int hue, int sat, int val) {
        setHue(hue);
        setSat(sat);
        setVal(val);

        return this;
    }

    public RGBConstruct getRGB() {
        return new RGBConstruct(
            (int) ((val / 255d) * (255 - sat + sat * Math.cos(hue * Math.PI / 180))),
            (int) ((val / 255d) * (255 - sat + sat * Math.cos((hue - 120) * Math.PI / 180))),
            (int) ((val / 255d) * (255 - sat + sat * Math.cos((hue + 120) * Math.PI / 180)))
        );
    }

}
