package com.receptro.mediapipeline.service.extract;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.File;

@Service
public class OcrExtractionService {

    @Value("${ocr.tessdata.path}")
    private String tessdataPath;

    public String extractText(MultipartFile imageFile) throws Exception {
        File file = File.createTempFile("upload", "*.png");
        imageFile.transferTo(file);

        Tesseract tesseract = new Tesseract();
        System.setProperty("TESSDATA_PREFIX", tessdataPath);
        tesseract.setDatapath(tessdataPath); // still required for safety
        tesseract.setLanguage("eng");// Update path if needed

        try {
            System.out.println("OCR File Path: " + file.getAbsolutePath());
            return tesseract.doOCR(file);
        } catch (TesseractException e) {
            System.err.println("OCR Failure: " + e.getMessage());
            throw new RuntimeException("Error during OCR", e);
        }

    }
}
