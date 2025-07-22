package com.receptro.mediapipeline.service.interpret;

import com.receptro.mediapipeline.model.Intent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IntentInterpreterService {


    public Intent interpret(String text) {
        text = text.toLowerCase();

        Map<String, String> parameters = new HashMap<>();
        String intentType = "general"; // fallback

        if (text.contains("weather") || text.contains("temperature") || text.contains("forecast")) {
            intentType = "-----Weather-----";
            parameters.put("location", extractLocation(text));
        } else if (text.contains("music") || text.contains("song")) {
            intentType = "------Music------";
            parameters.put("genre", "default");
        } else if (text.contains("schedule") || text.contains("meeting") || text.contains("appointment") || text.contains("call")) {
            intentType = "------Event------";
            parameters.put("date", "today");
        } else if (text.contains("dog") || text.contains("mastiff") || text.contains("bulldog") || text.contains("doberman")) {
            intentType = "------Dog------";
            parameters.put("Species", "Dog");
        }

        return new Intent(intentType, parameters);
    }

    private String extractLocation(String text) {
        // Basic location extraction, just as placeholder logic
        if (text.contains("new york")) return "New York";
        if (text.contains("dallas")) return "Dallas";
        return "unknown";
    }
}
