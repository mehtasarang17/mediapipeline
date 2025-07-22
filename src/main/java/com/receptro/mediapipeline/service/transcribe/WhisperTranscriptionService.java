package com.receptro.mediapipeline.service.transcribe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.net.http.*;
import java.net.URI;
import java.nio.file.Files;
import java.io.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;

@Service
public class WhisperTranscriptionService {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api.openai.com/v1/audio/transcriptions";

    public String transcribe(MultipartFile audioFile) throws Exception {
        File tempFile = File.createTempFile("upload", ".wav,.mp3");
        audioFile.transferTo(tempFile);

        String boundary = "----boundary";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(ofMimeMultipartData(tempFile, boundary))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("WHISPER RESPONSE: " + response.body()); // 🔍 Debug

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        JsonNode textNode = root.get("text");
        if (textNode == null) {
            throw new RuntimeException("Whisper API did not return 'text'. Full response: " + response.body());
        }

        return textNode.asText();
    }


    private static HttpRequest.BodyPublisher ofMimeMultipartData(File file, String boundary) throws IOException {
        var byteArrays = new ArrayList<byte[]>();

        // Add file
        byteArrays.add(("--" + boundary + "\r\n").getBytes());
        byteArrays.add(("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n").getBytes());
        byteArrays.add(("Content-Type: audio/wav\r\n\r\n").getBytes());
        byteArrays.add(Files.readAllBytes(file.toPath()));
        byteArrays.add("\r\n".getBytes());

        // Add model
        byteArrays.add(("--" + boundary + "\r\n").getBytes());
        byteArrays.add("Content-Disposition: form-data; name=\"model\"\r\n\r\n".getBytes());
        byteArrays.add("whisper-1\r\n".getBytes());

        // End
        byteArrays.add(("--" + boundary + "--\r\n").getBytes());

        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }
}
