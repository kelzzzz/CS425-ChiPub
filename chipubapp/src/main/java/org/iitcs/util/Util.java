package org.iitcs.util;

import java.awt.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static String substringByRegex(String regex, String check){
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(check);
        if(m.find()){
            return m.group();
        }
        return null;
    }

    public static void setGridBagConstraints(GridBagConstraints c, int gridx, int gridy, int ipadx) {
        c.gridx = gridx;
        c.gridy = gridy;
        c.ipadx = ipadx;
    }
    public static String generatePassword(){
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            salt.append(CHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
