package atlas;

import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 3161
 */
public class Split {

    public static Vector split(String splitStr, String delimeter) {
        StringBuffer token = new StringBuffer();
        Vector tokens = new Vector();
        
        char[] chars = splitStr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (delimeter.indexOf(chars[i]) != -1) {
                if (token.length() > 0) {
                    tokens.addElement(token.toString());
                    token.setLength(0);
                }
            } else {
                token.append(chars[i]);
            }
        }
        if (token.length() > 0) {
            tokens.addElement(token.toString());
        }
        return tokens;
    }
}
