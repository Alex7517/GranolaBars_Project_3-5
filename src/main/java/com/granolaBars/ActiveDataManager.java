package com.granolaBars;

import java.io.IOException;
import java.util.*;

public class ActiveDataManager {
    static void test(){
        //This creates a map of example IDs
        Map<Integer, String[]> i = new Hashtable<>();

        //This finds a path
        String path;
        try{
            path = new java.io.File( "." ).getCanonicalPath();

        }
        catch (IOException e){
            path = "Path not found";
        }

        //This finds the Date --We will likely want to switch to a calender object
        //Then it saves the path and the date to the String array
        String[] tempS= {path,new Date().toString()};

        //This adds the String array to the map --Imagine each being a new file and the int to be the ID for each
        i.put(1,tempS);
        i.put(2,tempS);
        i.put(3,tempS);

        //This creates a map of example index
        Map<String, List<Integer[]>> j = new Hashtable<>();

        //This creates an list that will hold small arrays, each list item will be associated to a specific word,
                                                            // and each array will hold the words file ID and POS
        List<Integer[]> temp = new ArrayList<>();
        Integer[] array1 = {1,22};
        Integer[] array2 = {3,52};

        temp.add(array1);
        temp.add(array2);

        //This adds the list to the specific word
        j.put("BLUE",temp);

        //This calls the method that saves the DATA
        PersistentDataManager.saveData(i,j);


        //This calls for the DATA and separates the DATA
        Map[] dataReturn = PersistentDataManager.loadData();
        //ID
        Map<Integer, String[]> idDATA = dataReturn[0];
        //Index
        Map<String, List<Integer[]>> indexDATA = dataReturn[1];

        //This is the path
        System.out.println(idDATA.get(1)[0]);
        //This is the date
        System.out.println(new Date(idDATA.get(1)[1]));

        //This returns the list for a specific word
        System.out.println(indexDATA.get("BLUE"));
        //This returns the file Id for the first Blue word
        System.out.println(indexDATA.get("BLUE").get(0)[0]);
        //This returns the POS for the first Blue word
        System.out.println(indexDATA.get("BLUE").get(0)[1]);
    }
}
