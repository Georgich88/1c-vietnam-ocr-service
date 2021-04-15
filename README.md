# Example of free OCR service

## 1. Introduction

**1C:Document Management** solution supports only Russian and English language recognition, which is quite enough for
most cases in Russia. However, when support for "exotic" languages is in demand then CuneiForm becomes insufficient,
whereas third-party solutions are either paid or proprietary, or depend on an external connection. In this repository, I
am showing how to build a completely free OCR server that supports high-quality document recognition  
in Vietnamese.

## 2. Tessaract

**Tesseract** is an open-source OCR engine developed by **HP** that recognizes more than 100 languages, along with the
support of ideographic and right-to-left languages. Also, we can train Tesseract to recognize other languages. It
contains two OCR engines for image processing – a LSTM (Long Short Term Memory) OCR engine, and a legacy OCR engine that
works by recognizing character patterns.

## 3. Spring Boot

**Spring Boot** makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".
We take an opinionated view of the Spring platform and third-party libraries, so you can get started with minimum fuss.
Most Spring Boot applications need minimal Spring configuration.

## 4. Setting up

Going to spring.io and create an initial image of the application. You can achieve the same result with a paid IntelliJ
IDEA and free STS based on Eclipse.

## 5. Project structure

Opening the downloaded project, for example, in IDEA.

We will create the following layers:

1. **The Controllers layer** to process REST requests; put it in the rest package
1. **The Service layer**, which will be responsible for preparing the data for recognition, and the generation of
   recognition results; place it in the services layer
1. **The Recognition Domain Layer**. For this, we will add the following interfaces `RecognitionParams` - for generating
   recognition parameters, `Recognition` - Recognition processor, `RecognitionResult` - for working with the results of
   recognition. For each interface, we will add an implementation for working with the Tess4J library.

Additionally, we will add to the project ready trained models for the Vietnamese and Russian languages, hence the native
**Tesseract** libraries.
