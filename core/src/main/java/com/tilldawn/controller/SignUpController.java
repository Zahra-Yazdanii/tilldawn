package com.tilldawn.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.tilldawn.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUpController {
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

    public static boolean isUsernameTaken(String username) {
        return users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }

    public static void registerUser(String username, String password, String question, String answer, String avatar) {
        users.add(new User(username, password, question, answer, avatar));
        new Json().toJson(users, Gdx.files.local(FILE_NAME));
    }
}
