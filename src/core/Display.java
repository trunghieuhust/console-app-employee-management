package core;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import util.Util;

public class Display {
    public static final int ERROR_UNKOWN_ERROR = 0;
    public static final int ERROR_INVALID_MENU_OPTION = 1;
    public static final int ERROR_INVALID_SEARCH_OPTION = 2;
    public static final int ERROR_INVALID_EMP_ID = 3;
    public static final int ERROR_PASSWORD_IS_TOO_LONG = 4;
    public static final int ERROR_NAME_IS_TOO_LONG = 5;
    public static final int ERROR_INVALID_GENDER = 6;
    public static final int ERROR_ADDRESS_IS_TOO_LONG = 7;
    public static final int ERROR_INVALID_DEPARTMENT = 8;
    public static final int ERROR_CANNOT_CLOSE_DATABASE = 9;
    public static final int ERROR_INVALID_DATE_FORMAT = 10;
    public static final int ERROR_USER_NOT_EXIST = 11;
    public static final int ERROR_ON_SEARCH = 12;
    public static final int ERROR_ON_INSERT = 13;
    public static final int ERROR_ON_DELETE = 14;
    public static final int ERROR_ON_UPDATE = 15;
    public static final int ERROR_USER_AND_PASSWORD_MISMATCH = 16;
    public static final int ERROR_USER_NOT_IN_ADMIN_GROUP = 17;

    public static final int MESSAGE_DATABASE_HAS_BEEN_CLOSED = 20;

    private static final String[] ERROR = { "未知のエラーが発生しました。",
            " 0～5 の数字を入力してください", "1～4 の数字を入力してください", "数字を入力してください(5 桁以内) ",
            "パスワードは 5 文字以下で入力してください", "社員名は 10 文字以下で入力してください",
            "1～2 の数字を入力してください ", "住所は 10 文字以下で入力してください", "1～4 の数字を入力してください ",
            "エラーが発生しました。切断できません。", "YYYY/MM/DDの形式で入力してください。", "ユーザが存在しません。",
            "DB文字列検索中にエラーが発生しました。", "DBデータ登録中にエラーが発生しました。",
            "DBデータ削除中にエラーが発生しました。", "DBデータ更新中にエラーが発生しました。", "IDまたはパスワードが違います。",
            "Adminグルプではありませんから、ログインできません。" };

    private static final String[] MESSAGE = { "切断しました。" };

    public static int showMenu() {
        System.out.println("-----------------");
        System.out.println("社員管理システム");
        System.out.println("-----------------");
        System.out.println("1．全件表示");
        System.out.println("2．検索表示");
        System.out.println("3．登録");
        System.out.println("4．更新");
        System.out.println("5．削除");
        System.out.println("0．終了");
        System.out.print("数字で項目を選んでください--->");

        int menuChoice;
        do {
            menuChoice = Util.readInt();
            boolean isValid = Validator.validateMenuChoice(menuChoice);
            if (!isValid) {
                showError(ERROR_INVALID_MENU_OPTION);
            } else {
                break;
            }
        } while (true);

        return menuChoice;
    }

    public static int showSearchMenu() {
        System.out.println("1．社員ID");
        System.out.println("2．社員名");
        System.out.println("3．性別（1：男、2：女）");
        System.out.println("4．部署ID（１：総務部、2:営業部、3:経理部、4:資材部）");
        System.out.print("検索したい列を数字で入力してください--->");

        int searchChoice;
        do {
            searchChoice = Util.readInt();
            boolean isValid = Validator.validateSearchMenuChoice(searchChoice);
            if (!isValid) {
                showError(ERROR_INVALID_SEARCH_OPTION);
            } else {
                break;
            }
        } while (true);

        return searchChoice;
    }

    public static String showInputSearchFrame() {
        System.out.print("検索したい文字列を入力してください--->");
        return Util.readString();
    }

