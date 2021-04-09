package com.georgeisaev.vietnamese.ocr.recognition.tessaract;

import com.georgeisaev.vietnamese.ocr.recognition.api.Recognition;
import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionParams;
import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionResult;
import lombok.extern.java.Log;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import static net.sourceforge.tess4j.ITessAPI.TessOcrEngineMode.OEM_LSTM_ONLY;

@Log
@Component
public class TesseractRecognition implements Recognition {

	private final Resource models;

	public TesseractRecognition() {
		models = new ClassPathResource("tesseract/models");
	}

	@Override
	public RecognitionResult recognize(File file, Map<String, String> settings) throws IOException {
		RecognitionParams<Tesseract> params = new TesseractParams(settings);
		Tesseract tesseract = params.toRecognitionProvider();
		tesseract.setDatapath(models.getFile().getPath());
		try {
			String text = tesseract.doOCR(file);
			return new TesseractResult(text, file.getName(), "");
		} catch (TesseractException e) {
			log.log(Level.SEVERE, e, e::getMessage);
			return new TesseractResult("", file.getName(), e.getMessage());
		}
	}

}
