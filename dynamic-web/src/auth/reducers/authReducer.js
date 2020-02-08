
import { SIGN_IN, SIGN_OUT } from "../actions/types";

const INITIAL_STATE = {
    keycloak: null
}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case SIGN_IN:
            return { ...state, keycloak: action.payload }
        case SIGN_OUT:
            return { ...state, keycloak: action.payload }
        default:
            return state;
    }
};