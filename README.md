[![Build on Push](https://github.com/mkazinauskas/online-radio-search-api/actions/workflows/build-on-push.yml/badge.svg)](https://github.com/mkazinauskas/online-radio-search-api/actions/workflows/build-on-push.yml)
[![codecov](https://codecov.io/gh/mkazinauskas/online-radio-search-api/branch/master/graph/badge.svg)](https://codecov.io/gh/mkazinauskas/online-radio-search-api)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d321ac221f864a2192d7a64331968db5)](https://www.codacy.com/gh/mkazinauskas/online-radio-search-api/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mkazinauskas/online-radio-search-api&amp;utm_campaign=Badge_Grade)

# OnlineRadioSearch.com API source code

## How to run

* Checkout repo [https://github.com/mkazinauskas/online-radio-search-local-env](https://github.com/mkazinauskas/online-radio-search-local-env)
* Follow instructions in checkout repo README.md file
* Execute `./gradlew bootRun` file in this project

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
