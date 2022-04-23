package com.app.visualizationbreak.Model.gameorganisator;

import com.app.visualizationbreak.Model.player.Player;

public class Game {
    private static Game instance ;

    private Game(){
    }

    public static Game getInstance(){
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }

    public void initParty(String mode){
        Party.getInstance(mode) ;
    }

    public void setPlayerName(String text) {
        Player.getInstance().setName(text);
    }
}
