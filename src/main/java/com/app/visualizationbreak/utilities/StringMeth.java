package com.app.visualizationbreak.utilities;

public class StringMeth {
    public static int count(String string, char elem){
        int i = 0 ;
        int occurrence = 0;
        while(i<string.length()){
            if (string.charAt(i) == elem){occurrence++;}
            i++ ;
        }
        return occurrence;
    }


    public static boolean containsChar(String string, char character){
        int i = 1 ;
        char[] stringCharArray = string.toCharArray() ;
        for(char c : stringCharArray){
            if(c == character){
                i*=0 ;
            }
        }
        return i == 0;
    }
}
