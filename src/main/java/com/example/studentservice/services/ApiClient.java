package com.example.studentservice.services;

import java.util.*;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApiClient {
  private final WebClient.Builder webClientBuilder;

  public ApiClient(WebClient.Builder webClientBuilder) {
      this.webClientBuilder = webClientBuilder;
  }

  // block getList start
  // use to get has many records

  public <T> List<T> getList(String url, Class<T[]> responseType) {
    Map<String, Object> options = new HashMap<>();
    options.put("queryParams", "");
    options.put("acceptFormat", MediaType.APPLICATION_JSON);
    return this.getList(url, responseType, options);
  }

  public <T> List<T> getList(String url, Class<T[]> responseType, Map<String, Object> options) {
    String queryUrl = url + "?";
    // extract query params
    queryUrl += options.get("queryParams") != null ? options.get("queryParams").toString() : "";
    // extract media type
    MediaType acceptFormat = options.get("acceptFormat") != null ? (MediaType) options.get("acceptFormat") : MediaType.APPLICATION_JSON;

    List<T> listData = List.of(webClientBuilder.build().get()
            .uri(queryUrl)
            .accept(acceptFormat)
            .retrieve()
            .bodyToMono(responseType)
            .block());
    return listData;
  }
  // block getList end

  // block get start
  // use to get has one record

  public <T> T get(String url, Class<T> responseType) {
    Map<String, Object> options = new HashMap<>();
    options.put("queryParams", "");
    options.put("acceptFormat", MediaType.APPLICATION_JSON);
    return this.get(url, responseType, options);
  }

  public <T> T get(String url, Class<T> responseType, Map<String, Object> options) {
    String queryUrl = url + "?";
    // extract query params
    queryUrl += options.get("queryParams") != null ? options.get("queryParams").toString() : "";
    // extract media type
    MediaType acceptFormat = options.get("acceptFormat") != null ? (MediaType) options.get("acceptFormat") : MediaType.APPLICATION_JSON;

    T data = webClientBuilder.build().get()
            .uri(queryUrl)
            .accept(acceptFormat)
            .retrieve()
            .bodyToMono(responseType)
            .block();
    return data;
  }

  // block get end

  // block post start
  // use to post has one record
  public <T, R> T post(String url, Class<T> responseType, R requestBody) {
    Map<String, Object> options = new HashMap<>();

    options.put("requestFormat", MediaType.APPLICATION_JSON);
    options.put("acceptFormat", MediaType.APPLICATION_JSON);

    return this.post(url, responseType, requestBody, options);
  }

  public <T, R> T post(String url, Class<T> responseType, R requestBody, Map<String, Object> options) {
    MediaType requestFormat = options.get("requestFormat") != null ? (MediaType) options.get("requestFormat") : MediaType.APPLICATION_JSON;
    MediaType acceptFormat = options.get("acceptFormat") != null ? (MediaType) options.get("acceptFormat") : MediaType.APPLICATION_JSON;

    T data = webClientBuilder.build().post()
            .uri(url)
            .accept(acceptFormat)
            .contentType(requestFormat)
            .body(BodyInserters.fromValue(requestBody))
            .retrieve()
            .bodyToMono(responseType)
            .block();

    return data;
  }
  // block post end

  // block postList start
  // use to post has many records
  public <T, R> List<T> postList(String url, Class<T[]> responseType, List<R> requestBody) {
    Map<String, Object> options = new HashMap<>();

    options.put("requestFormat", MediaType.APPLICATION_JSON);
    options.put("acceptFormat", MediaType.APPLICATION_JSON);

    return this.postList(url, responseType, requestBody, options);
  }

  public <T, R> List<T> postList(String url, Class<T[]> responseType, List<R> requestBody, Map<String, Object> options) {
    MediaType requestFormat = options.get("requestFormat") != null ? (MediaType) options.get("requestFormat") : MediaType.APPLICATION_JSON;
    MediaType acceptFormat = options.get("acceptFormat") != null ? (MediaType) options.get("acceptFormat") : MediaType.APPLICATION_JSON;

    List<T> listData = List.of(webClientBuilder.build().post()
            .uri(url)
            .accept(requestFormat)
            .contentType(acceptFormat)
            .body(BodyInserters.fromValue(requestBody))
            .retrieve()
            .bodyToMono(responseType)
            .block());

    return listData;
  }

  // block postList end
}
