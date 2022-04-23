package com.app.visualizationbreak.Model.serialization;

import java.io.Serializable;

public class ScoreSaved implements Serializable {
    private final int score;
    private final String name;

    public ScoreSaved(int score, String name){
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }
    
    public String getName(){
        return this.name;
    }
}
