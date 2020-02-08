import { keycloakConfiguration } from "./keycloakConfiguration";
import { signIn, signOut } from "./actions";
import { createStore } from 'redux';
import authReducers from './reducers';

export const initialize = () => {
    const keycloak = keycloakConfiguration;
    const authStore = createStore(authReducers);

    keycloak
        .init({ promiseType: 'native', onLoad: 'check-sso' })
        .then(authenticated => {
            if (authenticated) {
                authStore.dispatch(signIn(keycloak));
            } else {
                authStore.dispatch(signOut(keycloak));
            }
        });
    return authStore;
}