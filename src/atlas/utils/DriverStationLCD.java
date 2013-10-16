/*
 * This is actually FRC code.. but we want to bypass their Line object requirement to take an int.
 * Also lets us bypass the whole needing to clear every time.
 */
package atlas.utils;

import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.communication.FRCControl;

/**
 * Provide access to LCD on the Driver Station.
 *
 * Buffer the printed data locally and then send it
 * when UpdateLCD is called.
 */
public class DriverStationLCD extends SensorBase {

    private static DriverStationLCD m_instance;
    /**
     * Driver station timeout in milliseconds
     */
    public static final int kSyncTimeout_ms = 20;
    /**
     * Command to display text
     */
    public static final int kFullDisplayTextCommand = 0x9FFF;
    /**
     * Maximum line length for Driver Station display
     */
    public static final int kLineLength = 21;
    /**
     * A line with the max length of blank characters used to wipe the lines before printing.
     */
    public static final byte[] EMPTY = "                     ".getBytes(); // 21 spaces = max length.

    /**
     * The line number on the Driver Station LCD
     */
    public static class Line {

        /**
         * The integer value representing this enumeration
         */
        public final int value;
        static final int kMain6_val = 0;
        static final int kUser2_val = 1;
        static final int kUser3_val = 2;
        static final int kUser4_val = 3;
        static final int kUser5_val = 4;
        static final int kUser6_val = 5;
        /**
         * Line at the Bottom of the main screen
         */
        public static final Line kMain6 = new Line(kMain6_val);
        /**
         * Line on the user screen
         */
        public static final Line kUser2 = new Line(kUser2_val);
        /**
         * Line on the user screen
         */
        public static final Line kUser3 = new Line(kUser3_val);
        /**
         * Line on the user screen
         */
        public static final Line kUser4 = new Line(kUser4_val);
        /**
         * Line on the user screen
         */
        public static final Line kUser5 = new Line(kUser5_val);
        /**
         * Line on the user screen
         */
        public static final Line kUser6 = new Line(kUser6_val);

        private Line(int value) {
            this.value = value;
        }
    }
    final byte[] m_textBuffer;

    /**
     * Get an instance of the DriverStationLCD
     * @return an instance of the DriverStationLCD
     */
    public static synchronized DriverStationLCD getInstance() {
        if (m_instance == null) {
            m_instance = new DriverStationLCD();
        }
        return m_instance;
    }

    /**
     * DriverStationLCD contructor.
     *
     * This is only called once the first time GetInstance() is called
     */
    private DriverStationLCD() {
        m_textBuffer = new byte[FRCControl.USER_DS_LCD_DATA_SIZE];

        for (int i = 0; i < FRCControl.USER_DS_LCD_DATA_SIZE; i++) {
            m_textBuffer[i] = ' ';
        }

        m_textBuffer[0] = (byte) (kFullDisplayTextCommand >> 8);
        m_textBuffer[1] = (byte) kFullDisplayTextCommand;
    }

    /**
     * Send the text data to the Driver Station.
     */
    public synchronized void updateLCD() {
        FRCControl.setUserDsLcdData(m_textBuffer, FRCControl.USER_DS_LCD_DATA_SIZE, kSyncTimeout_ms);
    }

    /**
     * Print formatted text to the Driver Station LCD text buffer.
     *
     * Use UpdateLCD() periodically to actually send the text to the Driver Station.
     *
     * @param line The Line object that represents what row to print on the LCD.
     * @param startingColumn The column to start printing to.  This is a 1-based number.
     * @param text the text to print
     */
    public void println(Line line, int startingColumn, String text) {
        println(line.value, startingColumn, text);
    }

    /**
     * Sets every character on a line to spaces.
     *
     * @param line The line that is being cleared.
     */
    private void clear(int line) {
        for (int i = 0; i < EMPTY.length; i++) {
            m_textBuffer[i + line * kLineLength + 2] = EMPTY[i];
        }
    }

    /**
     * Print formatted text to the Driver Station LCD text buffer.
     *
     * Use UpdateLCD() periodically to actually send the test to the Driver Station.
     *
     * @param line The line on the LCD to print to. Must be between 0-5 inclusive.
     * @param startingColumn The column to start printing to.  This is a 1-based number.
     * @param text the text to print
     */
    public void println(int line, int startingColumn, String text) {
        if (line < 0 || line > 5) {
            return;
        }

        int start = startingColumn - 1;
        int maxLength = kLineLength - start;

        if (startingColumn < 1 || startingColumn > kLineLength) {
            throw new IndexOutOfBoundsException("Column must be between 1 and " + kLineLength + ", inclusive");
        }

        int length = text.length();
        byte[] bytes = text.getBytes();
        int finalLength = (length < maxLength ? length : maxLength);
        synchronized (this) {
            clear(line);
            for (int i = 0; i < finalLength; i++) {
                m_textBuffer[i + start + line * kLineLength + 2] = bytes[i];
            }
        }
    }
}
