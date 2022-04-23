package com.app.visualizationbreak.Controller.freemode;

import com.app.visualizationbreak.View.panel.FreeModeDrawing;
import com.app.visualizationbreak.utilities.StringMeth;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;

// Free mode drawing : gère les input du clavier (flèches) en verifiant que le chemin est valide

// case '0' : vide
// case '1' : chemin
public class FMDrawingHandler implements EventHandler<KeyEvent> {
    private final int mapSize;
    private final int initRowNum;
    private int rowNum;
    private int colNum ;
    private final Pane drawing ;
    private final Color voidColor = Color.MEDIUMPURPLE;
    private final Color pathColor = Color.SLATEGREY;
    private final FreeModeDrawing drawingMode ;
    private final ArrayList<ArrayList<Rectangle>> tileList ;
    private String sequence ;                                   // le String du style "RRRUUUUDD" où R = right, etc
                                                                // qui déterminera le chemin dessiné
    private final ArrayList<char[]> charList ;

    public FMDrawingHandler(FreeModeDrawing drawingMode){
        this.drawingMode = drawingMode ;
        this.mapSize = drawingMode.getMapWidth() ;
        this.drawing = drawingMode.getDrawing() ;
        this.initRowNum = drawingMode.getFirstTileLoc() ;       // localisation de la premiere case pour savoir
                                                                // comment dessiner le chemin
        this.rowNum = drawingMode.getFirstTileLoc() ;
        charList = new ArrayList<>();
        this.colNum = 0 ;
        this.tileList = drawingMode.getTileList() ;             // initiation d'une liste de [0,0,0,...,0]
                                                                // pour dessiner la map sous forme de 1 et de 0 selon
                                                                // le chemin dessiné
        for(int i = 0; i<mapSize; i++){
            char[] line = new char[mapSize] ;
            for(int j = 0 ; j < mapSize ; j ++){
                line[j] = '0' ;
            }
            charList.add(line);
        }
        sequence = "" ;
    }


    // les intersections sont des cases particulières : on ne peut pas faire ce qu'on veut. Si on vient de la
    // gauche, donc vers la droite et on croise un chemin vertical, on va le traverser automatiquement (donc on saute 4 cases)
    // si on veut revenir en arrière alors qu'on vient de passer une case intersection, il faudra faire un bond en arrière
    // de 4 cases aussi

