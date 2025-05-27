package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.SignUpController;
import com.tilldawn.controller.MainMenuController;

import java.util.Random;

public class SignUpScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final TextField usernameField, passwordField, answerField;
    private final SelectBox<String> securityQuestionBox;
    private final Label errorLabel;
    private final java.util.List<String> avatars = java.util.Arrays.asList(
        "images/T/T_Abby_Portrait.png",
        "images/T/T_Luna_Portrait.png",
        "images/T/T_Raven_Portrait.png",
        "images/T/T_Spark_Portrait.png"
    );

    public SignUpScreen(Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.usernameField = new TextField("", skin);
        this.passwordField = new TextField("", skin);
        this.answerField = new TextField("", skin);
        this.securityQuestionBox = new SelectBox<>(skin);
        this.errorLabel = new Label("", skin);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Image background = new Image(new Texture(Gdx.files.internal("background_dark_blue_black.png")));
        background.setFillParent(true);
        stage.addActor(background);

        FreeTypeFontGenerator titleGen = new FreeTypeFontGenerator(Gdx.files.internal("Font/ChevyRay - Lantern.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter titleParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParam.size = 48;
        BitmapFont titleFont = titleGen.generateFont(titleParam);
        titleGen.dispose();

        FreeTypeFontGenerator fieldGen = new FreeTypeFontGenerator(Gdx.files.internal("Font/OpenSans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 28;

        BitmapFont font = fieldGen.generateFont(param);
        font.setColor(Color.WHITE);

        BitmapFont fontHint = fieldGen.generateFont(param);
        fontHint.setColor(new Color(0.6f, 0.85f, 0.9f, 1f));

        fieldGen.dispose();


        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture whiteTexture = new Texture(pixmap);
        pixmap.dispose();
        Drawable whiteDrawable = new TextureRegionDrawable(new TextureRegion(whiteTexture));


        TextField.TextFieldStyle fieldStyle = new TextField.TextFieldStyle();
        fieldStyle.font = font;
        fieldStyle.messageFont = fontHint;
        fieldStyle.fontColor = Color.BLACK;
        fieldStyle.cursor = whiteDrawable;
        fieldStyle.selection = whiteDrawable;
        fieldStyle.background = usernameField.getStyle().background;

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);
        Label titleLabel = new Label("SignUp Menu", skin, "title");
        table.add(titleLabel).padBottom(40).row();

//        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Color.WHITE);
//        Label title = new Label("S I G N   U P", titleStyle);
//        table.add(title).padBottom(40).row();

        // یوزرنیم
        usernameField.setMessageText("Enter Username");
        usernameField.setStyle(fieldStyle);
        table.add(usernameField).width(500).height(100).padBottom(15).row();

        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("Enter Password");
        passwordField.setStyle(fieldStyle);
        table.add(passwordField).width(500).height(100).padBottom(15).row();

        Label questionLabel = new Label("Select Your Security Question", skin);
        table.add(questionLabel).padBottom(5).row();

        securityQuestionBox.getStyle().fontColor = Color.WHITE;
        securityQuestionBox.setItems(
            "Your pet's name?",
            "Mother's maiden name?",
            "Favorite color?"
        );
        table.add(securityQuestionBox).width(500).height(80).padBottom(15).row();

        // پاسخ سوال امنیتی
        answerField.setMessageText("Your Answer");
        answerField.setAlignment(Align.center);
        answerField.setStyle(fieldStyle);
        table.add(answerField).width(500).height(100).padBottom(15).row();

        errorLabel.setColor(Color.RED);
        table.add(errorLabel).padBottom(15).row();

        TextButton registerButton = new TextButton("Register", skin);
        registerButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                if (register()) {
                    errorLabel.setColor(Color.GREEN);
                    errorLabel.setText("Registered successfully!");
                }
            }
        });

        TextButton backButton = new TextButton("Go Back", skin);
        backButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });

        Table btnRow = new Table();
        btnRow.add(registerButton).width(235).height(120).padRight(30);
        btnRow.add(backButton).width(235).height(120);
        table.add(btnRow).padTop(20);
    }




    private boolean register() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String question = securityQuestionBox.getSelected();
        String answer = answerField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || answer.isEmpty()) {
            errorLabel.setColor(Color.RED);
            errorLabel.setText("Fields cannot be empty.");
            return false;
        }

        if (!password.matches("(?=.*[A-Z])(?=.*[@%$#&*()_])(?=.*[0-9]).{8,}")) {
            errorLabel.setColor(Color.RED);
            errorLabel.setText("Weak password.");
            return false;
        }

        if (SignUpController.isUsernameTaken(username)) {
            errorLabel.setColor(Color.RED);
            errorLabel.setText("Username already exists.");
            return false;
        }

        String avatar = avatars.get(new Random().nextInt(avatars.size()));
        SignUpController.registerUser(username, password, question, answer, avatar);
        return true;
    }
    private TextField.TextFieldStyle createTextFieldStyle(BitmapFont font, Drawable background) {
        // ساخت Drawable سفید برای کرسر و انتخاب
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture whiteTexture = new Texture(pixmap);
        Drawable whiteDrawable = new TextureRegionDrawable(new TextureRegion(whiteTexture));

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        style.cursor = whiteDrawable;
        style.selection = whiteDrawable;
        style.background = background;

        return style;
    }

    @Override public void render(float delta) { stage.act(delta); stage.draw(); }
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
