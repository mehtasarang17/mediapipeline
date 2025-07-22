package com.receptro.mediapipeline.model;

import java.util.Map;

public class Intent {
    private String intent;
    private Map<String, String> parameters;

    public Intent(String intent, Map<String, String> parameters) {
        this.intent = intent;
        this.parameters = parameters;
    }

    public String getIntent() {
        return intent;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
