package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Array;

public class UserStore {
    private static final String FILE_PATH = "users.json";
    private Array<UserData> users;
    private Json json;

    public UserStore() {
        json = new Json();
        loadUsers();
    }

    public void loadUsers() {
        FileHandle file = Gdx.files.local(FILE_PATH);
        if (file.exists()) {
            users = json.fromJson(Array.class, UserData.class, file);
        } else {
            users = new Array<>();
        }
    }

    public void saveUsers() {
        FileHandle file = Gdx.files.local(FILE_PATH);
        file.writeString(json.toJson(users), false);
    }

    public Array<UserData> getUsers() {
        return users;
    }

    public boolean isUsernameTaken(String username) {
        for (UserData user : users) {
            if (user.username.equals(username)) return true;
        }
        return false;
    }

    public void addUser(UserData user) {
        users.add(user);
        saveUsers();
    }
}
