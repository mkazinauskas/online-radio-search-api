ROOT=$(pwd)

cd api
./build.sh
cd ROOT

cd static-web
./build.sh
cd ROOT



docker-compose up