/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atlas.utils;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

/**
 *
 * @author Developer
 */
public class EncoderImpl {

    private static EncoderImpl instance;

    public static EncoderImpl getInstance() {
        if (instance == null) {
            instance = new EncoderImpl();
        }
        return instance;
    }
    
    private Encoder first, second;
    private Encoder armEnc;

    private EncoderImpl() {
//        DigitalInput firstLeft = new DigitalInput(4, 1);
//        DigitalInput firstRight = new DigitalInput(4, 2);
//
//        DigitalInput secondLeft = new DigitalInput(4, 3);
//        DigitalInput secondRight = new DigitalInput(4, 4);

        first = new Encoder(4, 5, 4, 6);

//        first = new Encoder(firstLeft,
//                firstRight, true, EncodingType.k2X);

        first.setDistancePerPulse(1.5);
        first.setReverseDirection(true);
        first.start();

        first.setMaxPeriod(99999);


        second = new Encoder(4, 12, 4, 13);
//        second = new Encoder(secondLeft,
//                secondRight, true, EncodingType.k2X);
        second.setDistancePerPulse(1.5);
        second.start();
        second.setMaxPeriod(999999);
//        armEnc = new Encoder(4, 5,
//                4, 6, true, EncodingType.k1X);
//        armEnc.setDistancePerPulse(1.5);
//        armEnc.start();
    }

    public double getFirstDistance() {
        return first.getDistance();
    }

    public double getSecondDistance() {
        return second.getDistance();
    }
//
//    public double getArmDistance() {
//        return armEnc.getRaw();
//    }

    public double getFirstRate() {
        return first.getRate();
    }

    public double getSecondRate() {
        return second.getRate();
    }

    public void resetFirst() {
        first.reset();
    }

    public void resetSecond() {
        second.reset();
    }

    public void resetArm() {
        armEnc.reset();
    }

    public boolean isStopped() {
        return first.getStopped() || second.getStopped();
    }

    public boolean isArmStopped() {
        return armEnc.getStopped();
    }

    public boolean getFirstDirection() {
        return first.getDirection();
    }

    public boolean getSecondDirection() {
        return second.getDirection();
    }

    public double getFirstRaw() {
        return first.getRaw();
    }

    public double getSecondRaw() {
        return second.getRaw();
    }

    public double getFirst() {
        return first.get();
    }

    public double getSecond() {
        return second.get();
    }
}
