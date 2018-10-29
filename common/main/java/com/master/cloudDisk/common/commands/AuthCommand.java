package com.master.cloudDisk.common.commands;

public class AuthCommand extends Command {
    public final static String salt = "SuPer_SaLt!";
    private String login;
    private String password;

    public AuthCommand(String login, String password) {
        this.login = login;
        this.password = password;
    }

//    public void execute() {
//        System.out.println("Get AUTH! Logiin: " + login + "Pass: " + password);
//    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
