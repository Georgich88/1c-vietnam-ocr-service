package com.georgeisaev.vietnamese.ocr.recognition.tessaract;

import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionParams;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static net.sourceforge.tess4j.ITessAPI.TessOcrEngineMode.OEM_LSTM_ONLY;

@Slf4j
@Data
@RequiredArgsConstructor
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
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

    String language;
    int pageSegMode;
    int ocrEngineMode;
    Map<String, String> tessVariables;

    // endregion

    // region Constructors

    public TesseractParams(@NonNull Map<String, String> settings) {
        language = settings.getOrDefault(LANGUAGE_KEY, LANGUAGE_DEFAULT_VALUE);
        pageSegMode = retrieveIntValue(settings, PAGE_SEG_MODE_KEY, PAGE_SEG_MODE_DEFAULT_VALUE);
        ocrEngineMode = retrieveIntValue(settings, OCR_ENGINE_MODE_KEY, OCR_ENGINE_MODE_DEFAULT_VALUE);
        tessVariables = settings.entrySet().stream()
                .filter(entry -> !KEY_PROPERTIES.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    // endregion

    /**
     * Retrieves integer value from the settings.
     *
     * @param settings     recognition settings
     * @param key          key with int value
     * @param defaultValue returns if settings does not contain a {@code key}
     * @return parsed int value from the {@code settings}
     */
    private int retrieveIntValue(@NonNull Map<String, String> settings, @NonNull String key, int defaultValue) {
        String stringValue = settings.getOrDefault(key, String.valueOf(defaultValue));
        try {
            return parseInt(stringValue);
        } catch (NumberFormatException e) {
            log.error("Cannot parse int value from {}", stringValue, e);
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
