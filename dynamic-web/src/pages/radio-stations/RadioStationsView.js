import React, { Component } from 'react';
import Keycloak from 'keycloak-js';

class RadioStationsView extends Component {

    constructor(props) {
        super(props);
        this.state = { keycloak: null, authenticated: false };
    }

    componentDidMount() {
        const keycloak = Keycloak({
            "realm": "online-radio-search",
            "url": "http://localhost:8081/auth/",
            "ssl-required": "external",
            "resource": "online-radio-search-dynamic-web",
            "public-client": true,
            "confidential-port": 0,
            "clientId": "online-radio-search-dynamic-web"
        });
        console.log('start');
        console.log(keycloak);
        keycloak
            .init({ promiseType: 'native', onLoad: 'login-required' })
            .then(authenticated => {
                console.log('init');
                console.log(authenticated);
                this.setState({ keycloak: keycloak, authenticated: authenticated })
            });
    }

    render() {
        if (this.state.keycloak) {
            if (this.state.authenticated) return (
                <div>
                    <p>This is a Keycloak-secured component of your application. You shouldn't be able
                to see this unless you've authenticated with Keycloak.</p>
                </div>
            ); else return (<div>Unable to authenticate!</div>)
        }
        return (
            <div>Initializing Keycloak...</div>
        );
        // return (
        //     <span>Radio stations view</span>
        // );
    }
}

export default RadioStationsView;

