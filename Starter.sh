#!/bin/bash
if [ "$RUN_DEBUG" == "true" ];
then
  java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -cp vietnamese-ocr.jar
  -Dspring.config.location=file:/deployments/config/application.properties org.springframework.boot.loader.PropertiesLauncher
else
  java -cp vietnamese-ocr.jar -Dspring.config.location=file:/deployments/config/application.properties org.springframework.boot.loader.PropertiesLauncher
fi
