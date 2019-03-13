package com.granolaBars;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Objects made from this load, save, and manage the active file meta and index data
 *
 */
public class ActiveDataManager {
    /**
     * This field holds the file index data used by the object
     */
    final String PD_FILE_NAME;

    /*
     * This field holds the file meta data used by the object
     */
    private Map<Integer, String[]> idDATA;

    /*
     * This field holds the file index data used by the object
     */
    private Map<String, List<Integer[]>> indexDATA;


    //This is example code and will not be used in anyway for the final project
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
        try{
            PersistentDataManager.saveData(i,j,"DATA");
        }
        catch (FileNotFoundException e){
            System.out.println("IDK how, but you found it!");
        }

        Map[] dataReturn = new Map[2];
        //This calls for the DATA and separates the DATA
        try{
            dataReturn = PersistentDataManager.loadData("DATA");
        }
        catch (FileNotFoundException e){
            System.out.println("Could not find the file named DATA");
        }
        catch (IOException e){
            System.out.println("Could not read the file named DATA");
        }
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


    /**
     * This constructor will create a object that will load, save, and manage the active file meta and index data
     * This will use the name DATA as the file name
     *
     */
    ActiveDataManager(){
        this("DATA");
    }


    /**
     * This constructor will create a object that will load, save, and manage the active file meta and index data
     *
     * @param PD_FILE_NAME A String that indicates the file name used for by PersistentDataManager
     */
    ActiveDataManager(String PD_FILE_NAME){
        this.PD_FILE_NAME = PD_FILE_NAME;
        loadData();
        //Possibly we will need a method to verify the data integrity here
        //verifyDataIntegrity()
        updateData();
    }

    /**
     * A method that will load the persistent data using the PersistentDataManager class,
     * then holds the data to the objects active data fields
     */
    void loadData(){
        Map[] dataReturn = new Map[2];
        try{
            dataReturn = PersistentDataManager.loadData(PD_FILE_NAME);
        }
        catch (FileNotFoundException e){
            //ERROR We will need to manage this error properly later
            System.out.println("Could not find the file named DATA");
        }
        catch (IOException e){
            //ERROR We will need to manage this error properly later
            System.out.println("Could not read the file named DATA");
        }
        //This will need a try block in case of errors with the returned DATA casting
        //ID
        idDATA = (Map<Integer, String[]>) dataReturn[0];
        //Index
        indexDATA = (Map<String, List<Integer[]>>) dataReturn[1];
    }

    /**
     * A method that will save the objects active data fields,
     * to the persistent data using the PersistentDataManager class
     */
    void saveDATA(){
        try{
            PersistentDataManager.saveData(idDATA,indexDATA,PD_FILE_NAME);
        }
        catch (FileNotFoundException e){
            //ERROR We will need to manage this error properly later
            System.out.println("IDK how, but you found it!");
        }
        updateGUI();
    }

    /**
     * A method that will go through all the active meta data and verify that they are UTD and exist
     * if they do not exist, then the files data is removed
     * if they are not UTD, then they are updated
     */
    void updateData(){
        for(int fileId: idDATA.keySet()){
            updateDataPrivate(fileId);
        }
        saveDATA();
    }

    /**
     * A method that will verify that a given file ID is UTD and exist
     * if it does not exist, then the files data is removed
     * if it is not UTD, then it is updated
     *
     * @param fileId An int that indicates the file ID that needs to be checked from the active data
     */
    void updateData(int fileId){
        updateDataPrivate(fileId);
        saveDATA();
    }

    /*
     * This is hidden to protect data, notice the lack of saveDATA();
     * This does what updateData(int fileId) says it does
     *
     * A method that will verify that a given file ID is UTD and exist
     * if it does not exist, then the files data is removed
     * if it is not UTD, then it is updated
     *
     * @param fileId An int that indicates the file ID that needs to be checked from the active data
     */
    private void updateDataPrivate(int fileId){
        String filePath = idDATA.get(fileId)[0];
        if(!checkFileExists(filePath)){
            //POTENTIAL ERROR HERE, if it removes the file it may cause errors with the foreach loop above
            //This will need to be tested for later
            removeDataPrivate(fileId);
        }
        else if(!checkFileisUTD(fileId)){
            //POTENTIAL ERROR HERE, if it removes the file it may cause errors with the foreach loop above
            //This will need to be tested for later
            removeDataPrivate(fileId);
            //POTENTIAL ERROR HERE, as with the other error, if a file is added to the Map,
            //it may cause errors with the foreach loop above
            //This will need to be tested for later
            addDataPrivate(filePath);
        }
    }

