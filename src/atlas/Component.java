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

/**
 * A class that holds a pair that represents a slot(Module) and channel for a Solenoid
 */
public class Component {

    private int slot;
    private int channel;

    public Component(int slot, int channel) {
        this.slot = slot;
        this.channel = channel;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getSlot() {
        return slot;
    }

    public int getChannel() {
        return channel;
    }
}
