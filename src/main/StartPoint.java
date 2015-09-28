package main;

import core.UserManagement;

public class StartPoint {
    public static void main(String[] args) {
        UserManagement userManagement = new UserManagement();

        userManagement.start();

        System.out.println("システムを終了します。\tお疲れ様でした。");
    }
}
