package com.tilldawn.model;

import com.tilldawn.model.enums.HeroType;
import com.tilldawn.model.enums.WeaponType;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username, password, question, answer, avatar;
    private List<GameRecord> gameRecords = new ArrayList<>();
    private static User currentUser;
    private static User GUEST_USER;
    private HeroType selectedHero;
   // private String selectedHero;
    private WeaponType selectedWeapon;
    private int selectedDuration;
    private boolean autoReloadEnabled = true;

    public User() {}

    public User(String username, String password, String question, String answer, String avatar) {
        this.username = username;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.avatar = avatar;
    }

    public static User getCurrentUser() {
        if (currentUser == null) {
            return GUEST_USER;
        }
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser =  user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public List<GameRecord> getGameRecords() {
        return gameRecords;
    }

    public void addGameRecord(GameRecord record) {
        gameRecords.add(record);
    }

    public GameRecord getLastFinishedGame() {
        for (int i = gameRecords.size() - 1; i >= 0; i--) {
            GameRecord record = gameRecords.get(i);
            if (record.isFinished()) return record;
        }
        return null;
    }

    public int getTotalScore() {
        int sum = 0;
        for (GameRecord r : gameRecords) {
            sum += r.getScore();
        }
        return sum;
    }
    public UserData toUserData() {
        return new UserData(username, password, question, answer, avatar);
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public User(UserData data) {
        this.username = data.username;
        this.password = data.password;
        this.question = data.securityQuestion;
        this.answer = data.securityAnswer;
        this.avatar = data.avatarPath;
    }

    public void setSelectedDuration(int duration) { this.selectedDuration = duration; }
    public HeroType getSelectedHero() {
        return selectedHero;
    }

    public void setSelectedHero(HeroType selectedHero) {
        this.selectedHero = selectedHero;
    }


    public WeaponType getSelectedWeapon() { return selectedWeapon; }
    public void setSelectedWeapon(WeaponType selectedWeapon) { this.selectedWeapon = selectedWeapon; }

    public int getSelectedDuration() { return selectedDuration; }
    public boolean isAutoReloadEnabled() {
        return autoReloadEnabled;
    }

    public void setAutoReloadEnabled(boolean autoReloadEnabled) {
        this.autoReloadEnabled = autoReloadEnabled;
    }
}

