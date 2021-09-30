package com.georgeisaev.vietnamese.ocr.services;

import com.georgeisaev.vietnamese.ocr.recognition.api.Recognition;
import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionResult;
import com.georgeisaev.vietnamese.ocr.recognition.exceptions.RecognitionException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class RecognitionService {

    Recognition tessaractRecognition;

    public RecognitionResult recognise(Path path, Map<String, String> settings) throws RecognitionException, IOException {
        return tessaractRecognition.recognize(path.toFile(), settings);
    }

}
