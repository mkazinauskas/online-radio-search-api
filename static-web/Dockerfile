FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
RUN mkdir /opt/app
WORKDIR /opt/app
COPY build/libs/web-*.jar /opt/app/web.jar
ENTRYPOINT ["java", "-jar", "/opt/app/web.jar"]