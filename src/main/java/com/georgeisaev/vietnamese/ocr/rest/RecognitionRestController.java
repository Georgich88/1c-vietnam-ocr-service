package com.georgeisaev.vietnamese.ocr.rest;

import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionResult;
import com.georgeisaev.vietnamese.ocr.services.RecognitionService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

/**
 * Handles http request for document recognition.
 *
 * @author Georgy Isaev
 */
@Slf4j
@RestController
@RequestMapping("/documents")
public class RecognitionRestController {

	private final ServletContext context;
	private final RecognitionService recognitionService;

	@Autowired
	public RecognitionRestController(ServletContext context, RecognitionService recognitionService) {
		this.context = context;
		this.recognitionService = recognitionService;
	}

	/**
	 * Handles a POST-request with a file and json parameters and returns recognition result.
	 *
	 * @param document a document to be recognized
	 * @param settings additional settings for recognition
	 * @return response containing the result of recognition
	 */
	@PostMapping(consumes = "multipart/form-data", produces = "application/json")
	@ResponseBody
	public ResponseEntity<RecognitionResult> recognize(@RequestParam MultipartFile document,
	                                                   @RequestParam(required = false) String settings) {
		try {
			Map<String, String> params = retrieveRecognitionParams(settings);
			final Path imageFileTempPath =
					Path.of(context.getRealPath(generateFilename(document)));
			document.transferTo(imageFileTempPath);
			RecognitionResult result = recognitionService.recognise(imageFileTempPath, params);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Cannot return recognition result", e);
			return new ResponseEntity<>(NOT_ACCEPTABLE);
		}

	}

	/**
	 * Returns a temp unique file name.
	 *
	 * @param document document to be processed
	 * @return a temp file name
	 */
	@NonNull
	private String generateFilename(MultipartFile document) {
		return UUID.randomUUID() + document.getOriginalFilename();
	}

	/**
	 * Parses a Json string into parameters.
	 *
	 * @param settings Json object in {@code String} representation
	 * @return recognition parameters
	 */
	@NonNull
	private Map<String, String> retrieveRecognitionParams(String settings) {
		return settings == null ? Collections.emptyMap() :
				new JSONObject(settings).toMap()
						.entrySet()
						.stream()
						.collect(Collectors.toMap(Map.Entry::getKey, e -> (String) e.getValue()));
	}

}
