package com.georgeisaev.vietnamese.ocr.recognition.tessaract;

import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionParams;
import lombok.Data;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static net.sourceforge.tess4j.ITessAPI.TessOcrEngineMode.OEM_LSTM_ONLY;

@Data
public class TesseractParams implements RecognitionParams<Tesseract> {

	// region Recognition options

	// Language
	public static final String LANGUAGE_KEY = "language";
	public static final String LANGUAGE_DEFAULT_VALUE = "vie";
	// Page segment mode
	public static final String PAGE_SEG_MODE_KEY = "pageSegMode";
	public static final int PAGE_SEG_MODE_DEFAULT_VALUE = 1;
	// Ocr engine mode
	public static final String OCR_ENGINE_MODE_KEY = "ocrEngineMode";
	public static final int OCR_ENGINE_MODE_DEFAULT_VALUE = OEM_LSTM_ONLY;

	public static final Set<String> KEY_PROPERTIES = Set.of(LANGUAGE_KEY, PAGE_SEG_MODE_KEY);

	// endregion

	// region Fields

	private final String language;
	private final int pageSegMode;
	private final int ocrEngineMode;
	private final Map<String, String> tessVariables;

	// endregion

	// region Constructors

	public TesseractParams(@NonNull Map<String, String> settings) {
		this.language = settings.getOrDefault(LANGUAGE_KEY, LANGUAGE_DEFAULT_VALUE);
		this.pageSegMode = retrieveIntValue(settings, PAGE_SEG_MODE_KEY, PAGE_SEG_MODE_DEFAULT_VALUE);
		this.ocrEngineMode = retrieveIntValue(settings, OCR_ENGINE_MODE_KEY, OCR_ENGINE_MODE_DEFAULT_VALUE);
		this.tessVariables = settings.entrySet().stream()
				.filter(entry -> !KEY_PROPERTIES.contains(entry.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	}

	// endregion

	private int retrieveIntValue(@NonNull Map<String, String> settings, @NonNull final String key, final int defaultValue) {
		final String mode = settings.getOrDefault(key, String.valueOf(defaultValue));
		try {
			return Integer.parseInt(mode);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	@Override
	public Tesseract toRecognitionProvider() {
		Tesseract tesseract = new Tesseract();
		tesseract.setLanguage(language);
		tesseract.setPageSegMode(pageSegMode);
		tesseract.setOcrEngineMode(ocrEngineMode);
		tessVariables.forEach(tesseract::setTessVariable);
		return tesseract;
	}

}
