# ============ Production image ===================
FROM openjdk:8-jre-slim

RUN adduser --system app --home /app
USER app

WORKDIR /home/app

COPY build/libs/online-radio-search-api-*.jar online-radio-search-api.jar
COPY src/main/resources/application.yaml application.yaml

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -jar online-radio-search-api.jar" ]
