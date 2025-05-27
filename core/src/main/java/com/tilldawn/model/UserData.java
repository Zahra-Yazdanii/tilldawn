package com.tilldawn.model;

public class UserData {
    public String username;
    public String password;
    public String securityQuestion;
    public String securityAnswer;
    public String avatarPath;

    public UserData() {}

    public UserData(String username, String password, String question, String answer, String avatarPath) {
        this.username = username;
        this.password = password;
        this.securityQuestion = question;
        this.securityAnswer = answer;
        this.avatarPath = avatarPath;
    }
}
