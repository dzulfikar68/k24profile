package io.github.dzulfikar68.k24memberapp.controller;

public class IsAdminUtil {
    public boolean login(String username, String password) {
        return username.equals(Config.usernameAdmin) && password.equals(Config.passwordAdmin);
    }
}
