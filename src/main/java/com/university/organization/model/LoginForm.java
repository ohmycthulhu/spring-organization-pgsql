package com.university.organization.model;

public class LoginForm {
    private String login;
    private String password;

    public LoginForm(
            String login,
            String password
    ) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
