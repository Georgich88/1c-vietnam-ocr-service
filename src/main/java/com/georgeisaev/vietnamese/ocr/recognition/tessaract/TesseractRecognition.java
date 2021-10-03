package com.georgeisaev.vietnamese.ocr.recognition.tessaract;

import com.georgeisaev.vietnamese.ocr.recognition.api.Recognition;
import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionParams;
import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionResult;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class TesseractRecognition implements Recognition {

    private static final String CLASSPATH_TESSERACT_MODELS = "./tesseract/models";

    @Override
    public RecognitionResult recognize(File file, Map<String, String> settings) {
        RecognitionParams<Tesseract> params = new TesseractParams(settings);
        Tesseract tesseract = params.toRecognitionProvider();
        try {
            tesseract.setDatapath(CLASSPATH_TESSERACT_MODELS);
            String text = tesseract.doOCR(file);
            return new TesseractResult(text, file.getName(), "");
        } catch (TesseractException e) {
            log.error("Cannot proceed recognition", e);
            return new TesseractResult("", file.getName(), e.getMessage());
        }
    }

}
