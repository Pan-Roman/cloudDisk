package com.master.cloudDisk.common;

import com.master.cloudDisk.common.Interfaces.ICommand;

import java.io.Serializable;

public class AuthCommand implements ICommand, Serializable {
    public final static String salt = "SuPer_SaLt!";
    private String login;
    private String password;

    public AuthCommand(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void execute() {
//        System.out.println("Get AUTH! Logiin: " + login + "Pass: " + password);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