    /**
     * A method that will verify that a given file exists in the systems directory
     *
     * @param filePath A String that indicates what file path is checked
     * @return A boolean that indicates if the file exists
     */
    //STUB
    boolean checkFileExists(String filePath){
        return true;
    }

    /**
     * A method that will verify that a given file is UTD(up to date) using its last modification time stamp
     *
     * @param fileId An int that indicates what file id is checked
     * @return A boolean that indicates if the file is UTD
     */
    //STUB
    boolean checkFileisUTD(int fileId){
        return true;
    }

    /**
     * A method that will verify that a given file is already within the meta data
     *
     * @param filePath A String that indicates what file path is checked
     * @return A boolean that indicates if the file is within the meta data
     */
    boolean checkFileMetaExists(String filePath){
        if (getFileId(filePath)==-1)
            return false;
        else
            return true;
    }

    /**
     * A method that will return the id within the meta data for the given path
     *
     * @param filePath A String that indicates what file path is checked
     * @return A int that is the file id associated to the specific path, returns -1 if it does not exist
     */
    //STUB
    int getFileId(String filePath){
        return -1;
    }

    /**
     * A method that will add a file to the active data
     *
     * @param filePath A String that indicates the path of the file added
     */
    void addData(String filePath){
        addDataPrivate(filePath);
        saveDATA();
    }

    /*
     * This is hidden to protect data, notice the lack of saveDATA();
     * This does what addData(String filePath) says it does
     *
     * A method that will add a file to the active data
     *
     * @param filePath A String that indicates the path of the file added
     */
    private void addDataPrivate(String filePath){
        if(checkFileExists(filePath)){
            if(!checkFileMetaExists(filePath)){
                Integer fileId = addMeta(filePath);
                addWords(fileId, filePath);
            }
            else{
                //If the file already exists then what do we do
                //Do we ignore it or update it
            }
        }
        else{
            //If the file does not exist then what do we do
            //Do we ignore it or throw an error
        }
    }

    /**
     * A method that will remove a file to the active data
     *
     * @param fileId An int that indicates the id of the file removed
     */
    void removeData(int fileId){
        removeDataPrivate(fileId);
        saveDATA();
    }

    /*
     * This is hidden to protect data, notice the lack of saveDATA();
     * This does what removeData(int fileId) says it does
     *
     * A method that will remove a file to the active data
     *
     * @param fileId An int that indicates the id of the file removed
     */
    private void removeDataPrivate(int fileId){
        //I dont think we need to check if data actually exists, but we may
        removeWords(fileId);
        removeMeta(fileId);
    }

    /*
     * A method that will add a files meta data to the active idDATA
     *
     * @param filePath A String that indicates the path of the file to be added
     * @return A int that is the new file id for the added meta data
     */
    //STUB
    private Integer addMeta(String filePath){
        return 1;
    }

    /*
     * A method that will remove a files meta data from the active idDATA
     *
     * @param fileId An int that indicates the id of the file to be removed
     */
    //STUB
    private void removeMeta(Integer fileId){

    }

    /*
     * A method that will add a files words data to the active indexData
     *
     * @param fileId An int is the id for the file meta data of the new words
     * @param filePath A String that indicates the path to the file to be added
     */
    //STUB
    private void addWords(int fileId, String filePath){

    }

    /*
     * A method that will remove a files words from the active indexData
     *
     * @param fileId An int that indicates the id of the file to have its words removed
     */
    //STUB
    private void removeWords(int fileId){

    }

    /**
     * A method that will update the MaintenanceFrame and the temporally MainFrame tables when called
     *
     */
    //STUB
    void updateGUI(){

    }
}
