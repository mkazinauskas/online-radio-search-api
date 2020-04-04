docker run --rm -v "$PWD":/app -w /app openjdk:11 ./gradlew clean build
docker build -t ors-static-web .