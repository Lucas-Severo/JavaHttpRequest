package br.com.java.json.java_object;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class Main {
    
    public static void main(String[] args) throws JsonProcessingException {
        String url = "https://jsonplaceholder.typicode.com/users";
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Content-Type", "application/json")
                .build();
        
        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = Arrays.asList(mapper.readValue(response, User[].class));
        
        showUsers(users);
    }
    
    public static void showUsers(List<User> users) {
        System.out.printf("%-5s %-28s %-20s %-15s %-15s %-20s\n", "Id", "Name", "City", "Longitude", "Latitude", "Company");
        users.forEach(Main::showUser);
    }
    
    public static void showUser(User user) {
        System.out.printf("%-5d %-28s %-20s %-15s %-15s %-20s\n", 
                user.getId(), 
                user.getName(), 
                user.getAddress().getCity(),
                user.getAddress().getGeo().getLng(),
                user.getAddress().getGeo().getLat(),
                user.getCompany().getName());
    }
}
