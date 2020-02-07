import Keycloak from 'keycloak-js';
import { SIGN_IN, SIGN_OUT } from "../actions/types";

const keycloak = Keycloak({
    "realm": "online-radio-search",
    "url": "http://localhost:8081/auth/",
    "ssl-required": "external",
    "resource": "online-radio-search-dynamic-web",
    "public-client": true,
    "confidential-port": 0,
    "clientId": "online-radio-search-dynamic-web"
});

const INITIAL_STATE = {
    keycloak
}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case SIGN_IN:
            return { ...state }
        case SIGN_OUT:
            return { ...state }
        default:
            return state;
    }
};