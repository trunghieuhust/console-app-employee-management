package core;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validator {
    public static boolean validateMenuChoice(int menuChoice) {
        if (menuChoice >= 0 && menuChoice < 6) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateSearchMenuChoice(int searchChoice) {
        if (searchChoice > 0 && searchChoice < 5) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateID(int ID) {
        if (ID == Integer.MIN_VALUE || ID > 99999 || ID < 0) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateName(String name) {
        if (name == null || name.length() > 10) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateGender(int gender) {
        if (gender == 1 || gender == 2) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateAddress(String address) {
        if (address == null || address.length() > 10) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateDepartmentID(int deptID) {
        if (deptID > 4 || deptID < 1) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.length() > 5) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidDate(String str) {
        String formatPattern = "yyyy/MM/dd";

        SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(str);
        } catch (ParseException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public static boolean isCancelKey(int input) {
        if (input == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCancelKey(String input) {
        if (input.equals("0") == true) {
            return true;
        } else {
            return false;
        }
    }
}
