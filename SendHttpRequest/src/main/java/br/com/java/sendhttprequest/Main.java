package br.com.java.sendhttprequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author lucas
 */
public class Main {

    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/photos"))
                .setHeader("Content-Type", "application/json")
                .GET()
                .timeout(Duration.of(1, ChronoUnit.MINUTES))
                .build();
        
        List<String> result = client.sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(Main::extractInformation)
                .join();
        
        result.forEach(System.out::println);
    }
    
    public static List<String> extractInformation(String responseData) {
        JSONArray array = new JSONArray(responseData);
        List<String> information = new ArrayList<>();
        
        for(Object object: array) {
            JSONObject photo = (JSONObject) object;
            
            int albumId = photo.getInt("albumId");
            int id = photo.getInt("id");
            String title = photo.getString("title");
            String url = photo.getString("url");
            
            information.add(id + " " + albumId + " " + title + " " + url);
        }
        
        return information;
    }
    
}
