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

import atlas.utils.RoboMacro;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Watchdog;
import java.util.Vector;

public class Atlas extends IterativeRobot {

    private AxisCamera camera;
    private Compressor compressor;
    private Relay horn;
    public static final int cRIOAnalogSlot = 1, cRIODigitalSlot = 4;
    private static Atlas instance;

    public static Atlas getInstance() {
        return instance;
    }

    public Atlas() {
        compressor = new Compressor(4, 11, 4, 1); //switch 4,11 relay 4,1
        camera = AxisCamera.getInstance();
        horn = new Relay(2);
        horn.setDirection(Relay.Direction.kForward);
        instance = this;
    }

    public void robotInit() {
        Watchdog.getInstance().feed();
        //SolenoidContainerLegacy.initialize();

        //Timer.delay(8); // Let camera boot..?
        //camera = AxisCamera.getInstance()

        compressor.start();
        validatePressure();

        // Shift Low
        Drive.shift(false);

        IO.sendMessage(IO.BOOTUP, "Lifting Sky.");
//        new ShifterThread().start();

    }

    public void disabledInit() {
        Watchdog.getInstance().feed();

        Drive.reset();

        IO.sendMessage(IO.BOOTUP, "Sky on Shoulder.");
    }

    public void autonomousInit() {
    }
    private Vector actions;
    //skew 5% faster on right

    Timer auto = new Timer();
    public void autonomousPeriodic() {
        Watchdog.getInstance().feed();
        long startUpTimeAuto = System.currentTimeMillis();

        validatePressure();
    }

    public void autonomousContinuous() {
        executeMacro();
    }

    public void teleopInit() {
        Watchdog.getInstance().feed();
        Drive.reset();
        SolenoidContainer.setSolenoid(Components.MINIBOT_SOLENOID, false);

        IO.sendMessage(IO.BOOTUP, "Starting teleop");
    }

    public void disabledPeriodic() {
        Watchdog.getInstance().feed();
        Drive.reset();
        timer.stop();
        timer.reset();
        auto.stop();
        auto.reset();
        if (RoboMacro.getInstance().toString().length() > 0) {
            System.out.println(RoboMacro.getInstance());
            RoboMacro.getInstance().clear();
        }

        IO.sendMessage(IO.BOOTUP, "Horatio, I art dead");
    }

    public void disabledContinuous() {
        Drive.reset();
    }
    Timer timer = new Timer();
    double pwmLastR = 0;
    double pwmLastL = 0;
    double pwmR = 0;
    double pwmL = 0;
    double pwmA = 0;
    double pwmLastA = 0;

    public void teleopPeriodic() {
        IO.sendMessage(3, "Derp Mode: " + String.valueOf(Controls.isDerpMode()));
        if (timer.get() == 0) {
            timer.start();
        }
        Watchdog.getInstance().feed();

        validatePressure();

        Drive.periodic();
        long startUpTime = System.currentTimeMillis();


        if (Controls.getButton(6)) {
            horn.set(Relay.Value.kOn);
        } else {
            horn.set(Relay.Value.kOff);
        }

        updateIO();
        //new ShifterThread().start();
        //EncoderImpl encoder = EncoderImpl.getInstance();
        //IO.sendMessage(IO.DEBUG, Double.toString(encoder.getFirstDistance()) + " | " + Double.toString(encoder.getSecondDistance()));

        //  IO.sendMessage(IO.DEBUG, "SWITCH: " + t.get());
        //IO.sendMessage(IO.DEBUG, EncoderImpl.getInstance().getSecondDistance() + "");

        //IO.sendMessage(IO.DEBUG, Double.toString(pmeter.getAverageVoltage()));
        IO.sendMessage(IO.BOOTUP, "Running");
        IO.sendMessage(IO.MAIN, "Teleop Periodic Running");
    }

    public void teleopContinuous() {
        record();
    }

    public void updateIO() {
        Watchdog.getInstance().feed();

        IO.setDigitalOut(1, false); // autoSwitch
        IO.setDigitalOut(2, false);
        IO.setDigitalOut(3, false);
        IO.setDigitalOut(4, false);
        IO.setDigitalOut(5, compressor.getPressureSwitchValue());
        IO.setDigitalOut(6, false);
        IO.setDigitalOut(7, false);
        IO.setDigitalOut(8, false);
    }

    public void validatePressure() {
        Watchdog.getInstance().feed();
        if (compressor.getPressureSwitchValue()) {
            compressor.setRelayValue(edu.wpi.first.wpilibj.Relay.Value.kOff);
            //IO.sendMessage(IO.COMPRESSOR, "Charged");
        } else {
            compressor.setRelayValue(edu.wpi.first.wpilibj.Relay.Value.kOn);
            //IO.sendMessage(IO.COMPRESSOR, "Charging.");
        }
    }
    public void record() {
        double pwmCurR = Controls.getRightY();
        if (pwmCurR <= pwmR - 0.005 || pwmCurR >= pwmR + 0.005) {
            pwmR = pwmCurR;

            if (pwmR + 0.005 >= pwmLastR || pwmR - 0.005 <= pwmLastR) {
                RoboMacro.getInstance().add(MacroKeys.JAG_RIGHT_VAL, timer.get(), pwmR);
                pwmLastR = pwmR;
            }
        }
        double pwmCurL = Controls.getLeftY();
        if (pwmCurL <= pwmL - 0.005 || pwmCurL >= pwmL + 0.005) {
            pwmL = pwmCurL;
            if (pwmL + 0.005 >= pwmLastL || pwmL - 0.005 <= pwmLastL) {
                RoboMacro.getInstance().add(MacroKeys.JAG_LEFT_VAL, timer.get(), pwmL);
                pwmLastL = pwmL;
            }
        }
        double pwmCurA = Controls.getRightJoy();
        if (pwmCurA <= pwmA - 0.005 || pwmCurA >= pwmA + 0.005) {
            pwmA = pwmCurA;
            if (pwmA + 0.005 >= pwmLastA || pwmA - 0.005 <= pwmLastA) {
                RoboMacro.getInstance().add(MacroKeys.ARM_JAG_VAL, timer.get(), pwmA);
                pwmLastA = pwmA;
            }
        }
    }
    public void executeMacro() {
            if (auto.get() == 0) {
            actions = Split.split(Tasks.RECORDED, "|");
            auto.start();
        }
        if (!actions.isEmpty() && auto.get() >= Double.parseDouble((String)actions.elementAt(1))) {
            if (actions.elementAt(0).equals(MacroKeys.JAG_LEFT_VAL)) {
                Drive.setLeft(Double.parseDouble((String)actions.elementAt(2)));
            } else if (actions.elementAt(0).equals(MacroKeys.JAG_RIGHT_VAL)) {
                Drive.setRight(Double.parseDouble((String)actions.elementAt(2)));
            } else if (actions.elementAt(0).equals(MacroKeys.ARM_JAG_VAL)) {
                Drive.setArm(Double.parseDouble((String)actions.elementAt(2)));
            }else {
                System.out.println("Invalid Key");
            }
            actions.removeElementAt(0);
            actions.removeElementAt(0);
            actions.removeElementAt(0);
        }
        if (actions.isEmpty()) {
            Drive.reset();
        }
    }

}
