package com.receptro.mediapipeline.controller;

import com.receptro.mediapipeline.model.Intent;
import com.receptro.mediapipeline.service.extract.OcrExtractionService;
import com.receptro.mediapipeline.service.interpret.IntentInterpreterService;
import com.receptro.mediapipeline.service.synthesize.TextToSpeechService;
import com.receptro.mediapipeline.service.transcribe.WhisperTranscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileProcessorController {

    @Autowired
    private WhisperTranscriptionService whisperService;

    @Autowired
    private OcrExtractionService ocrService;

    @Autowired
    private IntentInterpreterService intentService;

    @Autowired
    private TextToSpeechService textToSpeechService;

    @GetMapping("/")
    public String showUploadPage() {
        return "index";
    }

    @PostMapping("/process-audio")
    public String processAudio(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String transcription = whisperService.transcribe(file);
            model.addAttribute("transcription", transcription);

            Intent intent = intentService.interpret(transcription);
            model.addAttribute("intent", intent.getIntent());
            model.addAttribute("parameters", intent.getParameters());

            String filename = "response_" + System.currentTimeMillis();
            String audioPath = textToSpeechService.synthesize(transcription, filename);
            model.addAttribute("audioPath", audioPath);

        } catch (Exception e) {
            model.addAttribute("error", "Failed to process audio: " + e.getMessage());
        }

        return "index";
    }

    @PostMapping("/process-image")
    public String processImage(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String extractedText = ocrService.extractText(file);
            model.addAttribute("transcription", extractedText);

            Intent intent = intentService.interpret(extractedText);
            model.addAttribute("intent", intent.getIntent());
            model.addAttribute("parameters", intent.getParameters());

            String filename = "response_" + System.currentTimeMillis();
            String audioPath = textToSpeechService.synthesize(extractedText, filename);
            model.addAttribute("audioPath", audioPath);

        } catch (Exception e) {
            model.addAttribute("error", "Failed to process image: " + e.getMessage());
        }

        return "index";
    }
}