    @Override
    public void handle(KeyEvent event) {
        // event 1 : si on veut retourner en arrière
        if(event.getCode() == KeyCode.BACK_SPACE && sequence.length()>=1){
            // si la case est une case intersection, on fera un bond en arrière plus grand
            boolean caseIntersect = false ;
            // on remet la case actuelle en case "vide"
            tileList.get(rowNum).get(colNum).setFill(voidColor);
            charList.get(rowNum)[colNum] = '0' ;
            if(sequence.charAt(sequence.length()-1) == 'R'){ // si on vient de la gauche (vers la droite)
                // si on est loin des bords et qu'on vient d'une case intersection
                if(colNum-4>=1){
                    if(charList.get(rowNum)[colNum-2] == '1'
                            && charList.get(rowNum+1)[colNum-2] == '1'
                            && charList.get(rowNum-1)[colNum-2] == '1'){
                        tileList.get(rowNum).get(colNum-1).setFill(voidColor);
                        tileList.get(rowNum).get(colNum-3).setFill(voidColor);
                        // on reset toutes les cases concernées sauf celle déjà là (l'intersection)
                        // et on retourne 4 cases en arrière
                        charList.get(rowNum)[colNum-1] = '0';
                        charList.get(rowNum)[colNum-2] = '1';
                        charList.get(rowNum)[colNum-3] = '0';
                        charList.get(rowNum)[colNum-4] = '1' ;
                        colNum -= 4 ;
                        caseIntersect = true ;
                    }
                    // si on vient pas d'une intersection, on revient en arrière d'une case
                    else{
                        colNum -=1 ;
                    }
                }
                else{
                    colNum -=1 ;
                }
            }
            // si on vient de la droite, idem 
            else if (sequence.charAt(sequence.length()-1) == 'L'){
                if(colNum<mapSize-4){
                    if(charList.get(rowNum)[colNum+2] == '1'
                            && charList.get(rowNum+1)[colNum+2] == '1'
                            && charList.get(rowNum-1)[colNum+2] == '1'){
                        tileList.get(rowNum).get(colNum+1).setFill(voidColor);
                        tileList.get(rowNum).get(colNum+3).setFill(voidColor);
                        charList.get(rowNum)[colNum+1] = '0';
                        charList.get(rowNum)[colNum+2] = '1';
                        charList.get(rowNum)[colNum+3] = '0';
                        charList.get(rowNum)[colNum+4] = '1' ;
                        colNum += 4 ;
                        caseIntersect = true ;
                    }
                    else{
                        colNum +=1 ;
                    }
                }
                else{
                    colNum +=1 ;
                }
            }

            else if (sequence.charAt(sequence.length()-1) == 'D'){
                if(rowNum>4){
                    if(charList.get(rowNum-2)[colNum] == '1' && isHorIntersection(rowNum-2, colNum)){
                        tileList.get(rowNum-1).get(colNum).setFill(voidColor);
                        tileList.get(rowNum-3).get(colNum).setFill(voidColor);
                        charList.get(rowNum-1)[colNum] = '0';
                        charList.get(rowNum-2)[colNum] = '1';
                        charList.get(rowNum-3)[colNum] = '0';
                        charList.get(rowNum-4)[colNum] = '1' ;
                        rowNum -= 4 ;
                        caseIntersect = true ;
                    }
                    else{
                        rowNum -=1 ;
                    }
                }
                else{
                    rowNum -=1 ;
                }
            }
            else if (sequence.charAt(sequence.length()-1) == 'U'){
                if(rowNum<mapSize-4){
                    if(charList.get(rowNum+2)[colNum] == '1' && isHorIntersection(rowNum+2, colNum)){
                        tileList.get(rowNum+1).get(colNum).setFill(voidColor);
                        tileList.get(rowNum+3).get(colNum).setFill(voidColor);
                        charList.get(rowNum+1)[colNum] = '0';
                        charList.get(rowNum+2)[colNum] = '1';
                        charList.get(rowNum+3)[colNum] = '0';
                        charList.get(rowNum+4)[colNum] = '1' ;
                        rowNum += 4 ;
                        caseIntersect = true ;
                    }
                    else{
                        rowNum +=1 ;
                    }
                }
                else{
                    rowNum +=1 ;
                }
            }
            // une fois tous les cas envisagés, on supprime la séquence correspondante dans le String
            if(caseIntersect){
                sequence = (String) sequence.subSequence(0, sequence.length()-4);
            }
            else{
                sequence = (String) sequence.subSequence(0, sequence.length()-1);
            }
        }
        else {
            try {
                if(event.getCode() == KeyCode.ENTER && testPath(sequence)){ 
                    // si on appuie sur enter et que le chemin est VALIDE (en principe il l'est toujours vu les règles 
                    // qu'on impose) dans le dessin (certaines combinaisons sont interdites)
                    drawingMode.getFMScene().setOnKeyPressed(null);
                    sequence = sequence.toUpperCase() ;
                    //System.out.println(sequence);             // affiche la séquence une fois le chemin fini et validé
                    try {
                        drawingMode.startGame(sequence, initRowNum);        // c'est parti ! en freemode drawing
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{

                    if(event.getCode() == KeyCode.RIGHT && colNum < drawingMode.getMapWidth()-1 ){
                        // si c'est la premiere case à mettre, on peut la mettre dans nptquelle direction (UP, RIGHT, DOWN)
                        if(sequence.length() == 0){
                            colNum+=4 ;
                            sequence+="RRRR" ;
                            for(int i = 0 ; i <4 ; i++){
                                tileList.get(rowNum).get(i).setFill(pathColor);
                                charList.get(rowNum)[i] = '1' ;
                            }
                        }
                        else if(sequence.charAt(sequence.length()-1) != 'L'){
                            if(colNum < mapSize-4){
                                // si la case est occupée
                                if(charList.get(rowNum)[colNum+2] == '1'){
                                    // si c'est une case intersection
                                    if(isVertIntersection(rowNum, colNum+2)){
                                        // et si la case d'arrivée est libre
                                        if(isFree(rowNum, colNum+4)
                                                && !somethingUpOrDown(rowNum, colNum+3)
                                                && !somethingUpOrDown(rowNum, colNum+1)){
                                            for(int i = 1 ; i<4 ; i++){
                                                tileList.get(rowNum).get(colNum+i).setFill(pathColor);
                                                charList.get(rowNum)[colNum+i] = '1';
                                            }
                                            colNum += 4 ;
                                            sequence += "RRRR" ;
                                        }
                                    }
                                }
                                else if(!somethingUpOrDown(rowNum, colNum+1)){
                                    colNum+=1 ;
                                    sequence+="R" ;
                                }
                            }
                            // si c'est pas la premiere case, alors la touche "rightArrow" n'est acceptee que si on ne vient pas de la droite
                            // on refuse les points de rebroussement !
                            else{
                                colNum+=1 ;
                                sequence+="R" ;
                            }
                        }
                    }

                    else if(event.getCode() == KeyCode.UP && rowNum > 1&& sequence.length() !=0){
                        if(sequence.length() == 0) {
                            rowNum -= 1;
                            sequence += "U";
                        }
                        else if (sequence.charAt(sequence.length()-1) != 'D'){
                            if(rowNum>4){
                                // si la case est occupée
                                if(charList.get(rowNum-2)[colNum] == '1'){
                                    // si la case est une intersection
                                    if(isHorIntersection(rowNum-2, colNum)){
                                        if(isFree(rowNum-4, colNum)
                                                && !somethingRightOrLeft(rowNum-1, colNum)
                                                && !somethingRightOrLeft(rowNum-3, colNum)){
                                            for(int i = 1 ; i < 4 ; i ++){
                                                tileList.get(rowNum-i).get(colNum).setFill(pathColor);
                                                charList.get(rowNum-i)[colNum] = '1' ;
                                            }
                                            rowNum -= 4 ;
                                            sequence += "UUUU";
                                        }
                                    }
                                }
                                else if(!somethingRightOrLeft(rowNum-1, colNum)){
                                    rowNum -= 1;
                                    sequence += "U";
                                }
                            }
                            else{
                                rowNum -= 1;
                                sequence += "U";
                            }
                        }
                    }

                    else if(event.getCode() == KeyCode.LEFT && colNum > 1 && sequence.length() !=0){
                        if(sequence.charAt(sequence.length()-1) != 'R'){
                            if (colNum > 4) {
                                // si la case est occupée
                                if(charList.get(rowNum)[colNum-2] == '1'){
                                    // si la case est une intersection
                                    if(isVertIntersection(rowNum, colNum-2)){
                                        if(isFree(rowNum, colNum-4)
                                                && !somethingUpOrDown(rowNum, colNum-1)
                                                && !somethingUpOrDown(rowNum, colNum-3)){
                                            for(int i = 1 ; i<4 ; i++){
                                                tileList.get(rowNum).get(colNum-i).setFill(pathColor);
                                                charList.get(rowNum)[colNum-i] = '1';
                                            }
                                            colNum -= 4 ;
                                            sequence += "LLLL" ;
                                        }
                                    }
                                }
                                else if(!somethingUpOrDown(rowNum, colNum-1)){
                                    colNum-=1 ;
                                    sequence += "L" ;
                                }
                            }
                            else{
                                colNum-=1 ;
                                sequence += "L" ;
                            }
                        }
                    }

                    else if(event.getCode() == KeyCode.DOWN && rowNum < drawingMode.getMapWidth()-1&& sequence.length() !=0){
                        if(sequence.length() ==0) {
                            rowNum += 1;
                            sequence += "D";
                        }
                        else if(sequence.charAt(sequence.length()-1) != 'U'){
                            if(rowNum < mapSize - 4){
                                // si la case est occupée
                                if(charList.get(rowNum+2)[colNum] == '1' ){
                                    // si c'est une case intersection
                                    if(isHorIntersection(rowNum+2, colNum)){
                                        // si la case d'arrivée est libre
                                        if(isFree(rowNum+4, colNum)
                                                && !somethingRightOrLeft(rowNum+1, colNum)
                                                && !somethingRightOrLeft(rowNum+3, colNum)){
                                            for(int i = 1 ; i < 4 ; i ++){
                                                tileList.get(rowNum+i).get(colNum).setFill(pathColor);
                                                charList.get(rowNum+i)[colNum] = '1' ;
                                            }
                                            rowNum += 4 ;
                                            sequence += "DDDD";
                                        }

                                    }
                                }
                                else if(!somethingRightOrLeft(rowNum+1, colNum)){
                                    rowNum += 1;
                                    sequence += "D";
                                }
                            }
                            else{
                                rowNum += 1;
                                sequence += "D";
                            }
                        }
                    }
                    charList.get(rowNum)[colNum] = '1';
                    Rectangle chosenOne = tileList.get(rowNum).get(colNum);
                    chosenOne.setFill(pathColor);
                    chosenOne.setOpacity(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sequence += event.getText() ;
    }
    public boolean somethingUpOrDown(int row, int col){
        return charList.get(row+1)[col] == '1' | charList.get(row-1)[col] == '1';
    }
    public boolean somethingRightOrLeft(int row, int col){
        return charList.get(row)[col+1] == '1' | charList.get(row)[col-1] == '1';
    }
    public boolean isFree(int row, int col){
        return charList.get(row)[col+1] == '0' && charList.get(row)[col-1] == '0' && charList.get(row+1)[col] == '0' &&
                charList.get(row-1)[col] == '0';
    }

    public boolean isHorIntersection(int row, int col){
        return charList.get(row)[col+1] == '1' && charList.get(row)[col-1] == '1' ;
    }
    public boolean isVertIntersection(int row, int col){
        return charList.get(row+1)[col] == '1' && charList.get(row-1)[col] == '1' ;
    }
    
    /*  test de la séquence :
     - on ne peut pas mettre de caractère autre que R, U, L, D
     - il faut que le chemin couvre toute la map horizontalement
     - il ne peut pas sortir de la map
     - il ne doit pas contenir de demi tour soudain et serré comme un "U" très serré (cases collées)
    
     */
    public boolean testPath(String pathSequence){
        int pathError = 1 ;
        int mapError = 1;
        int finalPointError = 1 ;
        int charError = 1 ;
        int numR = StringMeth.count(pathSequence.toUpperCase(), 'R') ;
        int numU = StringMeth.count(pathSequence.toUpperCase(), 'U') ;
        int numL = StringMeth.count(pathSequence.toUpperCase(), 'L') ;
        int numD = StringMeth.count(pathSequence.toUpperCase(), 'D') ;

        if(numR-numL != drawingMode.getMapWidth() -1){
            finalPointError*=0 ;
        }

        if(Math.abs(numU - numD) > drawingMode.getMapWidth()){mapError*=0 ;}
        if(Math.abs(numR - numL) > drawingMode.getMapWidth()){mapError*=0 ;}

        if (pathSequence.contains("RL")){pathError*=0 ;}
        if (pathSequence.contains("LR")){pathError*=0 ;}
        if (pathSequence.contains("UD")){pathError*=0 ;}
        if (pathSequence.contains("DU")){pathError*=0 ;}
        if (pathSequence.contains("RDL")){pathError*=0 ;}
        if (pathSequence.contains("LDR")){pathError*=0 ;}
        if (pathSequence.contains("RUL")){pathError*=0 ;}
        if (pathSequence.contains("LUR")){pathError*=0 ;}
        if (pathSequence.contains("ULD")){pathError*=0 ;}
        if (pathSequence.contains("DRU")){pathError*=0 ;}
        if (pathSequence.contains("URD")){pathError*=0 ;}
        if (pathSequence.contains("DLU")){pathError*=0 ;}

        char[] alphabet = "azertyuiopqsdfghjklmwxcvbn".toUpperCase().toCharArray();
        for(char c : alphabet){
            if(StringMeth.containsChar(pathSequence.toUpperCase(), c)){
                if( c != 'U' && c != 'D' && c != 'L' && c != 'R'){charError*=0 ;}}}

        return pathError == 1 && mapError == 1 && finalPointError == 1 && charError == 1 ;
    }

    // méthode statique pour tester à l'aide d'un test unitaire
    public static boolean testPath(String pathSequence, int mapBounds) throws Exception {
        int pathError = 1 ;
        int mapError = 1;
        int finalPointError = 1 ;
        int charError = 1 ;
        int numR = StringMeth.count(pathSequence.toUpperCase(), 'R') ;
        int numU = StringMeth.count(pathSequence.toUpperCase(), 'U') ;
        int numL = StringMeth.count(pathSequence.toUpperCase(), 'L') ;
        int numD = StringMeth.count(pathSequence.toUpperCase(), 'D') ;

        if(numR-numL != mapBounds-1){
            finalPointError*=0 ;
        }

        if(Math.abs(numU - numD) > mapBounds){mapError*=0 ;}
        if(Math.abs(numR - numL) > mapBounds){mapError*=0 ;}

        if (pathSequence.contains("RL")){pathError*=0 ;}
        if (pathSequence.contains("LR")){pathError*=0 ;}
        if (pathSequence.contains("UD")){pathError*=0 ;}
        if (pathSequence.contains("DU")){pathError*=0 ;}
        if (pathSequence.contains("RDL")){pathError*=0 ;}
        if (pathSequence.contains("LDR")){pathError*=0 ;}
        if (pathSequence.contains("RUL")){pathError*=0 ;}
        if (pathSequence.contains("LUR")){pathError*=0 ;}
        if (pathSequence.contains("ULD")){pathError*=0 ;}
        if (pathSequence.contains("DRU")){pathError*=0 ;}
        if (pathSequence.contains("URD")){pathError*=0 ;}
        if (pathSequence.contains("DLU")){pathError*=0 ;}

        char[] alphabet = "azertyuiopqsdfghjklmwxcvbn".toUpperCase().toCharArray();
        for(char c : alphabet){
            if(StringMeth.containsChar(pathSequence.toUpperCase(), c)){
                if( c != 'U' && c != 'D' && c != 'L' && c != 'R'){charError*=0 ;}}}
        
        return pathError == 1 && mapError == 1 && finalPointError == 1 && charError == 1 ;
    }
}
