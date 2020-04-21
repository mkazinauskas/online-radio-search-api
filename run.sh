ROOT=$(pwd)

cd api
./build.sh
cd $ROOT

docker-compose up