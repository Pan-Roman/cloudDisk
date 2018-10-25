package com.master.cloudDisk.server;

public class Repository {

    public static boolean checkAuthData(String userName, String passHash){
        String query = "SELECT pass FROM users WHERE userName = " + userName;
        // TODO: Реализовать подключение к БД и ТД и ТП
//                        b7723cf84cad26e6c9a32f02fa7a06cb
        String pass = "b7723cf84cad26e6c9a32f02fa7a06cb";
        return pass.equals(passHash);
    }
}
