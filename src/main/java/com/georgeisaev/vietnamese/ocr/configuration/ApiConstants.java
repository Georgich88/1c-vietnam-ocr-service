package com.georgeisaev.vietnamese.ocr.configuration;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ApiConstants {

    public static final String VERSION = "v1";
    public static final String API = "api";

    // region Endpoints

    public static final String BASE_ENDPOINT = "/" + API + "/" + VERSION + "/";
    public static final String RECOGNITION_DOCUMENTS_ENDPOINT = "documents";

    // endregion

    public static final String APP_PATH = "1c-vietnam-ocr-service";

}
