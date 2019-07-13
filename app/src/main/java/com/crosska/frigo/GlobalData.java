package com.crosska.frigo;

import android.app.Application;

public class GlobalData extends Application {

    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String new_login) {
        login = new_login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String new_password) {
        password = new_password;
    }

}
