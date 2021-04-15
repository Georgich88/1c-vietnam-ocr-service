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

	/**
	 * Recognize text from a given file and returns the result of recognition.
	 *
	 * @param imageFile file contains images
	 * @param params    additional provider specific recognition parameters
	 * @return recognition result
	 * @throws RecognitionException when recognition processor cannot digest a file content
	 * @throws IOException          when an error occurs during file operation with {@code imageFile}
	 */
	RecognitionResult recognize(File imageFile, Map<String, String> params) throws RecognitionException, IOException;

}
