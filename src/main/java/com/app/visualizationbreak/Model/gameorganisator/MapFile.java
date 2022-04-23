package com.app.visualizationbreak.Model.gameorganisator;


import com.app.visualizationbreak.Model.figures.Point;
import com.app.visualizationbreak.Model.figures.Tile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapFile extends File {
    public String pathname ;
    public MapFile(String pathname) {
        super(pathname);
        this.pathname = pathname ;
    }

    // méthode qui initie une liste de Tiles à ajouter au Drawing à la lecture du fichier
    public ArrayList<Tile> makeMapTiles(int size) throws IOException {
        ArrayList<Tile> tilesList = new ArrayList<>() ;
        ArrayList<char[]> charList = makeCharList() ;
        int rowNum = 0 ;
        int colNum = 0 ;
        for(char[] line : charList){
            for(int i = 0; i < line.length; i++){
                double x = colNum*size ;
                double y = rowNum*size ;
                tilesList.add(new Tile(new Point(x, y), line[i], size)) ;
                colNum ++ ;
            }
            colNum = 0 ;
            rowNum ++ ;
        }
        return tilesList ;
    }

    // méthode qui traduit le fichier en listes de sous listes de charactères, utile pour localiser un chemin
    public ArrayList<char[]> makeCharList() throws IOException {
        ArrayList<char[]> charList = new ArrayList<>() ;
        FileReader fr = new FileReader(pathname) ;
        BufferedReader br = new BufferedReader(fr) ;
        String line ;
        while ((line = br.readLine()) != null) {
            charList.add(line.toCharArray()) ;
        }
        return charList ;
    }

    // compte le nombre de chemins sur la map
    public int countPaths() throws IOException {
        int num = 0 ;
        for(int i = 0; i< makeCharList().get(1).length; i++){
            if(makeCharList().get(i)[0] == '1'){
                num ++ ;
            }
        }
        return num ;
    }

    // indique le numéro des lignes du début des chemins
    public ArrayList<Integer> firstTileLocs() throws IOException {
        ArrayList<Integer> firstTiles = new ArrayList<>() ;
        for(int i = 0; i< makeCharList().get(1).length; i++){
            if(makeCharList().get(i)[0] == '1'){
                firstTiles.add(i) ;
            }
        }

        return firstTiles ;
    }

    // pour un début chemin, construit la séquence du chemin ("RRDDRRUUU" par exemple, où "R" = Right, etc.)
    public String makeOnePath(String startString, int firstTileLineNum) throws IOException {
        ArrayList<char[]> charList = makeCharList();
        int colIndex = 1 ;
        int linIndex = firstTileLineNum ;
        while(colIndex!= charList.get(1).length-1){
            int len = startString.length();
            if(charList.get(linIndex)[colIndex+1] == '1' & charList.get(linIndex+1)[colIndex] == '1'
                    &charList.get(linIndex-1)[colIndex] == '1' & charList.get(linIndex)[colIndex-1] == '1'){
                startString += startString.charAt(len-1);
                if(startString.charAt(len-1) == 'U'){
                    linIndex -= 1;
                }
                else if(startString.charAt(len-1) == 'D'){
                    linIndex += 1 ;
                }
                else if(startString.charAt(len-1) == 'R'){
                    colIndex += 1 ;
                }
                else if(startString.charAt(len-1) == 'L'){
                    colIndex -= 1 ;
                }
            }
            else{
                if (startString.charAt(len -1) != 'L'&& charList.get(linIndex)[colIndex+1] == '1'){
                    startString += 'R' ;
                    colIndex +=1 ;
                }
                else if (startString.charAt(len -1) != 'R'&& charList.get(linIndex)[colIndex-1] == '1'){
                    startString += 'L' ;
                    colIndex -= 1 ;
                }
                else if (startString.charAt(len -1) != 'U'&& charList.get(linIndex+1)[colIndex] == '1'){
                    startString += 'D' ;
                    linIndex += 1 ;
                }
                else if (startString.charAt(len -1) != 'D'&& charList.get(linIndex-1)[colIndex] == '1'){
                    startString += 'U' ;
                    linIndex -= 1 ;
                }
            }
        }
        return startString;
    }

    // construis la liste des séquences correspondant aux différents chemins
    public ArrayList<String> makePathSequences() throws IOException{
        int numberOfPaths = countPaths() ;
        ArrayList<String> sequences = new ArrayList<>() ;
        ArrayList<Integer> firstTilesLoc = firstTileLocs() ;

       for(int i = 0; i < numberOfPaths; i++){
           String str = "R" ;
           sequences.add(makeOnePath(str, firstTilesLoc.get(i))) ;
       }
        return  sequences ;
    }

    public String makePathSequence() throws IOException {
        ArrayList<char[]> charList = makeCharList();
        String sequence = "R" ;
        // D'abord : localisation de la premiere case chemin
        int firstTileLineNum = 0 ;
        while(charList.get(firstTileLineNum)[0] != '1'){
            firstTileLineNum ++;
        }
        int colIndex = 1 ;
        int linIndex = firstTileLineNum ;
        while(colIndex!= charList.get(1).length-1){
            int len = sequence.length();
            if (sequence.charAt(len -1) != 'L'&& charList.get(linIndex)[colIndex+1] == '1'){
                sequence += 'R' ;
                colIndex +=1 ;
            }
            else if (sequence.charAt(len -1) != 'R'&& charList.get(linIndex)[colIndex-1] == '1'){
                sequence += 'L' ;
                colIndex -= 1 ;
            }
            else if (sequence.charAt(len -1) != 'U'&& charList.get(linIndex+1)[colIndex] == '1'){
                sequence += 'D' ;
                linIndex += 1 ;
            }
            else if (sequence.charAt(len -1) != 'D'&& charList.get(linIndex-1)[colIndex] == '1'){
                sequence += 'U' ;
                linIndex -= 1 ;
            }


        }


        return sequence ;

    }
}
