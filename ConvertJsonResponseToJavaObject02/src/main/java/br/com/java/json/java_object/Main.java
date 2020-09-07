package br.com.java.json.java_object;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String URL = "https://jsonplaceholder.typicode.com/todos";
    private static final HttpClient client = HttpClient.newHttpClient();
    
    public static void main(String[] args) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).GET().build();
        
        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        
        List<Todo> todos = Arrays.asList(Mapper.fromJson(response, Todo[].class));
        
        showTodos(todos);
        
        createTodo();
    }
    
    private static void showTodos(List<Todo> todos) {
        System.out.printf("%-10s %-10s %-80s %-5s\n", "UserId", "Id", "Title", "Completed");
        todos.forEach(Main::showTodo);
    }
    
    private static void showTodo(Todo todo) {
        System.out.printf("%-10d %-10d %-80s %-5b\n", todo.getUserId(), todo.getId(), todo.getTitle(), todo.getCompleted());
    }
    
    private static void createTodo() {
        Todo todo = new Todo(2L, null, "New todo", false);
        
        String json = Mapper.toJson(todo);
        
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        
        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        
        System.out.println("\nRegistered Todo: \n" + response);
    }
    
}
