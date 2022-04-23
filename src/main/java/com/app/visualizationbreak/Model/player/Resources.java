package com.app.visualizationbreak.Model.player;

import com.app.visualizationbreak.Model.gameorganisator.Party;

import static java.lang.Thread.sleep;

public class Resources implements Runnable {
    private int money = 100;              //valeur initiale d'argent
    private int adding = 2;
    private final boolean isEligible ;

    // constructeur
    public Resources() {
        isEligible = false;
    }

    //accesseur lecture
    public int getMoney() {
        return money;
    }

    //accesseur ecriture
    public void setMoney(int newmoney) {
        this.money = newmoney;
    }

    //accesseur ecriture


    public void setAdding(int adding) {
        this.adding = adding;
    }

    //méthode qui rajoute de l'argent
    public synchronized void addMoney(int reward) {
        money += reward;
    }


    //methode qui permet de compter le nombre de pièces que rapportent les Farmers
    public int countMoney() {
        int res = 0;
        /*for (Farmer f : myFarmers) {
            res += f.getProduction();
        }*/
        return res;
    }

    @Override
    public void run() {
        while (Player.getInstance().stillAlive()) {
            while(Party.getGameSpeed() == 0){
                try{
                    sleep(50);                    //on attend 50 ms pour voir si la vitesse du jeu a changé
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (Player.getInstance().getInWave() && Party.getInstance().NotInPause()) {
                //argent qu'on va rajouter de base aux ressources
                this.addMoney(adding + countMoney());
            }
            try {
                sleep(5000/Party.getGameSpeed()); //toutes les 5 secondes, on va permettre de rajouter de l'argent
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}