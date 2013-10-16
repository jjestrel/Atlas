/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/*                                                                            */
/*----------------------------------------------------------------------------*/
/*                          Written for Team 3161                             */
/*----------------------------------------------------------------------------*/
package atlas;

import atlas.SolenoidContainer.Components;
import atlas.utils.EncoderImpl;

import edu.wpi.first.wpilibj.Jaguar;

import edu.wpi.first.wpilibj.Watchdog;

public class Drive {

    public static final Object ARM_MUTEX = new Object();
    private static Jaguar frontRightJag = new Jaguar(4, 1);
    private static Jaguar backRightJag = new Jaguar(4, 2);
    private static Jaguar frontLeftJag = new Jaguar(4, 3);
    private static Jaguar backLeftJag = new Jaguar(4, 4);
    private static Jaguar jayIsBade = new Jaguar(4,7);
   
    
    static long sinceLastSet = System.currentTimeMillis();
 

    /**
     * Sets all jaguar's pwm to zero.
     */
    public static void reset() {
        frontLeftJag.set(0);
        frontRightJag.set(0);
        backLeftJag.set(0);
        backRightJag.set(0);
        jayIsBade.set(0);
    }

    /**
     *
     * @param pwm the PWM to set all four jags to.
     */
    public static void set(double pwm) {
        frontLeftJag.set(-pwm);
        backLeftJag.set(-pwm);
        frontRightJag.set(pwm);
        backRightJag.set(pwm);
        jayIsBade.set(pwm);
    }

    public static void setLeft(double pwm) {
        if (Controls.isDerpMode()) {
            pwm *= 0.4;
        }
        frontLeftJag.set(pwm);
        backLeftJag.set(pwm);
    }
    
    public static void setRight(double pwm) {
        if (Controls.isDerpMode()) {
            pwm *= 0.4;
        }
        frontRightJag.set(-pwm);
        backRightJag.set(-pwm);
    }
    
    public static void set(double left, double right) {
        setLeft(left);
        setRight(right);
    }

    // normally closed 1 button open
  

    // 5 flip
    /**
     * Does checks for all 'drive' related things:
     * shifting, roller, arm, wrist and driving.
     */
    public static void periodic() {
        //IO.sendMessage(IO.CONTROL, EncoderImpl.getInstance().getFirst() + "|" + EncoderImpl.getInstance().getSecond());
        //IO.sendMessage(IO.DEBUG, Double.toString(EncoderImpl.getInstance().getFirstDistance()) + "|" + Double.toString(EncoderImpl.getInstance().getSecondDistance()));
        
        drive();
        shift();
        
        
    }

    


    /**
     *
     * @param high Whether or not to shift to high gear
     */
    public static void shift(boolean high) {
        if (high) {
            SolenoidContainer.getSolenoid(Components.SHIFTER2_SOLENOID).set(true);
            SolenoidContainer.getSolenoid(Components.SHIFTER1_SOLENOID).set(false);
        } else {
            SolenoidContainer.getSolenoid(Components.SHIFTER1_SOLENOID).set(true);
            SolenoidContainer.getSolenoid(Components.SHIFTER2_SOLENOID).set(false);
        }
    }

    /**
     * Periodic method that handles shifting.
     */
    private static void shift() {
        if (Controls.canShiftHigh()) {
            shift(true);
        } else if (Controls.canShiftLow()) {
            shift(false);
        }
    }

    /**
     * Periodic method that handles movement of jags using controller
     */
    private static void drive() {
        setLeft(Controls.getLeftY());
        setRight(Controls.getRightY());
        /*
        Watchdog.getInstance().feed();
        if (Controls.isRampOverride()) {
            frontLeftJag.set(limit(ramp(Controls.getLeftY())));
            backLeftJag.set(limit(ramp(Controls.getLeftY())));
            frontRightJag.set(limit(ramp(-Controls.getRightY())));
            backRightJag.set(limit(ramp(-Controls.getRightY())));
        } else {
            double leftTarget = limit(ramp(Controls.getLeftY()));
            double rightTarget = limit(ramp(-Controls.getRightY()));

            if (leftTarget < frontLeftJag.get()) {
                frontLeftJag.set(frontLeftJag.get() - 0.075);
                backLeftJag.set(backLeftJag.get() - 0.075);
            }
            if (leftTarget > frontLeftJag.get()) {
                frontLeftJag.set(frontLeftJag.get() + 0.075);
                backLeftJag.set(backLeftJag.get() + 0.075);
            }
            if (rightTarget < frontRightJag.get()) {
                frontRightJag.set(frontRightJag.get() - 0.075);
                backRightJag.set(backRightJag.get() - 0.075);
            }
            if (rightTarget > frontRightJag.get()) {
                frontRightJag.set(frontRightJag.get() + 0.075);
                backRightJag.set(backRightJag.get() + 0.075);
            }
        }*/
    }

    private static double limit(double target) {
        if (target < -1) {
            return -1;
        }
        if (target > 1) {
            return 1;
        }
        return target;
    }

    public static double getRightAverage() {
        //return (frontRightJag.get() + backRightJag.get()) / 2;
        return frontRightJag.get();
    }

    public static double getLeftAverage() {
        //return (frontLeftJag.get() + backLeftJag.get()) / 2;
        return frontLeftJag.get();
    }

    private static double ramp(double target) {
        if (target < 0) {
            return -(target * target);
        } else if (target > 0) {
            return target * target;
        }
        return 0;
    }

    /**
     * Blocks if another move action is in progress
     * @param degrees
     */
    public static void waitByDegrees(double degrees) {
        EncoderImpl.getInstance().resetFirst();
        while (EncoderImpl.getInstance().getFirstDistance() < degrees && Atlas.getInstance().isAutonomous());

    }

    /**
     * Waits an amount of seconds. Same as Timer.delay()
     * @param seconds Seconds to wait
     */
    public static void waitBySeconds(double seconds) {
        double start = System.currentTimeMillis() / 1000;
        while (System.currentTimeMillis() / 1000 - start >= seconds && Atlas.getInstance().isAutonomous());
    }
    public static void setArm(double pwm) {
        jayIsBade.set(pwm);
    }
}
