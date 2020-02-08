
import { SIGN_IN, SIGN_OUT, LOADED } from "../actions/types";

const INITIAL_STATE = {
    keycloak: null,
    loading: true,
    authenticated: false
}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case SIGN_IN:
            state.keycloak.login();
            return {
                ...state,
                loading: true,
                authenticated: false
            }
        case SIGN_OUT:
            state.keycloak.logout();
            return {
                ...state,
                loading: true,
                authenticated: false
            }
        case LOADED:
            console.log(action.payload);
            return {
                ...state,
                keycloak: action.payload,
                loading: false,
                authenticated: action.payload.authenticated
            }
        default:
            return state;
    }
};