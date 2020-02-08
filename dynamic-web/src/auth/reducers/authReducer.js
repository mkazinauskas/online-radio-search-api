
import { SIGN_IN, SIGN_OUT } from "../actions/types";
import { keycloakConfiguration } from "../keycloakConfiguration";

const INITIAL_STATE = {
    keycloak: keycloakConfiguration
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