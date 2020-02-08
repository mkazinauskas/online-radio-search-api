
import { SIGN_IN, SIGN_OUT, LOADED } from "../actions/types";

const INITIAL_STATE = {
    keycloak: null,
    loading: true
}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case SIGN_IN:
            state.keycloak.login();
            return { ...state, loading: true }
        case SIGN_OUT:
            state.keycloak.logout();
            return { ...state, loading: true }
        case LOADED:
            return { ...state, keycloak: action.payload, loading: false }
        default:
            return state;
    }
};