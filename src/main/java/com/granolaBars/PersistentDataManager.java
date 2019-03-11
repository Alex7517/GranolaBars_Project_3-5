package com.granolaBars;

//This is the methods that will "write" the string that has the DATA
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.json.*;

//This is only needed to actually save and load the string
import java.io.*;
import java.util.List;
import java.util.Map;

public class PersistentDataManager {
    final static private String ID = "IDs";
    final static private String INDEX = "Index";

    static void saveData(Map<Integer,String[]> idData,Map<String, List<Integer[]>> indexData, String FILE_NAME){

        //This creates a object that contains the DATA and manages the conversion from real DATA to strings
        JSONObject idDataObj = new JSONObject();
        JSONObject indexDataObj = new JSONObject();

        //This saves the DATA to the objects
        idDataObj.put(ID,idData);
        indexDataObj.put(INDEX,indexData);

        //DEBUG This shows that the JSONObject is just being converted to a string normally.
        System.out.println(idDataObj);
        System.out.println(indexDataObj);

        //This creates the file that saves the persistent DATA.
        //It creates the file if not found, and replaces it if it already exists.
        try{
            //This creates the text output steam
            PrintStream out = new PrintStream( new FileOutputStream( FILE_NAME ) );
            //This actually saves the DATA to the file
            out.println( idDataObj );
            out.println( indexDataObj );
            //This closes the file to prevent errors
            out.close();
        }
        //FileNotFoundException is a checked error from the FileOutputStream method above.
        catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

    static Map[] loadData(String FILE_NAME){
        //This array will store the returned maps
        Map[] dataReturn = new Map[2];

        //Here is where the DATA is pull in from the file.
        try{
            //This creates the text input steam
            BufferedReader in = new BufferedReader( new FileReader( FILE_NAME ) );
            //This holds the string pulled from the file
            String idDataString = in.readLine();
            String indexDataString = in.readLine();
            //This closes the file to prevent errors
            in.close();

            //This converts the DATA back from a string into a GSON file, so that it can be converted into a Map
            //IDS
            java.lang.reflect.Type idDataObjType = new TypeToken<Map<String, Map<Integer,String[]>>>(){}.getType();
            Map<String, Map> idDataObj = new Gson().fromJson(idDataString,idDataObjType);
            //INDEX
            java.lang.reflect.Type indexDataObjType = new TypeToken<Map<String, Map<String, List<Integer[]>>>>(){}.getType();
            Map<String, Map> indexDataObj = new Gson().fromJson(indexDataString,indexDataObjType);

            //This adds the data to a returned array
            dataReturn[0] = (Map<Integer, String[]>)idDataObj.get(ID);
            dataReturn[1] = (Map<String, List<Integer[]>>)indexDataObj.get(INDEX);
        }
        //FileNotFoundException is a checked error from the FileOutputStream method above.
        catch (FileNotFoundException e){
            System.out.println(e);
        }
        //IOException is a checked error from the FileOutputStream method above.
        catch (IOException e){
            System.out.println(e);
        }
        return dataReturn;
    }

}
