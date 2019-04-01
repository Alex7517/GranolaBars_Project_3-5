package com.granolaBars;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Objects made from this load, save, and manage the active file meta and index data
 *
 */
public class ActiveDataManager {
    /**
     * This field holds the file index data used by the object
     */
    final public String PD_FILE_NAME;
    final private static String DEFAULT_PD_FILE_NAME = "DATA";

    /**
     * This is a list of updatableGUIs that will be updated when the data is changed
     */
    private List<updatableGUI> displayGUIList;

    /**
     * These are simple Strings used for debug output
     */
    final public static boolean DEBUG_MODE = true;
    final private static String MSG_DATALOADED = "DATA loaded";
    final private static String MSG_MISSING_DATA_FILE = "Could not find data file: ";
    final private static String MSG_CREATING_DATA_FILE = "Creating a empty data file named: ";
    final private static String MSG_CORRUPT_DATA = "Corrupt Data ";
    final private static String MSG_CANT_READ_FILE = "Could not read the file: ";
    final private static String MSG_PATH_NOT_IN_IDDATA = " was not in index";
    final private static String MSG_ERROR_CURRUPT_DATA_STRUCTURE = "Corruption found in the data files";
    final private static String MSG_DATA_ADDED = " was added";
    final private static String MSG_DATA_REMOVED = " was removed";
    final private static String MSG_DATA_ALREADY_EXISTS = " already exists in index";
    final private static String MSG_DATA_DOES_NOT_EXIST = " file does not exist in windows";
    final private static String MSG_FILE_NOT_UTD = "File is not UTD: ";

    /**
     * These are used to make the code easier to read
     */
    final private static int NO_ID = -1;
    final private static int ID_DATA_PATH = 0;
    final private static int ID_DATA_TIMESTAMP = 1;
    final private static int INDEX_DATA_FILE_ID = 0;
    final private static int INDEX_DATA_POS = 1;
    final private static int STARTING_ID = 0;
    final private static String TIMESTAMP_Format ="EEE, dd MMM yyyy HH:mm:ss z";

    /**
     * This field holds the file meta data used by the object
     */
    private Map<Integer, String[]> idDATA;

    /**
     * This field holds the file index data used by the object
     */
    private Map<String, List<Integer[]>> indexDATA;


    /**
     * This constructor will create a object that will load, save, and manage the active file meta and index data
     * This will use the name DATA as the file name
     *
     */
    ActiveDataManager(){
        this(DEFAULT_PD_FILE_NAME);
    }


    /**
     * This constructor will create a object that will load, save, and manage the active file meta and index data
     *
     * @param PD_FILE_NAME A String that indicates the file name used for by PersistentDataManager
     */
    ActiveDataManager(String PD_FILE_NAME){
        this.PD_FILE_NAME = PD_FILE_NAME;
        this.displayGUIList = new ArrayList<>();
        loadData();
        if (verifyDataIntegrity()) {
            updateAllData();
        }
        else{
            throw new RuntimeException(MSG_ERROR_CURRUPT_DATA_STRUCTURE);
        }
    }

    /**
     * A method that will load the persistent data using the PersistentDataManager class,
     * then holds the data to the objects active data fields
     */
    public void loadData(){
        try{
            Map[] dataReturn = PersistentDataManager.loadData(PD_FILE_NAME);

            //ID
            idDATA = (Map<Integer, String[]>) dataReturn[0];
            //Index
            indexDATA = (Map<String, List<Integer[]>>) dataReturn[1];

            if (DEBUG_MODE){System.out.println(MSG_DATALOADED);}
        }
        catch (FileNotFoundException e){
            //This is if the file could not be found, such as the first time it was ran
            if (DEBUG_MODE){System.out.println(MSG_MISSING_DATA_FILE + PD_FILE_NAME);}

            //Creating a new empty file
            if (DEBUG_MODE){System.out.println(MSG_CREATING_DATA_FILE + PD_FILE_NAME);}
            saveDATA();

            //POTENTIAL ERROR HERE, We will need to make sure there is no chance of infinite loop.
            loadData();
        }
        catch (IOException e){
            //ERROR This may be possible if some once changes the PD_FILE when the program was off.
            //Regardless this means that the Data is corrupt, we will have to decide how to handle this
            if (DEBUG_MODE){System.out.println(MSG_CANT_READ_FILE+PD_FILE_NAME);}
            throw new RuntimeException(MSG_CORRUPT_DATA+e);
        }
        catch (ClassCastException e){
            //This should never happen, as the data was already casted to this within persistentDataManager.
            if (DEBUG_MODE){System.out.println("***This indicates the data could not be casted properly\n***Let me know how!");}
            throw new RuntimeException(MSG_CORRUPT_DATA+e);
        }
    }

