# Online Radio Search API

# Setup
1. Start containers `docker-compose up`
2. Start application `./gradlew bootRun`
3. Query application with commands

# Use application

If you want to see keycloak user authentication window visit [http://localhost:8081/auth/realms/online-radio-search/account](http://localhost:8081/auth/realms/online-radio-search/account)

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
1. Edit `docker/keycloak/setup/setup.sh`
2. Run this file inside your local keycloak installation bin folder

# Todo list:
* Latest searches
* Create elastic search repository for full text search
* Create docker images to run locally 
* Create search api for results