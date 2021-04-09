package com.georgeisaev.vietnamese.ocr.recognition.api;

import com.georgeisaev.vietnamese.ocr.recognition.exceptions.RecognitionException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Provides API for abstract recognition processing of image files.
 *
 * @author Georgy Isaev
 */
public interface Recognition {

	RecognitionResult recognize(File imageFile, Map<String, String> params) throws RecognitionException, IOException;

}
