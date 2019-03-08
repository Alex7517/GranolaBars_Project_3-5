package com.granolaBars;

import java.util.*;

public class ActiveDataManager {
    static void test(){
        //This creates a map of example IDs
        Map<Integer, String> i = new Hashtable<>();
        i.put(1,"ID DATA 1");
        i.put(2,"ID DATA 2");
        i.put(3,"ID DATA 3");

        //This creates a map of example index
        Map<Integer, String> j = new Hashtable<>();
        j.put(1,"Index Data 1");
        j.put(2,"Index Data 2");
        j.put(3,"Index Data 3");
        j.put(4,"Index Data 4");

        //This calls the method that saves the DATA
        PersistentDataManager.saveData(i,j);


        //This calls for the DATA and separates the DATA
        Map[] dataReturn = PersistentDataManager.loadData();
        //Map idDATA = dataReturn[0];
        //Map indexDATA = dataReturn[1];

        //
        //System.out.println(idDATA.get(1));
        //System.out.println(indexDATA.get(1));
    }
}
