import Keycloak from 'keycloak-js';

export const keycloakConfiguration = Keycloak({
    "realm": "online-radio-search",
    "url": "http://localhost:8081/auth/",
    "ssl-required": "external",
    "resource": "online-radio-search-dynamic-web",
    "public-client": true,
    "clientId": "online-radio-search-dynamic-web"
});