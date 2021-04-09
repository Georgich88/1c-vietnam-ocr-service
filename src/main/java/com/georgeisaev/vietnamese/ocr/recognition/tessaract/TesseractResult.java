package com.georgeisaev.vietnamese.ocr.recognition.tessaract;

import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionResult;

public class TesseractResult implements RecognitionResult {

	private final String text;
	private final String fileName;
	private final String errorMessage;

	public TesseractResult(String text, String fileName, String errorMessage) {
		this.text = text;
		this.fileName = fileName;
		this.errorMessage = errorMessage;
	}

	// region Getters

	@Override
	public String getText() {
		return text;
	}

	public String getFileName() {
		return fileName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	// endregion

}