    /**
     * A method that will save the objects active data fields,
     * to the persistent data using the PersistentDataManager class
     */
    private void saveDATA(){
        try{
            PersistentDataManager.saveData(idDATA,indexDATA,PD_FILE_NAME);
        }
        catch (FileNotFoundException e){
            //This should never happen, as the file should be made if its not found.
            if (DEBUG_MODE){System.out.println("***IDK how, but you found it!\n***Let me know how!");}
            throw new RuntimeException(e);
        }
    }

    /**
     * A method that will go through all the active meta data and verify that they are UTD and exist
     * if they do not exist, then the files data is removed
     * if they are not UTD, then they are updated
     */
    public void updateAllData(){
        //This creates a copy of the maps keys, allowing up to add and remove keys from the map in this loop
        List<Integer> idDataKeys = new ArrayList<>(idDATA.keySet());
        for(int fileId: idDataKeys){
            updateDataPrivate(fileId);
        }
        saveDATA();
        updateGUI();
    }

    /**
     * A method that will verify that a given file ID is UTD and exist
     * if it does not exist, then the files data is removed
     * if it is not UTD, then it is updated
     *
     * @param fileId An int that indicates the file ID that needs to be checked from the active data
     */
    public void updateData(int fileId){
        updateDataPrivate(fileId);
        saveDATA();
        updateGUI();
    }

    /**
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
        String filePath = idDATA.get(fileId)[ID_DATA_PATH];
        if(!checkFileExists(filePath)){
            if (DEBUG_MODE){System.out.println(MSG_MISSING_DATA_FILE+filePath);}
            removeDataPrivate(fileId);
        }
        else if(!checkFileisUTD(fileId)){
            if (DEBUG_MODE){System.out.println(MSG_FILE_NOT_UTD+filePath);}
            removeDataPrivate(fileId);
            addDataPrivate(filePath);
        }
    }

    /**
     * A method that will verify that a given file exists in the systems directory
     *
     * @param filePath A String that indicates what file path is checked
     * @return A boolean that indicates if the file exists
     */
    public boolean checkFileExists(String filePath){
        File f = new File(filePath);
        if(f.exists() && !f.isDirectory())
            return true;
        else
            return false;
    }

