package com.tilldawn.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.tilldawn.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileController {
    private static final String FILE_NAME = "users.json";
    private static final List<User> users;
    private static User currentUser = User.getCurrentUser();

    static {
        FileHandle file = Gdx.files.local(FILE_NAME);
        if (file.exists()) {
            users = new ArrayList<>(Arrays.asList(new Json().fromJson(User[].class, file)));
        } else {
            users = new ArrayList<>();
        }
    }


    public static boolean updateUsername(String newUsername) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(newUsername)) return false;
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(currentUser.getUsername())) {
                currentUser.setUsername(newUsername);
                users.set(i, currentUser);
                saveUsers();
                return true;
            }
        }
        return false;
    }

    public static boolean updatePassword(String newPassword) {
        if (!newPassword.matches("(?=.*[A-Z])(?=.*[@%$#&*()_])(?=.*[0-9]).{8,}")) return false;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(currentUser.getUsername())) {
                currentUser.setPassword(newPassword);
                users.set(i, currentUser);
                saveUsers();
                return true;
            }
        }
        return false;
    }

    public static void updateAvatar(String path) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(currentUser.getUsername())) {
                currentUser.setAvatar(path);
                users.set(i, currentUser);
                saveUsers();
                return;
            }
        }
    }

    public static void deleteAccount() {
        users.removeIf(u -> u.getUsername().equals(currentUser.getUsername()));
        saveUsers();
        currentUser = null;
    }

    private static void saveUsers() {
        new Json().toJson(users, Gdx.files.local(FILE_NAME));
    }
}