    public static User showInputUserFrame() {
        User user = new User();
        do {
            System.out.print("登録するパスワード--->");
            String password = Util.readString();
            if (Validator.isCancelKey(password) == true) {
                return null;
            }
            if (Validator.validatePassword(password) == true) {
                user.setPass(password);
                break;
            } else {
                showError(ERROR_PASSWORD_IS_TOO_LONG);
            }
        } while (true);

        do {
            System.out.print("登録する社員名--->");
            String name = Util.readString();
            if (Validator.isCancelKey(name) == true) {
                return null;
            }
            if (Validator.validateName(name) == true) {
                user.setName(name);
                break;
            } else {
                showError(ERROR_NAME_IS_TOO_LONG);
            }
        } while (true);

        do {
            System.out.print("登録する性別（1：男,2：女）--->");
            int gender = Util.readInt();
            // 更新したくない場合、０を代入する。
            if (gender == Integer.MIN_VALUE) {
                user.setGender(0);
                break;
            }
            if (Validator.isCancelKey(gender) == true) {
                return null;
            }
            if (Validator.validateGender(gender) == true) {
                user.setGender(gender);
                break;
            } else {
                showError(ERROR_INVALID_GENDER);
            }
        } while (true);

        do {
            System.out.print("登録する住所--->");
            String address = Util.readString();
            // 更新したくない場合
            if (address.length() == 0) {
                user.setAddress(address);
                break;
            }
            if (Validator.isCancelKey(address) == true) {
                return null;
            }
            if (Validator.validateAddress(address) == true) {
                user.setAddress(address);
                break;
            } else {
                showError(ERROR_ADDRESS_IS_TOO_LONG);
            }
        } while (true);

        do {
            System.out.print("登録する誕生日（YYYY/MM/DDの形式で入力してください）--->");
            String date = Util.readString();
            // 更新したくない場合
            if (date.length() == 0) {
                user.setBirthday(null);
                break;
            }
            if (Validator.isCancelKey(date) == true) {
                return null;
            }

            if (Validator.isValidDate(date) == true) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                java.util.Date birthday = null;
                try {
                    birthday = format.parse(date);
                } catch (ParseException e) {
                    // 処理がいらない
                }
                java.sql.Date sqlDate = new Date(birthday.getTime());
                user.setBirthday(sqlDate);
                break;
            } else {
                showError(ERROR_INVALID_DATE_FORMAT);
            }
        } while (true);

        do {
            System.out.print("登録する部署ID（1：総務部、2：営業部、3：経理部、4：資材部）--->");
            // 更新したくない場合
            int deptID = Util.readInt();
            if (deptID == Integer.MIN_VALUE) {
                user.setDeptID(0);
                break;
            }
            if (Validator.isCancelKey(deptID) == true) {
                return null;
            }
            if (Validator.validateDepartmentID(deptID) == true) {
                user.setDeptID(deptID);
                break;
            } else {
                showError(ERROR_INVALID_DEPARTMENT);
            }
        } while (true);

        return user;
    }

    public static int showInputIDForUpdateFrame() {
        System.out.print("更新したい社員ID--->");
        do {
            int ID = Util.readInt();
            if (Validator.validateID(ID) == true) {
                return ID;
            } else {
                showError(ERROR_INVALID_EMP_ID);
            }
        } while (true);
    }

    public static int showInputIDForDeleteFrame() {
        System.out.print("削除したい社員ID--->");
        do {
            int ID = Util.readInt();
            if (Validator.validateID(ID) == true) {
                return ID;
            } else {
                showError(ERROR_INVALID_EMP_ID);
            }
        } while (true);
    }

    public static void showUsersList(List<User> usersList) {
        System.out.println("社員ID\tパスワード\t社員名\t\t性別\t住所\t誕生日\t\t部署名");

        for (User user : usersList) {
            System.out.print(user.getID() + "\t");
            System.out.print(user.getPass() + "\t\t");
            System.out.print(user.getName() + "\t");
            System.out.print(user.getGender() + "\t");
            System.out.print(user.getAddress() + "\t");
            System.out.print(user.getBirthday().toString() + "\t");
            System.out.println(user.getDeptName() + "\t");
        }
    }

    public static int showInputUserIDForLoginFrame() {
        do {
            System.out.println("ログイン");
            System.out.print("ID--->");
            int id = Util.readInt();
            if (id == Integer.MIN_VALUE) {
                Display.showError(ERROR_INVALID_EMP_ID);
                continue;
            } else {
                return id;
            }
        } while (true);

    }

    public static String showInputUserPasswordForLoginFrame() {
        System.out.print("パスワード--->");
        return Util.readString();
    }

    public static void showInsertedCount(int count) {
        System.out.println(count + "件登録しました。");
    }

    public static void showUpdatedCount(int count) {
        System.out.println(count + "件更新しました。");
    }

    public static void showDeletedCount(int count) {
        System.out.println(count + "件削除しました。");
    }

    public static void showMessage(int messageCode) {
        if (messageCode > 19 && messageCode < MESSAGE.length + 20) {
            System.out.println(MESSAGE[messageCode - 20]);
        }
    }

    public static void showError(int errorCode) {
        if (errorCode >= 0 && errorCode < ERROR.length) {
            System.err.println("※※※※" + ERROR[errorCode] + "※※※※");
        } else {
            System.err.println("※※※※" + ERROR[0] + "※※※※");
        }
    }
}
