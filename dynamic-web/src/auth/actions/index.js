import { SIGN_IN, SIGN_OUT, LOADED } from "./types"

export const signIn = () => {
    return {
        type: SIGN_IN
    }
}

export const signOut = () => {
    return {
        type: SIGN_OUT
    }
}

export const refresh = (keycloak) => {
    return {
        type: LOADED,
        payload: keycloak
    }
}
