package com.kousenit;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kousenit.after.TTSRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TextToSpeechService {
    private static final String TTS_URL = "https://api.openai.com/v1/audio/speech";
    private static final String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");

    public static final String TTS = "tts-1";
    public static final String TTS_HD = "tts-1-hd";

    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public Path generateMp3_v2(TTSRequest ttsRequest) {
        String postBody = gson.toJson(ttsRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TTS_URL))
                .header("Authorization", "Bearer %s".formatted(OPENAI_API_KEY))
                .header("Content-Type", "application/json")
                .header("Accept", "audio/mpeg")
                .POST(HttpRequest.BodyPublishers.ofString(postBody))
                .build();

        try (var client = HttpClient.newHttpClient()) {
            HttpResponse<Path> response =
                    client.send(request, HttpResponse.BodyHandlers.ofFile(getFilePath()));
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

        public Path generateMp3_v1(String model, String input, String voice) {
        String postBody = """
                {
                    "model": "%s",
                    "input": "%s",
                    "voice": "%s"
                }
                """.formatted(model,
                input.replaceAll("\\s+", " ").trim(),
                voice);
        System.out.println(postBody);

        var request = HttpRequest.newBuilder()
                .uri(URI.create(TTS_URL))
                .header("Authorization", "Bearer %s".formatted(OPENAI_API_KEY))
                .header("Content-Type", "application/json")
                .header("Accept", "audio/mpeg")
                .POST(HttpRequest.BodyPublishers.ofString(postBody))
                .build();

        try (var client = HttpClient.newHttpClient()) {
            var response = client.send(request, HttpResponse.BodyHandlers.ofFile(getFilePath()));
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error generating mp3", e);
        }
    }

    private Path getFilePath() {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = String.format("audio_%s.png", timestamp);
        Path dir = Paths.get("src/main/resources");
        return dir.resolve(fileName);
    }

}
