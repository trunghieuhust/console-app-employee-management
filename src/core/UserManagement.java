package core;

import java.util.List;

import database.DatabaseManager;

public class UserManagement {
    DatabaseManager databaseManager;

    public void start() {
        databaseManager = new DatabaseManager();
        List<User> usersList;
        int choice = -1;

        // 認証が失敗すれば、システムが終了されます。
        if (authorize() == false) {
            System.exit(1);
        }
        do {
            choice = View.showMenu();
            switch (choice) {
            case 1:
                usersList = databaseManager.getAllUser();
                View.showUsersList(usersList);
                break;
            case 2:
                usersList = Search.search();
                if (usersList == null) {
                    continue;
                }
                View.showUsersList(usersList);
                break;
            case 3:
                User newUser = View.showInputUserFrame();
                if (newUser == null) {
                    continue;
                }
                int insertedCount = databaseManager.insertNewEmployee(newUser);
                View.showInsertedCount(insertedCount);
                break;
            case 4:
                int updateEmpID = View.showInputIDForUpdateFrame();
                if (updateEmpID == 0) {
                    continue;
                }
                if (databaseManager.isUserExisted(updateEmpID) == true) {
                    // チェックしたから、ユーザが存在しているので、このクエリは必ず列が戻ります。
                    User originalUser = databaseManager.searchByID(updateEmpID)
                            .get(0);
                    User updatedUser = View.showInputUserFrame();
                    insertUnchageField(originalUser, updatedUser);
                    updatedUser.setID(updateEmpID);
                    int updatedCount = databaseManager
                            .updateExistedEmployee(updatedUser);
                    View.showUpdatedCount(updatedCount);
                } else {
                    View.showError(View.ERROR_USER_NOT_EXIST);
                }
                break;
            case 5:
                int deleteEmpID = View.showInputIDForDeleteFrame();
                if (deleteEmpID == 0) {
                    continue;
                }
                int deletedCount = databaseManager.deleteEmployee(deleteEmpID);
                View.showDeletedCount(deletedCount);
                break;
            default:
                break;
            }
        } while (choice != 0);
        databaseManager.close();
    }

    private boolean authorize() {
        int id = View.showInputUserIDForLoginFrame();
        String password = View.showInputUserPasswordForLoginFrame();
        if (databaseManager.isAdmin(id) == true) {
            if (databaseManager.authenticate(id, password) == true) {
                return true;
            } else {
                View.showError(View.ERROR_USER_AND_PASSWORD_MISMATCH);
                return false;
            }
        } else {
            View.showError(View.ERROR_USER_NOT_IN_ADMIN_GROUP);
        }
        return false;
    }

    // TODO ko can nhap lai khi update, enter to skip
    private void insertUnchageField(User originalUser, User updatedUser) {
        if (updatedUser.getAddress() == null
                || updatedUser.getAddress().length() == 0) {
            updatedUser.setAddress(originalUser.getAddress());
        }
        if (updatedUser.getBirthday() == null) {
            updatedUser.setBirthday(originalUser.getBirthday());
        }
        if (updatedUser.getDeptID() == 0) {
            updatedUser.setDeptID(originalUser.getDeptID());
        }
        if (updatedUser.getDeptName() == null
                || updatedUser.getDeptName().length() == 0) {
            updatedUser.setDeptName(originalUser.getDeptName());
        }
        if (updatedUser.getGender() == 0) {
            updatedUser.setGender(originalUser.getGender());
        }
        if (updatedUser.getName() == null
                || updatedUser.getName().length() == 0) {
            updatedUser.setName(originalUser.getName());
        }
        if (updatedUser.getPass() == null
                || updatedUser.getPass().length() == 0) {
            updatedUser.setPass(originalUser.getPass());
        }
    }
}
