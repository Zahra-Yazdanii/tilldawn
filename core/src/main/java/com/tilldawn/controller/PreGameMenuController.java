//package com.tilldawn.controller;
//
//import com.tilldawn.Main;
//import com.tilldawn.model.GameAssetManager;
//import com.tilldawn.model.Pregame;
////import com.tilldawn.view.GameView;
//import com.tilldawn.view.PreGameMenuView;
//
//
//public class PreGameMenuController {
//    private PreGameMenuView view;
//    private Pregame pregame;
//
//
//    public void setView(PreGameMenuView view) {
//        this.view = view;
//        this.pregame = new Pregame();
//    }
//
//    public void handlePreGameMenuButtons() {
//        if (view != null) {
//            Main.getMain().getScreen().dispose();
//            Main.getMain().setScreen(new GameView(new GameController(), GameAssetManager.getGameAssetManager().getSkin()));
//        }
//    }
//
//}
