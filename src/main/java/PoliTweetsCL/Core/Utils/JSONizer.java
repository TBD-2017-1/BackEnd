package PoliTweetsCL.Core.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONizer {
    static public String toJSON(Object obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    static public String toPrettyJSON(Object obj){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(obj);
    }
}
