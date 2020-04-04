# Online Radio Search static web

## How to run in dev mode
* `./gradlew bootRUn`

## How to build
* `./gradlew build` or  `docker run --rm -v "$PWD":/app -w /app openjdk:11 ./gradlew clean build`
* `docker build -t ors-static-web .`

## How to run
* `docker run -e "application.apiUrl=http://localhost:8080" -p 3002:8080 ors-static-web`