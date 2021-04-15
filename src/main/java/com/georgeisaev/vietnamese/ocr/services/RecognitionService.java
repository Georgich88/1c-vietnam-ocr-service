package com.georgeisaev.vietnamese.ocr.services;

import com.georgeisaev.vietnamese.ocr.recognition.api.Recognition;
import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionResult;
import com.georgeisaev.vietnamese.ocr.recognition.exceptions.RecognitionException;
import com.georgeisaev.vietnamese.ocr.recognition.tessaract.TesseractRecognition;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@Service
public class RecognitionService {

	private final Recognition tessaractRecognition;

	public RecognitionService(TesseractRecognition tessaractRecognition) {
		this.tessaractRecognition = tessaractRecognition;
	}

	public RecognitionResult recognise(Path path, Map<String, String> settings) throws RecognitionException, IOException {
		return tessaractRecognition.recognize(path.toFile(), settings);
	}

}
