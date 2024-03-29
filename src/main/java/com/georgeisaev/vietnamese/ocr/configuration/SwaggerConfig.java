package com.georgeisaev.vietnamese.ocr.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import static java.util.Collections.emptyList;

@AllArgsConstructor
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    static final String API_TITLE = "1C:OCR (vi)";
    static final String API_DESCRIPTION = "OCR engine for Vietnamese market";
    static final String BASE_PACKAGE_APIS = "com.georgeisaev.vietnamese.ocr.controller.v1";
    static final String CONTACT_NAME = "George Isaev";
    static final String CONTACT_EMAIL = "georgeisaev@gmail.com";

    private final Environment env;

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.OAS_30)
                .ignoredParameterTypes(Principal.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE_APIS))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
        docket.pathMapping("/");
        return docket;
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(CONTACT_NAME, "", CONTACT_EMAIL);
        String appVersion = this.env.getProperty("app.version");
        return new ApiInfo(
                API_TITLE,
                API_DESCRIPTION,
                appVersion,
                "",
                contact,
                "",
                "",
                emptyList());
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .showCommonExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }

    @Component
    public class StringToMapConverter implements Converter<String, Map<String, String>> {

        @Override
        public Map<String, String> convert(String source) {
            try {
                return new ObjectMapper().readValue(source, new TypeReference<Map<String, String>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

    }

}