    /**
     * A method that will verify that a given file is UTD(up to date) using its last modification time stamp
     *
     * @param fileId An int that indicates what file id is checked
     * @return A boolean that indicates if the file is UTD
     */
    public boolean checkFileisUTD(int fileId) {
        String savedTimeStamp = idDATA.get(fileId)[ID_DATA_TIMESTAMP];
        String fileTimeStamp = getFileCurrentTimestamp(idDATA.get(fileId)[ID_DATA_PATH]);
        if(savedTimeStamp.equals(fileTimeStamp)){
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * A method that will verify that a given file is already within the meta data
     *
     * @param filePath A String that indicates what file path is checked
     * @return A boolean that indicates if the file is within the meta data
     */
    public boolean checkFileMetaExists(String filePath){
        if (getFileId(filePath)==NO_ID)
            return false;
        else
            return true;
    }

    /**
     * A method that will return the id within the meta data for the given path
     *
     * @param filePath A String that indicates what file path is checked
     * @return A int that is the file id associated to the specific path, returns NO_ID(-1) if it does not exist
     */
    public int getFileId(String filePath) {

        for (int fileId: idDATA.keySet())
        {
            if(idDATA.get(fileId)[ID_DATA_PATH].equals(filePath)){
                return fileId;
            }
        }
        return NO_ID;
    }

    /**
     * A method that will add a file to the active data
     *
     * @param filePath A String that indicates the path of the file added
     */
    public void addData(String filePath){
        addDataPrivate(filePath);
        saveDATA();
        updateGUI();
    }

    /**
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
                if (DEBUG_MODE){System.out.println(filePath+MSG_DATA_ADDED);}
            }
            else{
                if (DEBUG_MODE){System.out.println(filePath+MSG_DATA_ALREADY_EXISTS);}
                //If the file already exists then what do we do
                //Do we ignore it or update it
            }
        }
        else{
            if (DEBUG_MODE){System.out.println(filePath+MSG_DATA_DOES_NOT_EXIST);}
            //If the file does not exist then what do we do
            //Do we ignore it or throw an error
        }
    }

    /**
     * A method that will remove a file to the active data
     *
     * @param filePath A String that indicates the path of the file removed
     */
    public void removeData(String filePath){
        int fileId = getFileId(filePath);
        if(fileId==-1) {
            if (DEBUG_MODE){System.out.println(filePath+MSG_PATH_NOT_IN_IDDATA);}
        }
        else{
            removeDataPrivate(fileId);
            saveDATA();
            updateGUI();
        }
    }

    /**
     * This is hidden to protect data, notice the lack of saveDATA();
     * This does what removeData(int fileId) says it does
     *
     * A method that will remove a file to the active data
     *
     * @param fileId An int that indicates the id of the file removed
     */
    private void removeDataPrivate(int fileId){
        //I dont think we need to check if data actually exists, but we may
        if (DEBUG_MODE){System.out.println(idDATA.get(fileId)[ID_DATA_PATH]+MSG_DATA_REMOVED);}
        removeWords(fileId);
        removeMeta(fileId);
    }

    /**
     * A method that will add a files meta data to the active idDATA
     *
     * @param filePath A String that indicates the path of the file to be added
     * @return A int that is the new file id for the added meta data
     */
    private Integer addMeta(String filePath){
        //Find first unused ID
        int newID = NO_ID;
        for(int i = STARTING_ID; newID==NO_ID; i++){
            if(!idDATA.containsKey(i)){
                newID = i;
            }
        }

        // Load file properties into array
        String fileProps[] = new String[2];
        fileProps[ID_DATA_PATH] = filePath;
        fileProps[ID_DATA_TIMESTAMP] = getFileCurrentTimestamp(filePath);

        // Save file properties in map
        idDATA.put(newID, fileProps);

        return newID;
    }

    /**
     * A method that will remove a files meta data from the active idDATA
     *
     * @param fileId An int that indicates the id of the file to be removed
     */
    private void removeMeta(Integer fileId)
    {
        idDATA.remove(fileId);
    }

    /**
     * A method that will add a files words data to the active indexData
     *
     * @param fileId An int is the id for the file meta data of the new words
     * @param filePath A String that indicates the path to the file to be added
     */
    private void addWords(int fileId, String filePath)
       {
        String lineOfText;
        String[] linesOfWords;
        File infile;
        Scanner scn = null;
        int wordPos = 0;

        try
        {
            //Open the file
            infile = new File(filePath);
            //Place file into a inputSteam
            scn = new Scanner(infile);

            //While file has a line to read
            while (scn.hasNextLine())
            {
                //Read the line
            	lineOfText = scn.nextLine();
            	//Convert line to all caps to remove cap sensitivity
            	lineOfText.toUpperCase();
            	//Split the line into a list of words
            	linesOfWords = lineOfText.split("\\s+");
                //For each word
            	for (String word : linesOfWords)
            	{
            	    //See if its a proper word
                	if (word.length() > 0){
                        //See if word already exists in indexData
                        if(!indexDATA.containsKey(word)){
                            //If not, create the key and the list value, and add it to the map
                            indexDATA.put(word, new LinkedList<Integer[]>());
                        }
                        //Create Integer[] of this words data, and it to indexData
                        indexDATA.get(word).add(new Integer[] {fileId, wordPos});
                    }
                	//Increase POS for next word
                    wordPos++;
                }
            }
            
         }
         catch (IOException e)
         {
        	System.out.println("Invalid file name");
        	System.out.println(e.getMessage());
         }
        finally
        {
	        scn.close();
        }
    }
 
    /*
     * A method that will remove a files words from the active indexData
     *
     * @param fileId An int that indicates the id of the file to have its words removed
     */
    //STUB
    private void removeWords(int fileId){
        //Check to see if file id exists in indexDATA
        //if it does, remove entry for that key
         if  ( indexDATA.containsKey(fileId) )
         {
            indexDATA.remove(fileId);
         }
    }

    /**
     * A method that will update the MaintenanceFrame and the temporally MainFrame tables when called
     *
     */
    public void updateGUI() {

        Object[][] data = new Object[idDATA.size()][2];
        int i = STARTING_ID;
        for (int fileId: idDATA.keySet()) {
            data[i][ID_DATA_PATH] = idDATA.get(fileId)[ID_DATA_PATH];
            data[i][ID_DATA_TIMESTAMP] = idDATA.get(fileId)[ID_DATA_TIMESTAMP];
            i++;
        }

        for(updatableGUI GUI: displayGUIList)
        {
            GUI.updateTable(data);
        }
    }

    /**
     * A method that verify the data loaded to see if there is any errors with the data structure
     * @return True is data structure has no errors or false otherwise
     */
    public boolean verifyDataIntegrity() {
        Set<String> words = indexDATA.keySet();
        // for each index
        for (String word: words) {
            List<Integer[]> index = indexDATA.get(word);
            // for each pair file_id - data_pos
            for (Integer[] pair: index){
                int fileId = pair[INDEX_DATA_FILE_ID];
                // check if this file_id contains in metadata map
                if (!idDATA.containsKey(fileId)) {
                    return false;
                }
            }
        }
        // integrity OK
        return true;
    }

    /**
     * A method that will get the current timestamp of a file
     *
     * @param filePath The path of the file to get the timestamp from
     * @return returns the current timestamp of a file
     */
    public String getFileCurrentTimestamp(String filePath){
        SimpleDateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_Format);
        return timestampFormat.format(new File(filePath).lastModified());
    }

    /**
     * This method will add a GUI to the list of updatableGUI that would receive a Object[][] to load into a Jtable
     * @param displayGUI the updatableGUI object that will be added to the list of GUIs
     */
    public void addDisplayGUI(updatableGUI displayGUI) {
        this.displayGUIList.add(displayGUI);
        updateGUI();
    }

    /**
     * This method will add a list of GUI to the list of updatableGUI that would receive a Object[][] to load into a Jtable
     * @param displayGUIList a list of updatableGUI objects that will be added to the list of GUIs
     */
    public void addDisplayGUI(List<updatableGUI> displayGUIList) {
        this.displayGUIList.addAll(displayGUIList);
        updateGUI();
    }

    /**
     * This method will remove a GUI to the list of updatableGUI so that it would not receive a Object[][]
     * @param displayGUI
     */
    public void removeDisplayGUI(updatableGUI displayGUI){
        this.displayGUIList.remove(displayGUI);
    }

    /**
     * This method will perform a OR search through the index data and return the data
     * @param searchedWords a set of words that contain every word to look for
     * @return a Object[][] that can be easily loaded into a Jtable
     */
    //STUB
    public Object[][] searchDataOr(Set<String> searchedWords){
        //for each searchedWords word
            //Find all instances in indexData of that word with for loop
                //Save the fileID of found word to a set
        //pass set of fileID to buildJtableData
        return buildJtableData(new HashSet<Integer>());
    }

    /**
     * This method will perform a AND search through the index data and return the data
     * @param searchedWords a set of words that contain every word to look for
     * @return a Object[][] that can be easily loaded into a Jtable
     */
    //STUB
    public Object[][] searchDataAnd(Set<String> searchedWords){
        //Create a list of sets
        //for each searchedWords word
            //Find all instances in indexData of that word with for loop
                //Save the fileID of found word to a set
                //Save the set to the next position on the list of sets
        //for each set in list of sets
            //Intersect sets together
                //stop once done with all set or (if 0 fileID left if set)exta good
        //pass set of fileID to buildJtableData
        return buildJtableData(new HashSet<Integer>());
    }

    /**
     * This method will perform a PHRASE search through the index data and return the data
     * @param searchedWords a list of words that contain every word to look for, the of the list if important
     * @return a Object[][] that can be easily loaded into a Jtable
     */
    //STUB
    public Object[][] searchDataPhrase(List<String> searchedWords){
        return new Object[1][1];
    }

    /**
     * This method will create a Object[][] from a Set of file IDs
     * @param foundFiles a set of file IDs that need to be added int the return array
     * @return a Object[][] that can be easily loaded into a Jtable
     */
    //STUB
    public Object[][] buildJtableData(Set<Integer> foundFiles){
        return buildJtableData(new HashSet<Integer>());
    }
}
