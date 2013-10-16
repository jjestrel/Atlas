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

import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Solenoid;

public class SolenoidContainer {
    public static class Components {
        public static final Component SHIFTER1_SOLENOID = new Component(7, 1);
        public static final Component SHIFTER2_SOLENOID = new Component(7, 2);
        public static final Component WRIST1_SOLENOID = new Component(7, 3);
        public static final Component WRIST2_SOLENOID = new Component(7, 4);
        public static final Component TBD1_SOLENOID = new Component(7, 5);
        public static final Component TBD2_SOLENOID = new Component(7, 6);
        public static final Component MINIBOT_SOLENOID = new Component(7, 7);
    }

    private static Solenoid[][] solenoids = new Solenoid[SensorBase.kSolenoidModules][SensorBase.kSolenoidChannels];

    /**
     * Ensures that the solenoid at the slot:channel exists inside the array.
     * @param slot Slot of the solenoid
     * @param channel Channel the solenoid is located on.
     */
    private static void validate(int slot, int channel) {
        if (solenoids[slot-7][channel] == null) {
            solenoids[slot-7][channel] = new Solenoid(slot, channel);
        }
    }

    /**
     * @param slot The slot to get the solenoid from.
     * @param channel The channel that the solenoid is located in.
     * @return The solenoid at the specified slot and channel.
     */
    public static Solenoid getSolenoid(int slot, int channel) {
        validate(slot, channel);
        return solenoids[slot - 7][channel];
    }

    /**
     * This uses the default solenoid module See: SensorBase.getDefaultSolenoidModule()
     * @param channel The channel that the solenoid is located in.
     * @return The solenoid at the specified slot and channel.
     */
    public static Solenoid getSolenoid(int channel) {
        validate(SensorBase.getDefaultSolenoidModule(), channel);
        return solenoids[SensorBase.getDefaultSolenoidModule() - 7][channel];
    }

    /**
     * @param component The component that points to the solenoid.
     * @return The solenoid specified by the component
     */
    public static Solenoid getSolenoid(Component component) {
        validate(component.getSlot(), component.getChannel());
        return solenoids[component.getSlot() - 7][component.getChannel()];
    }

    /**
     * @param slot The slot to get the solenoid from.
     * @param channel The channel that the solenoid is located in.
     * @param set Whether or not to activate the solenoid
     */
    public static void setSolenoid(int slot, int channel, boolean set) {
        getSolenoid(slot, channel).set(set);
    }


    /**
     * This uses the default solenoid module See: SensorBase.getDefaultSolenoidModule()
     * @param channel The channel that the solenoid is located in.
     * @param set Whether or not to activate the solenoid
     */
    public static void setSolenoid(int channel, boolean set) {
        getSolenoid(SensorBase.getDefaultSolenoidModule(), channel).set(set);
    }

    /**
     * @param component The component that points to the solenoid.
     * @param set Whether or not to activate the solenoid.
     */
    public static void setSolenoid(Component component, boolean set) {
        getSolenoid(component.getSlot(), component.getChannel()).set(set);
    }
}
