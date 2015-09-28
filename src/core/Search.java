package core;

import java.util.ArrayList;
import java.util.List;

import util.Util;

import database.DatabaseManager;

public class Search {
    private static DatabaseManager databaseManager = new DatabaseManager();

    public static List<User> search() {
        int searchChoice = -1;
        List<User> usersList = new ArrayList<User>();
        searchChoice = Display.showSearchMenu();
        String keyword = Display.showInputSearchFrame();

        switch (searchChoice) {
        case 1:
            do {
                int ID = Util.parseInt(keyword);
                if (Validator.validateID(ID) == true) {
                    usersList = databaseManager.searchByID(ID);
                    break;
                } else {
                    Display.showError(Display.ERROR_INVALID_EMP_ID);
                }
            } while (true);
            break;
        case 2:
            do {
                if (Validator.validateName(keyword) == true) {
                    usersList = databaseManager.searchByName(keyword);
                    break;
                } else {
                    Display.showError(Display.ERROR_NAME_IS_TOO_LONG);
                }
            } while (true);
            break;
        case 3:
            do {
                int gender = Util.parseInt(keyword);
                if (Validator.validateGender(gender) == true) {
                    usersList = databaseManager.searchByGender(gender);
                    break;
                } else {
                    Display.showError(Display.ERROR_INVALID_GENDER);
                }

            } while (true);
            break;
        case 4:
            do {
                int deptID = Util.parseInt(keyword);
                if (Validator.validateDepartmentID(deptID) == true) {
                    usersList = databaseManager.searchByDepartmentID(deptID);
                    break;
                } else {
                    Display.showError(Display.ERROR_INVALID_DEPARTMENT);
                }
            } while (true);
            break;

        default:
            break;
        }
        return usersList;
    }
}