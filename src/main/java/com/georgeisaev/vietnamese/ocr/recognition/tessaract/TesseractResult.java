package com.georgeisaev.vietnamese.ocr.recognition.tessaract;

import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionResult;
import lombok.Data;

@Data
public class TesseractResult implements RecognitionResult {

	private final String text;
	private final String fileName;
	private final String errorMessage;

}
