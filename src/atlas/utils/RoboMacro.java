/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atlas.utils;

/**
 *
 * @author 3161
 */
public class RoboMacro {
    private static RoboMacro instance;
    
    public static RoboMacro getInstance() {
        if (instance == null) {
            instance = new RoboMacro();
        }
        return instance;
    }
    
    private StringBuffer buffer;
    
    public RoboMacro() {
        buffer = new StringBuffer();
    }

    private double setDigits(double number, int count) {
        int val = (int)(number * pow(10.0, count));
        return (double)val / pow(10, count);
    }

    private double pow(double base, int exp) {
        double ret = 1;
        for (int i = 0; i < exp; i++) {
            ret *= base;
        }
        
        return ret;
    }

    public int getHighestPower(double number, int toCheck) {
        for(int i = 1; ; i++) {
            if (number / pow(toCheck, i) <= 1) {
                return i;
            }
        }
    }

    public void add(String key, double time, double state) {
        buffer.append(key);
        buffer.append("|");
        buffer.append(setDigits(time, getHighestPower(time, 10) + 5));
        buffer.append("|");
        buffer.append(state);
        buffer.append("|");
    }

    public void clear() {
        buffer.delete(0, buffer.length());
    }
    
    public String toString() {
        return buffer.toString();
    }
}
