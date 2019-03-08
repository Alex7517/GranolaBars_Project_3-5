package com.granolaBars;

//This is the methods that will "write" the string that has the DATA
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.json.*;

//This is only needed to actually save and load the string
import java.io.*;
import java.util.Map;

public class PersistentDataManager {
    final static private String FILENAME = "DATA";
    final static private String ID = "IDs";
    final static private String INDEX = "Index";

    static void saveData(Map idData,Map indexData){

        //This creates a object that contains the DATA and manages the conversion from real DATA to strings
        JSONObject dataObj = new JSONObject();

        //This is an example of the DATA to be saved, its like a map saving maps.
        //At the moment the map is a string but this is just for example, in the future it will be a map object.
        //The IDs key will point to the value containing our Map object that holds the META info, think of the DATA in the DB now.
        //The Index key will point to the value containing our Map object that holds the inverted index
        dataObj.put(ID,idData);
        dataObj.put(INDEX,indexData);

        //DIAG This shows that the JSONObject is just being converted to a string normally.
        System.out.println(dataObj);

        //This actually creates the file that saves the persistent DATA.
        //It creates the file if not found, and replaces it if it already exists.
        try{
            //This creates the text output steam
            PrintStream out = new PrintStream( new FileOutputStream( FILENAME ) );
            //This actually saves the file
            out.println( dataObj );
            //This closes the file to prevent errors
            out.close();
        }
        //FileNotFoundException is a checked error from the FileOutputStream method above.
        catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

    static Map[] loadData(){
        //This array will store the returned maps
        Map[] dataReturn = new Map[2];

        //Here is where the DATA is pull in from the file.
        try{
            //This creates the text input steam
            BufferedReader in = new BufferedReader( new FileReader( FILENAME ) );
            //This holds the string pulled from the file
            String dataString = in.readLine();
            //This closes the file to prevent errors
            in.close();

            //This converts the DATA back from a string into a JSON file that we can access
            JSONObject dataObj = new JSONObject( dataString );

            //DIAG This is just for example, later this would be something like: Map idMap = newObj.get("IDs");
            System.out.println(dataObj.get(ID));
            //System.out.println(dataObj.get(INDEX));

            //This converts the DATA back from a string into a GSON file, so that it can be converted into a Map
            //java.lang.reflect.Type mapType = new TypeToken<Map<String, Map<Integer, String>>>(){}.getType();
            //Map<String, Map> idData = new Gson().fromJson(dataObj.get(ID).toString(),mapType);

            //This adds the data to a returned array
            //dataReturn[0]= idData;
            //dataReturn[1]= dataObj.get(INDEX);
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
