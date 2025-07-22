# Modular Media & Data Processing Pipeline

A modular Java Spring Boot application that enables users to upload either **audio** or **image** files and receive:
- Transcribed text (via Whisper or OCR)
- Extracted intent and parameters
- Synthesized speech output (text-to-speech)

## Features

- **Speech-to-Text** using OpenAI Whisper API
- **OCR (Image-to-Text)** using Tess4J (Tesseract)
- **Intent Detection** from extracted text
- **Text-to-Speech** synthesis and playback
- Clean, dual-mode UI for both audio and image uploads
- Modular service-based architecture

## Technologies Used

- Java 17
- Spring Boot 3.x
- Thymeleaf
- Tess4J (Tesseract OCR)
- Whisper API (via HTTP client)
- FreeTTS (Text-to-Speech)
- Gradle

## Demo

### Upload Image  
![Image Upload](screenshots/image-upload.png)

### Upload Audio  
![Audio Upload](screenshots/audio-upload.wav)

### Output Section  
- Transcription  
- Intent  
- Parameters  
- Synthesized Audio Playback  

![Output](screenshots/output.png)

## How It Works

1. Upload an audio (`.wav`,`.mp3`) or image (`.png`) file
2. The backend:
   - Uses Whisper for audio transcription
   - Uses Tess4J for OCR on images
3. Extracts intent and parameters from the transcribed text
4. Generates a `.wav` audio file from the interpreted text
5. Displays all results in a responsive web page

## Future Enhancements

**Functional Enhancements**

1. Multi-language Support - Enable OCR, transcription, and synthesis in multiple languages (e.g., Spanish, French, Hindi).
2. Entity Recognition (NER) - Integrate NLP libraries (like spaCy or Stanford NLP) to extract more structured entities from text (names, locations, etc.).
3. Contextual Intent Detection - Improve intent extraction using pretrained transformer models (e.g., BERT via ONNX or remote APIs).
4. Drag and Drop Interface - Improve the UI to allow drag & drop file upload with live preview before processing.
5. Voice Customization - Let users select from different synthesized voice types and speeds.

**Architectural Improvements**

1. Asynchronous Processing - Use async queues (e.g., RabbitMQ) to offload long-running tasks like audio transcription.
2. Dockerization - Package the entire app in Docker containers for easier deployment.
3. Cloud Deployment - Deploy on Heroku, AWS Elastic Beanstalk, or GCP App Engine with persistent storage for uploaded/generated files.
4. Database Integration - Store user-uploaded files, transcription history, and metadata using a relational DB (e.g., PostgreSQL).

**Reliability & Monitoring**

1. File Type Validation - Add server-side validation for supported file types and maximum sizes.
2. Audit Logging - Log all user interactions and service outputs for debugging and analytics.
3. Unit and Integration Testing - Write test cases using JUnit, Mockito, and SpringBootTest to ensure system robustness.



