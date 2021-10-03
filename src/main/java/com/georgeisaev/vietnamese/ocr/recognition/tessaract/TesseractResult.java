package com.georgeisaev.vietnamese.ocr.recognition.tessaract;

import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionResult;

public record TesseractResult(String text, String fileName, String errorMessage) implements RecognitionResult {

}
