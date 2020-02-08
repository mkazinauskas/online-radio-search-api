import { SIGN_IN, SIGN_OUT } from "./types"

export const signIn = (keycloak) => {
    return {
        type: SIGN_IN,
        payload: keycloak
    }
}

export const signOut = (keycloak) => {
    return {
        type: SIGN_OUT,
        payload: keycloak
    }
}
