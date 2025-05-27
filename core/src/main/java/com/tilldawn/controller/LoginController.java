package com.tilldawn.controller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.tilldawn.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginController {
    private static final String FILE_NAME = "users.json";
    private static final List<User> users;

    static {
        FileHandle file = Gdx.files.local(FILE_NAME);
        if (file.exists()) {
            users = new ArrayList<>(Arrays.asList(new Json().fromJson(User[].class, file)));
        } else {
            users = new ArrayList<>();
        }
    }

    public static boolean validateLogin(String username, String password) {
        return users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password));
    }

    public static User getUserByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);
    }

    public static boolean resetPassword(String username, String answer, String newPassword) {
        User user = getUserByUsername(username);
        if (user != null && user.getAnswer().equalsIgnoreCase(answer)) {
            user.setPassword(newPassword);
            new Json().toJson(users, Gdx.files.local(FILE_NAME));
            return true;
        }
        return false;
    }
}
