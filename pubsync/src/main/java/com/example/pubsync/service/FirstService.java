package com.example.pubsync.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class FirstService {

    public String getTestData(String param){
        Gson gson = new Gson();
        HttpClient httpClient = HttpClient.newBuilder()
                .build();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://dblp.org/search/publ/api?q=test&format="+param))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var testObject = gson.fromJson(response.body(), FirstService.class);
            return response.body();

        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return  "";
    }
    public String getAuthor(String param){
        HttpClient httpClient = HttpClient.newBuilder()
                .build();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://dblp.org/search/publ/api?q="+param))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return  "";
    }
}
