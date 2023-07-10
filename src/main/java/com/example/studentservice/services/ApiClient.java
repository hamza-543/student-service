package com.example.studentservice.services;

import java.util.*;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.studentservice.dto.AddressDto;

@Service
public class ApiClient {
  private final WebClient.Builder webClientBuilder;

  public ApiClient(WebClient.Builder webClientBuilder) {
      this.webClientBuilder = webClientBuilder;
  }
    public <T> List<T> get(String url, Class<T[]> responseType) {
    Map<String, Object> options = new HashMap<>();
    options.put("queryParams", "");
    return this.get(url, responseType, options);
  }

  public <T> List<T> get(String url, Class<T[]> responseType, Map<String, Object> options) {
    String queryUrl = url + "?";
    // extract query params
    queryUrl += options.get("queryParams") != null ? options.get("queryParams").toString() : "";

    List<T> listData = List.of(webClientBuilder.build().get()
            .uri(queryUrl)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(responseType)
            .block());
    return listData;
  }
}
