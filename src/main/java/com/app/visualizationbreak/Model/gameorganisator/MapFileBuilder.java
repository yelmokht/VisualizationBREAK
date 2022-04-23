package com.app.visualizationbreak.Model.gameorganisator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MapFileBuilder {
    private final int mapSize ;
    private final ArrayList<char[]> charList ;
    private int lineNum ;
    private final String fileName;
    private String pathSequence;
    private String fileContent ;

    public MapFileBuilder(int mapSize) throws IOException {
        fileName = "src\\main\\java\\com\\app\\visualizationbreak\\Model\\Maps\\FreeModeMap.txt";
        fileContent = "" ;
        this.mapSize = mapSize ;
        charList = new ArrayList<>() ;
        // construction d'une liste de 32 sur 32 de 0
        for(int i = 0; i<mapSize; i++){
            char[] line = new char[mapSize] ;
            for(int j = 0 ; j < mapSize ; j ++){
                line[j] = '0' ;
            }
            charList.add(line);
        }

    }

    public String getFileContent(){return fileContent ;}


    public void drawPath(String sequence, int initRowNum) {
        int rowNum = initRowNum ;
        int colNum = 0 ;
        charList.get(initRowNum)[0] = '1' ;
        for(int i = 0 ; i < sequence.length() ; i++){
            char c = sequence.charAt(i) ;
            if(c == 'R'){
                colNum += 1;
            }
            else if(c == 'L'){
                colNum -=1 ;
            }
            else if(c == 'U'){
                rowNum -=1 ;
            }
            else if(c == 'D'){
                rowNum +=1 ;
            }
            charList.get(rowNum)[colNum] = '1';
        }
    }

    public void writeMapInFile() throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(fileName)) ;
        for(int i = 0; i<mapSize; i++){
            String lineContent = "" ;
            for(int j = 0 ; j < mapSize ; j ++){
                lineContent+=charList.get(i)[j] ;
            }
            fileContent+= lineContent + "\n"  ;
        }
        br.write(fileContent);
        br.close();
    }
}
