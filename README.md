# Rest for OCR service with Vietnamese and Russian language support

[Latest docker image](https://hub.docker.com/layers/170166608/one1gog/1c-vietnam-ocr/latest/images/sha256-8f482f35f79c04e57371dd7039b4391a2836aaa5742de62bad275241e15b4161?context=repo)

## 1. About
Version: 0.1.0-1   
Service provides REST API to recognize documents using OCR library Tessaract

## 2. Tessaract

**Tesseract** is an open-source OCR engine developed by **HP** that recognizes more than 100 languages, along with the
support of ideographic and right-to-left languages. Also, we can train Tesseract to recognize other languages. It
contains two OCR engines for image processing – a LSTM (Long Short Term Memory) OCR engine, and a legacy OCR engine that
works by recognizing character patterns.

## 3. Spring Boot

**Spring Boot** makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".
We take an opinionated view of the Spring platform and third-party libraries, so you can get started with minimum fuss.
Most Spring Boot applications need minimal Spring configuration.

## 4. Build
Build a docker image:
```shell
docker build -t one1gog/1c-vietnam-ocr .
```

## 5. Running
1. Download docker image `docker pull one1gog/1c-vietnam-ocr:latest`
2. Run docker with 8080 port binding: `docker run -p 8080:8080/tcp one1gog/1c-vietnam-ocr:latest`
3. Swagger will be available on a bound port: http://localhost:8080/swagger-ui/index.html
4. Try to recognize document
```shell
curl -X POST "http://localhost:8080/api/v1/documents" -H "accept: application/json" \ 
-H "Content-Type: multipart/form-data" -F "recognitionParams={ "language": "vie" }" \ 
-F "file=@example.jpg;type=image/jpeg" 
```
5. To pass specific Tessaract parameters try `recognitionParams` header param




