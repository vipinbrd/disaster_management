package com.disaster.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LocationExtractorService {

    @Value("${GEMINI_API_KEY}")
    private String apiKey;
    @Value("${opencage.api.key}")
    private String openapiKey;

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";

    public String extractLocationFromText(String userInput) throws Exception {
        try {
            String prompt = """
                Extract only the most relevant location mentioned in the following text. 
                Respond with just the full address or place name, nothing else. 
                Do not include any extra explanation or words.
                Text: """ + userInput;

            String body = """
            {
              "contents": [
                {
                  "parts": [
                    {
                      "text": "%s"
                    }
                  ]
                }
              ]
            }
            """.formatted(prompt);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API_URL + "?key=" + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return extractTextFromGeminiResponse(response.body());

        } catch (Exception e) {
            e.printStackTrace();
           throw new Exception("ai not Respoding");
        }
    }
    private String extractTextFromGeminiResponse(String jsonResponse) {
        try {
            int index = jsonResponse.indexOf("\"text\":");
            if (index != -1) {
                int start = jsonResponse.indexOf("\"", index + 7) + 1;
                int end = jsonResponse.indexOf("\"", start);
                String result = jsonResponse.substring(start, end);
                return result.replaceAll("\\\\n", "").trim(); // ✅ remove \n and trim whitespace
            }
        } catch (Exception ignored) {}
        return "No location found.";
    }

    public double[] getCoordinatesFromDescription(String description) throws Exception {
    	String  address=extractLocationFromText(description);
        try {
            String url = UriComponentsBuilder.fromHttpUrl("https://api.opencagedata.com/geocode/v1/json")
                    .queryParam("q", address)
                    .queryParam("key", openapiKey)
                    .queryParam("limit", 1)
                    .build()
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            JsonNode geometry = root.path("results").get(0).path("geometry");
            double lat = geometry.path("lat").asDouble();
            double lng = geometry.path("lng").asDouble();

            System.out.println("Latitude: " + lat);
            System.out.println("Longitude: " + lng);

            return new double[] {lat,lng}; // or parse JSON to extract lat/lng
        } catch (Exception e) {
           throw new Exception("ai not responding");
           
        }
		
    }
}
