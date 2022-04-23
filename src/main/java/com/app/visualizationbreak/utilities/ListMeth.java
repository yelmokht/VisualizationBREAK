package com.app.visualizationbreak.utilities;

import java.util.List;

public class ListMeth {

    //methode de tri bulle, inspiré du code fourni à la section 8.2.3 du syllabus d'informatique INFO-H-100 version 3.5.1
    //, rédigé par Thierry MASSART.
    public static void triBulle(List<String> stringList){
        int n = stringList.size();
        for(int i = n; i > 1; i--){
            for (int k = 0; k < i - 1; k++){
                String[] values  = stringList.get(k).split(",");
                String[] values1 = stringList.get(k+1).split(",");
                if (Integer.valueOf(values[1]) < Integer.valueOf(values1[1])){
                    String str = stringList.get(k);
                    String str1 = stringList.get(k+1);
                    stringList.set(k, str1);
                    stringList.set(k + 1,str);
                }
            }
        }
    }
}
