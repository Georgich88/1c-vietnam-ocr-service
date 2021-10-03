package com.georgeisaev.vietnamese.ocr.controller.v1;

import com.georgeisaev.vietnamese.ocr.recognition.api.RecognitionResult;
import com.georgeisaev.vietnamese.ocr.services.RecognitionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static com.georgeisaev.vietnamese.ocr.configuration.ApiConstants.BASE_ENDPOINT;
import static com.georgeisaev.vietnamese.ocr.configuration.ApiConstants.RECOGNITION_DOCUMENTS_ENDPOINT;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

/**
 * Handles http request for document recognition.
 *
 * @author Georgy Isaev
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(BASE_ENDPOINT + RECOGNITION_DOCUMENTS_ENDPOINT)
public class DocumentsRecognitionController {

    RecognitionService recognitionService;


    /**
     * Handles a POST-request with a file and json parameters and returns recognition result.
     *
     * @param file              a file to be recognized
     * @param recognitionParams additional settings for recognition
     * @return response containing the result of recognition
     */
    @ApiOperation(
            nickname = "Documents Recognition",
            value = "Recognize with immediate result")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Recognition is completed"),
            @ApiResponse(code = 400, message = "Invalid data"),
            @ApiResponse(code = 406, message = "Cannot recognize"),
            @ApiResponse(code = 500, message = "Internal error")})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    @ResponseBody
    public ResponseEntity<RecognitionResult> recognize(
            @Parameter(
                    description = "Files to be uploaded",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)
            )
            @RequestPart MultipartFile file,
            @Parameter(
                    description = "Additional recognition parameters",
                    example = "{\"language\":\"vie\"}"
            )
            @RequestParam(value = "recognitionParams", required = false) Map<String, String> recognitionParams) {
        try {
            Path imageFileTempPath = Files.createTempFile(UUID.randomUUID().toString(), file.getOriginalFilename());
            imageFileTempPath.toFile().deleteOnExit();
            file.transferTo(imageFileTempPath);
            RecognitionResult result = recognitionService.recognise(imageFileTempPath,
                    nonNull(recognitionParams) ? recognitionParams : Collections.emptyMap());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Cannot return recognition result", e);
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }

    }

}
