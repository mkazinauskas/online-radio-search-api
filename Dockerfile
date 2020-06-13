FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
RUN mkdir /opt/app
WORKDIR /opt/app
COPY build/libs/api-*.jar /opt/app/api.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /opt/app/api.jar