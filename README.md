[![Build Status](https://travis-ci.org/modestukasai/online-radio-search.svg?branch=master)](https://travis-ci.org/modestukasai/online-radio-search)
[![codecov](https://codecov.io/gh/modestukasai/online-radio-search/branch/master/graph/badge.svg)](https://codecov.io/gh/modestukasai/online-radio-search)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/49433a89024e4a0ca901bc26ad9b7418)](https://app.codacy.com/manual/modestukasai/online-radio-search?utm_source=github.com&utm_medium=referral&utm_content=modestukasai/online-radio-search&utm_campaign=Badge_Grade_Dashboard)

# OnlineRadioSearch.com source code

## How to run

### Dev Environment
* Please run `./run-dev.sh`
* Then go to each project folder and execute `./gradlew bootRun`

### Start all
* Just run `./run.sh` in root directory 

## API
[![Known Vulnerabilities in API](https://snyk.io/test/github/modestukasai/online-radio-search/badge.svg?targetFile=api/build.gradle)](https://snyk.io/test/github/modestukasai/online-radio-search?targetFile=api/build.gradle)
Please check api folder

## As admin
### Get auth key
`curl -ss --data "grant_type=password&client_id=curl&username=joe_admin&password=admin" http://localhost:8081/auth/realms/online-radio-search/protocol/openid-connect/token`

### Curl secured endpoint
`curl -H "Authorization: bearer [your-token]" http://localhost:8080/admin/hello`

## As user
### Get auth key
`curl -ss --data "grant_type=password&client_id=curl&username=jim_user&password=admin" http://localhost:8081/auth/realms/online-radio-search/protocol/openid-connect/token`

### Curl secured endpoint
`curl -H "Authorization: bearer [your-token]" http://localhost:8080/user/hello`

# Keycloak config from scratch
Execute commands inside container
`docker exec -it springbootkeycloak_keycloak_1 /bin/bash`

# Update realms and users
1. Edit `keycloak/setup/setup.sh`
2. Run this file inside your local keycloak installation bin folder