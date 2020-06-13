FROM adoptopenjdk/openjdk11:jre-11.0.7_10-alpine
RUN mkdir /opt/app
WORKDIR /opt/app
COPY build/libs/api-*.jar /opt/app/api.jar
ENTRYPOINT exec java $JAVA_OPTS -jar api.jar