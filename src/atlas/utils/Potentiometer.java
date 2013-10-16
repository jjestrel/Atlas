/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atlas.utils;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author Developer
 */
public class Potentiometer extends AnalogChannel {

    private static Potentiometer instance;
    public static final Object mutex = new Object();

    public static class DegreePoller extends Thread {

        public void run() {
            for (;;) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                }
                synchronized (mutex) {
                    instance.degrees = instance.getDegreesInternal();
                }
            }
        }
    }

    public static Potentiometer getInstance() {
        if (instance == null) {
            instance = new Potentiometer(1, 1);
            //new DegreePoller().start();
        }
        return instance;
    }
    private double degrees;

    public Potentiometer(final int channel) {
        super(channel);
    }

    public Potentiometer(final int slot, final int channel) {
        super(slot, channel);
    }

    public double getDegrees() {
        synchronized (mutex) {
            return degrees;
        }
    }

    protected double getDegreesInternal() {
            return getVoltage() / 0.0013848777777777777777777777777777777; // most accuracy
           // return getVoltage() / 0.0013848;
    }
}
