package com.app.visualizationbreak.Model.player;

import com.app.visualizationbreak.Controller.observers.BigTowerObserving;
import com.app.visualizationbreak.Model.gameorganisator.Party;

import java.io.IOException;

public class Player implements BigTowerObserving {
    private static Player instance = null ;
    private String name ;
    private int score = 0 ;  //score de base
    private int life = 50;   //vie de base
    private boolean inWave ; //booleen qui indique si le joueur est dans une vague
    private boolean inLevel;
    private final Resources ressources ;

    private Player(){
        this.ressources = new Resources();
        Thread t1 = new Thread(ressources);
        inLevel = false ;
        inWave = false ;
        t1.start();
    }

    public static Player getInstance() {
        if (Player.instance == null){
            Player.instance = new Player();
        }
        return Player.instance;
    }

    public static void setInstance(Player instance) {
        Player.instance = instance;
    }

    public String getName() {
        if(name == null){
            name = "Training";
        }
        return this.name;
    }
    //Accessor
    public void setName(String s){ name = s;}

    public Resources getResources() { return this.ressources;}

    public int getLife() {
        return life;
    }

    public void setLife(int newLife){this.life = newLife;}

    public boolean getInWave(){return inWave;}

    public void setInWave(boolean x){inWave = x;}

    public boolean getInLevel(){return  inLevel ;}

    public void setInLevel(boolean x){this.inLevel = x;}

    public boolean stillAlive(){return life > 0;}

    public int getScore(){
        return score;
    }

    public void setScore(int nscore){
        score = nscore;
    }

    @Override
    public void update(int damage) {
        if (this.life > damage){
            this.life = life - damage;
        }
        else{
            try {
                this.life = 0;
                Party.getInstance().finish();   //envoie de message à la party, pour la finir
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void makeInWave() { inWave = true ;}
    public void makeNotInWave() { inWave = false ;}
}
