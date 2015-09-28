package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {
    public static String readString() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int readInt() {
        String numStr = readString();
        int num;
        try {
            num = Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
        return num;
    }

    public static int parseInt(String intStr) {
        int num;
        try {
            num = Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
        return num;
    }
}
