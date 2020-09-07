package br.com.java.json.java_object;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Mapper {
    
    private static final Gson gson;
    
    static {
        gson = new GsonBuilder().create();
    }
    
    public static <T> T fromJson(String json, Class<T> classOfT) {
       return gson.fromJson(json, classOfT);
    }
    
    public static <T> String toJson(T object) {
        return gson.toJson(object);
    }

}
