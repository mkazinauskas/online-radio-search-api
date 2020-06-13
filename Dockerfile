FROM openjdk:11.0.7-jre
RUN mkdir /opt/app
WORKDIR /opt/app
COPY build/libs/api-*.jar /opt/app/api.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /opt/app/api.jar