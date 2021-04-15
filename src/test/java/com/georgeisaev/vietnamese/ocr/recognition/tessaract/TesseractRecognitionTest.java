package com.georgeisaev.vietnamese.ocr.recognition.tessaract;

import com.georgeisaev.vietnamese.ocr.recognition.api.Recognition;
import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TesseractTestConfig.class)
class TesseractRecognitionTest {

	public static final String TEST_DOCUMENT = "tesseract/testdata/test.png";
	public static final String EXPECTED_RESULT = "đi lại bằng tàu điện ngầm\nbằng tàu metro\n\n \n";

	private final Recognition tessaractRecognition;
	private final ClassPathResource testData;

	@Autowired
	public TesseractRecognitionTest(TesseractRecognition tessaractRecognition) {
		this.tessaractRecognition = tessaractRecognition;
		testData = new ClassPathResource(TEST_DOCUMENT);
	}

	@Test
	void shouldRecognize() {
		RecognitionResult result =
				assertDoesNotThrow(() -> tessaractRecognition.recognize(testData.getFile(), emptyMap()));
		assertEquals(EXPECTED_RESULT, result.getText());
	}

}
