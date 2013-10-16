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

import atlas.utils.DriverStationLCD;
import atlas.utils.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.DriverStation;

public class IO {
    private static String[] messages = new String[6];

    public static final int MAIN = 0,
            KICK = 1,
            COMPRESSOR = 2,
            CONTROL = 3,
            DEBUG = 4,
            BOOTUP = 5;

    private static final String EMPTY = "                     "; // 21 spaces = max length.

    static {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = EMPTY;
        }
    }

    /**
     * Displays a message to the specified row
     * @param row The row to display the message as defined by the constants in the IO class.
     * @param message The message to be displayed.
     */
    public static void sendMessage(int row, String message) {
        if (message.length() > DriverStationLCD.kLineLength) {
            //sendMessage(DEBUG, "Msg to row " + row + " too long");
        }
        if (!messages[row].equals(message)) {
            DriverStationLCD.getInstance().println(row, 1, (messages[row] = message));
            DriverStationLCD.getInstance().updateLCD();
        }
    }

    /**
     * Set a value for the digital outputs on the Driver Station.
     *
     * Control digital outputs on the Driver Station. These values are typically used for
     * giving feedback on a custom operator station such as LEDs.
     *
     * @param channel The digital output to set. Valid range is 1 - 8.
     * @param value The state to set the digital output.
     */
    public static void setDigitalOut(int channel, boolean value) {
        DriverStation.getInstance().setDigitalOut(channel, value);
    }

    /**
     * Get values from the digital inputs on the Driver Station.
     * Return digital values from the Drivers Station. These values are typically used for buttons
     * and switches on advanced operator interfaces.
     * @param channel The digital input to get. Valid range is 1 - 8.
     * @return The value of the digital input
     */
    public static boolean getDigitalIn(int channel) {
        return DriverStation.getInstance().getDigitalIn(channel);
    }

    /**
     * This is now deprecated because of atlas.utils.DriverStationLCD providing support for integers over objects to represent the row.
     * @param messageType The message row.
     * @return The row represented as a Line object.
     */
    private static final Line getLineByInt(int messageType) {
        switch (messageType) {
            case 0: return Line.kMain6;
            case 1: return Line.kUser2;
            case 2: return Line.kUser3;
            case 3: return Line.kUser4;
            case 4: return Line.kUser5;
            case 5: return Line.kUser6;
            default: return null;
        }
    }
}
