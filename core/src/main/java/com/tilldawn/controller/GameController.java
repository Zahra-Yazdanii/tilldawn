//package com.tilldawn.controller;
//
//
//import com.tilldawn.view.GameView;
//
//public class GameController {
//    private GameView view;
//    private PlayerController playerController;
//    private com.tilldawn.controller.WorldController worldController;
//    private com.tilldawn.Control.WeaponController weaponController;
//
//
//    public void setView(GameView view) {
//        this.view = view;
//        playerController = new PlayerController(new com.tilldawn.model.Player());
//        worldController = new com.tilldawn.controller.WorldController(playerController);
//        weaponController = new com.tilldawn.Control.WeaponController(new com.tilldawn.model.Weapon());
//    }
//
//    public void updateGame() {
//        if (view != null) {
//            worldController.update();
//            playerController.update();
//            weaponController.update();
//        }
//    }
//
//    public PlayerController getPlayerController() {
//        return playerController;
//    }
//
//    public com.tilldawn.Control.WeaponController getWeaponController() {
//        return weaponController;
//    }
//}
