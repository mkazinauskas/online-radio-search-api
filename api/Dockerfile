# ============ Builder image ===================
FROM gcr.io/testing-226112/online-radio-search:latest as builder

WORKDIR /app

COPY . .

RUN ls -a

RUN ./gradlew build

# ============ Production image ===================
FROM openjdk:8-jre-slim

RUN adduser --system app --home /app
USER app

WORKDIR /home/app

COPY --from=builder /app/build/libs/online-radio-search-api-*.jar online-radio-search-api.jar
COPY --from=builder /app/src/main/resources/application.properties application.properties

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -jar online-radio-search-api.jar" ]