package core;

import java.util.List;

import database.DatabaseManager;

public class UserManagement {

    public void start() {
        DatabaseManager databaseManager = new DatabaseManager();
        List<User> usersList;
        int choice = -1;
        do {
            choice = Display.showMenu();
            switch (choice) {
            case 1:
                usersList = databaseManager.getAllUser();
                Display.showUsersList(usersList);
                break;
            case 2:
                usersList = Search.search();
                if (usersList == null) {
                    continue;
                }
                Display.showUsersList(usersList);
                break;
            case 3:
                User newUser = Display.showInputUserFrame();
                if (newUser == null) {
                    continue;
                }
                int insertedCount = databaseManager.insertNewEmployee(newUser);
                Display.showInsertedCount(insertedCount);
                break;
            case 4:
                int updateEmpID = Display.showInputIDForUpdateFrame();
                if (updateEmpID == 0) {
                    continue;
                }
                if (databaseManager.isUserExisted(updateEmpID) == true) {
                    User updatedUser = Display.showInputUserFrame();
                    updatedUser.setID(updateEmpID);
                    int updatedCount = databaseManager
                            .updateExistedEmployee(updatedUser);
                    Display.showUpdatedCount(updatedCount);
                } else {
                    Display.showError(Display.ERROR_USER_NOT_EXIST);
                }
                break;
            case 5:
                int deleteEmpID = Display.showInputIDForDeleteFrame();
                if (deleteEmpID == 0) {
                    continue;
                }
                int deletedCount = databaseManager.deleteEmployee(deleteEmpID);
                Display.showDeletedCount(deletedCount);
                break;
            default:
                break;
            }
        } while (choice != 0);
        databaseManager.close();
    }
}
