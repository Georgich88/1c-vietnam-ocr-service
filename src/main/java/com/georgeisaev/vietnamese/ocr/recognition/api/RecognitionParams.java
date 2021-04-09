package com.georgeisaev.vietnamese.ocr.recognition.api;

import net.sourceforge.tess4j.Tesseract;

/**
 * Define API for params.
 *
 * @author Georgy Isaev
 */
public interface RecognitionParams<T> {

	T toRecognitionProvider();

}
