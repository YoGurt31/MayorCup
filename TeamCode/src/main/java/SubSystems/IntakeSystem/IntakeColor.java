package SubSystems.IntakeSystem;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.ftc.OpModeData;

public class IntakeColor extends Subsystem {

    public static final IntakeColor INSTANCE = new IntakeColor();

    private IntakeColor() {
    }

    public ColorSensor colorSensor;
    public String colorSensorName = "colorSensor";

    // Color Sensor
    public boolean isYellow() {
        return ((colorSensor.red() > 200 && colorSensor.red() < 500) && (colorSensor.green() > 200 && colorSensor.green() < 500) && (colorSensor.blue() > 200 && colorSensor.blue() < 500));
    }

    public boolean isRed() {
        return (colorSensor.red() > 200 && colorSensor.green() < 400 && colorSensor.blue() < 200);
    }

    public boolean isBlue() {
        return (colorSensor.red() < 200 && colorSensor.green() < 400 && colorSensor.blue() > 200);
    }

    public boolean isIgnored() {
        return ((colorSensor.red() > 35 && colorSensor.red() < 75) && (colorSensor.green() > 80 && colorSensor.green() < 120) && (colorSensor.blue() > 80 && colorSensor.blue() < 120));
    }

    @Override
    public void initialize() {
        colorSensor = OpModeData.INSTANCE.getHardwareMap().get(ColorSensor.class, colorSensorName);
    }


}
