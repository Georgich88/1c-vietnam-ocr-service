FROM openjdk:17-alpine
ARG version=0.1.0-1
COPY ./vietnamese-ocr-$version.jar /usr/app/vietnamese-ocr.jar
COPY ./Starter.sh /usr/app/Starter.sh

WORKDIR /usr/app
USER root

RUN apk update && apk add \
    tesseract-ocr \
    ghostscript

RUN chgrp -R 0 /usr/app/ && chmod -R g=u /usr/app/; \
    chmod +x /usr/app/Starter.sh; \
    su -c 'rm -f /etc/localtime && ln -s /usr/share/zoneinfo/Europe/Moscow /etc/localtime'
ENV RUN_DEBUG=false;
EXPOSE 8080
EXPOSE 8000

ONBUILD ADD ./ext /usr/app/ext
CMD sleep 10; \
./Starter.sh
