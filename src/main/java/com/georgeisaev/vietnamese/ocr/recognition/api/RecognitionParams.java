package com.georgeisaev.vietnamese.ocr.recognition.api;

/**
 * Define API for params.
 *
 * @param <T> recognition provider type
 * @author Georgy Isaev
 */
public interface RecognitionParams<T> {

	T toRecognitionProvider();

}
