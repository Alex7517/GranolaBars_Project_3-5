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
    final private static String MSG_FILE_EMPTY = "File is empty or can not be read: ";

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
    final private static String WORD_CLEANUP_REG = "[^a-zA-Z0-9 ]";
    final private static String WORD_SPLIT_REG = "\\W+";
    final private static String MSG_NO_SEARCH_RESULTS = "No results";

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
        if (verifyDataIntegrity()) {
            updateAllData();
        }
        else{
            throw new RuntimeException(MSG_ERROR_CURRUPT_DATA_STRUCTURE);
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
        List<String> linesOfWords;
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
            	//Clean up the input
            	linesOfWords = prepTextInput(lineOfText);
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
            if(wordPos == 0){
                if (DEBUG_MODE){System.out.println(MSG_FILE_EMPTY+filePath);}
                //We may want to MSG the user
            }

         }
         catch (IOException e)
         {
             if (DEBUG_MODE){System.out.println(MSG_CANT_READ_FILE+filePath);}
             //We may want to MSG the user
         }
        finally
        {
	        scn.close();
        }
    }

    /**
     * A method that will remove a files words from the active indexData
     *
     * @param fileId An int that indicates the id of the file to have its words removed
     */
    private void removeWords(int fileId){
        //Create a temp copy of keys to use in forloop, to prevent errors
        List<String> keyCopies = new ArrayList<>(indexDATA.keySet());

        //for each word in indexData
        for (String word: keyCopies) {
            //Create a iterator for the words list to allow for removing while iterating
            Iterator<Integer[]> wordListIterator = indexDATA.get(word).iterator();

            //For each in word instance
            while(wordListIterator.hasNext()){
                Integer[] nextWordInstence = wordListIterator.next();
                //See if the next word instance has the fileId to be removed
                if(nextWordInstence[INDEX_DATA_FILE_ID] == fileId) {
                    //Remove that word instance from list
                    wordListIterator.remove();
                }
            }
            //if no more word instances in list
            //remove the word from indexData
            if(indexDATA.get(word).size()==0)
                indexDATA.remove(word);
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
     * @param searchedWordsString a single string that will be parsed and searched
     * @return a Object[][] that can be easily loaded into a Jtable
     */
    public Object[][] searchDataOr(String searchedWordsString){
        //Parse the string
        Set<String> searchedWords = new HashSet<>(prepTextInput(searchedWordsString));

        Set<Integer> filesFound = new HashSet<>();
        //for each searchedWords word
        for(String searchedWord: searchedWords) {
            //See if word exists in indexData
            if(indexDATA.containsKey(searchedWord)){
                //Find all instances in indexData of that word with for loop
                for (Integer[] wordList : indexDATA.get(searchedWord)) {
                    //Save the fileID of found word to a set
                    filesFound.add(wordList[INDEX_DATA_FILE_ID]);
                }
            }
        }
        //pass set of fileID to buildJtableData
        return buildJtableData((filesFound));
    }

    /**
     * This method will perform an AND search through the index data and return the data
     * @param searchedWordsString a single string that will be parsed and searched
     * @return a Object[][] that can be easily loaded into a Jtable
     */
    public Object[][] searchDataAnd(String searchedWordsString){
        //Parse the string
        Set<String> searchedWords = new HashSet<>(prepTextInput(searchedWordsString));

        //Create a list of sets
        List<Set> wordSets = new LinkedList<>();

        //for each searchedWords word
        for(String searchedWord: searchedWords) {
            //See if that word exists in indexDATA
            if(indexDATA.containsKey(searchedWord)) {
                //Create a NEW set of ints to hold the found fileIDs
                Set<Integer>wordsFound = new HashSet<>();
                //Find all instances in indexData of that word with for loop
                for(Integer[] word: indexDATA.get(searchedWord)) {
                    //Add fileIDs for the words
                    wordsFound.add(word[INDEX_DATA_FILE_ID]);
                }
                //Add set to list of sets
                wordSets.add(wordsFound);
                }
            else{
                //If Word was not found, give up
                return buildJtableData(new HashSet<Integer>());
            }
        }

        //for each set in list of sets
        for(int i=1; i < wordSets.size(); i++) {
            //Intersect sets together
            wordSets.get(0).retainAll(wordSets.get(i));

        }

        //pass set of fileID to buildJtableData
        if(wordSets.size()>0){
            return buildJtableData(wordSets.get(0));
        }
        else{
            return buildJtableData(new HashSet<Integer>());
        }
    }

    /**
     * This method will perform a PHRASE search through the index data and return the data
     * @param searchedWordsString a single string that will be parsed and searched
     * @return a Object[][] that can be easily loaded into a Jtable
     */
    public Object[][] searchDataPhrase(String searchedWordsString){
        //Parse the string
        List<String> searchedWords = prepTextInput(searchedWordsString);

        Set<Integer> filesFound = new HashSet<>();
        //Check that searchedWords is not empty
        if(searchedWords.size()>0){
            //if word exists
            if(indexDATA.containsKey(searchedWords.get(0))) {
                //Find all instances in indexData of the first word
                for(Integer[] word: indexDATA.get(searchedWords.get(0))) {
                    //if only one word
                    if(searchedWords.size()==1){
                        filesFound.add(word[INDEX_DATA_FILE_ID]);
                    }
                    else{
                        filesFound.addAll(searchDataPhraseRec(searchedWords.subList(1,searchedWords.size()), word[INDEX_DATA_POS]+1, word[INDEX_DATA_FILE_ID]));
                    }
                }
            }
        }
        return buildJtableData(filesFound);
    }

    /**
     * This method is the recursive counterpart for the searchDataPhrase method
     * This will recursively search up the "tree" of words
     * ending when it finds a dead end (no valid word)
     * or a valid word, ending the chain with a valid file ID
     *
     * @param searchedWords a list of words that contain every word to look for, the order of the list is important
     * @param pos the pos(position) of the potential word to look for
     * @param fileId the fileId of the potential word to look for
     * @return a Set<Integer> that holds the list of files that qualify
     */
    private Set<Integer> searchDataPhraseRec(List<String> searchedWords, int pos, int fileId){
        Set<Integer> filesFound = new HashSet<>();
        //if word exists
        if(indexDATA.containsKey(searchedWords.get(0))) {
            //Find all instances in indexData of the first word
            for(Integer[] word: indexDATA.get(searchedWords.get(0))) {
                //If word pos and fileId matches
                if(word[INDEX_DATA_FILE_ID] == fileId && word[INDEX_DATA_POS] == pos){
                    //if only one word
                    if(searchedWords.size()==1){
                        //Add this word instance to the set
                        filesFound.add(word[INDEX_DATA_FILE_ID]);
                    }
                    else{
                        //Else check if next word exists
                        //If anything is return it will be also returned
                        filesFound.addAll(searchDataPhraseRec(searchedWords.subList(1,searchedWords.size()), pos+1, fileId));
                    }
                }
            }
        }
        return filesFound;
    }

    /**
     * This method will create a Object[][] from a Set of file IDs
     * @param foundFiles a set of file IDs that need to be added int the return array
     * @return a Object[][] that can be easily loaded into a Jtable
     */
    private Object[][] buildJtableData(Set<Integer> foundFiles){


        Object[][] data = new Object[foundFiles.size()][2];

        int i = STARTING_ID;

        for (Integer fileId : foundFiles){
            data[i][ID_DATA_PATH] = idDATA.get(fileId)[ID_DATA_PATH];
            data[i][ID_DATA_TIMESTAMP] = idDATA.get(fileId)[ID_DATA_TIMESTAMP];
            i++;
        }

        if (data.length>0)
            return data;
        else{
            data = new Object[1][1];
            data[0][0] = MSG_NO_SEARCH_RESULTS;
            return data;
        }
    }

    /**
     * This method will cleanup the text input to insure that all words are properly indexed and searched
     *
     * @param inputString the string to be clean and converted into a list of words
     * @return a list of strings, each element is a single word
     */
    private static List<String> prepTextInput(String inputString){

        /* This is what its doing
        inputString = inputString.toUpperCase();
        inputString = inputString.replaceAll(WORD_CLEANUP_REG,"");
        List<String> inputList = Arrays.asList(inputString.split(WORD_SPLIT_REG));
        return inputList;
        */

        if(DEBUG_MODE)
        System.out.println(Arrays.asList(inputString.toUpperCase().replaceAll(WORD_CLEANUP_REG,"").split(WORD_SPLIT_REG)));

        return Arrays.asList(inputString.toUpperCase().replaceAll(WORD_CLEANUP_REG,"").split(WORD_SPLIT_REG));

    }
}
