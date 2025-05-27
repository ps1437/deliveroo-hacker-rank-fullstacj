package com.syscho.security;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class WeatherPaginationClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        int page = 1;
        boolean hasMore = true;

        while (hasMore) {
            String url = "https://jsonmock.hackerrank.com/api/weather/search?page=" + page;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode root = mapper.readTree(response.body());
            JsonNode dataNode = root.get("data");

            if (dataNode == null || !dataNode.isArray() || dataNode.size() == 0) {
                hasMore = false;
                break;
            }

            List<WeatherData> weatherList = mapper.readValue(
                    dataNode.toString(),
                    new TypeReference<List<WeatherData>>() {}
            );

            for (WeatherData weather : weatherList) {
                if (weather.status != null && weather.status.size() == 2) {
                    String name = weather.name;
                    String temp = weather.weather.replace(" degree", "").trim();

                    String wind = "";
                    String humidity = "";

                    for (String s : weather.status) {
                        if (s.startsWith("Wind:")) {
                            wind = s.replace("Wind:", "").replace("Kmph", "").trim();
                        } else if (s.startsWith("Humidity:")) {
                            humidity = s.replace("Humidity:", "").replace("%", "").trim();
                        }
                    }

                    System.out.println(name + "," + temp + "," + wind + "," + humidity);
                }
            }

            page++;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class WeatherData {
        public String name;
        public String weather;
        public List<String> status;
    }
}
