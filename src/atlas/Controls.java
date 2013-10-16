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

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * A class that treats the controller as Joystick 1, right(roller) as Joystick 2 and left(arm) as Joystick 3
 */
public class Controls {
    
    private static Timer timer = new Timer();
    private static double lastPress;
    private static Joystick controller = new Joystick(1);
    private static Joystick rightStick = new Joystick(2);
    private static Joystick leftStick = new Joystick(3);
    private static Joystick camStick = new Joystick(4);
    private static Joystick rightJoy = new Joystick(5);

    static {
        timer.start();
        lastPress = timer.get();
    }

    /**
     * The y value of the left stick on the controller
     * @return The y value of the left stick on the controller
     */
    public static double getLeftY() {
        return controller.getRawAxis(2);
    }

    /**
     * The y value of the right stick on the controller
     * @return The y value of the right stick on the controller
     */
    public static double getRightY() {
        return controller.getRawAxis(4);
    }

    

    public static boolean getButton(int n){
        return controller.getRawButton(n);
    }

    /**
     * It is preferable to use the provided methods to access Joystick values over this for the sake of readability.
     * @return The joystick connected to slot three.
     */
    public static Joystick getCameraControl() {
        return camStick;
    }

    /**
     * Whether button 6 is pushed down on the controller
     * @return Whether button 6 is pushed down on the controller
     */
    public static boolean canShiftHigh() {
        return controller.getRawButton(6);
    }

    /**
     * Whether button 8 is pushed down on the controller
     * @return Whether button 8 is pushed down on the controller
     */
    public static boolean canShiftLow() {
        return controller.getRawButton(8);
    }

    /**
     * Whether button 5 is pushed on the controller
     * @return Whether or not button 5 is pushed on the controller
     */
    public static boolean isManual() {
        return controller.getRawButton(5);
    }

   

    public static boolean isTest() {
        return false;
    }

    public static boolean isRampOverride() {
        return controller.getRawButton(5);
    }
    public static double getRightJoy() {
        return rightJoy.getY();
    }

    private static boolean derpMode;
    public static boolean isDerpMode() {
        if (timer.get() - lastPress > 0.6 && rightStick.getRawButton(6)) {
            derpMode = !derpMode;
            lastPress = timer.get();
        }
        return derpMode;
    }
}
