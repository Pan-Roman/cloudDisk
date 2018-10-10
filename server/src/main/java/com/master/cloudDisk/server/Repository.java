package com.master.cloudDisk.server;

public class Repository {

    public static boolean checkAuthData(String userName, String passHash){
        String query = "SELECT pass FROM users WHERE userName = " + userName;
        // TODO: Реализовать подключение к БД и ТД и ТП
        String tempPass = "password";
        return (tempPass == passHash);
    }
}
