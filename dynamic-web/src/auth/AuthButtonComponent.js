import React, { Component } from 'react';

import { signIn, signOut } from '../auth/actions';
import { connect } from 'react-redux';

class AuthButtonComponent extends Component {

    componentDidMount() {
        this.props.keycloak
            .init({ promiseType: 'native', onLoad: 'check-sso' })
            .then(authenticated => authenticated ? this.props.signIn() : this.props.signOut());
    }

    render() {
        if (this.props.authenticated === undefined) {
            return (<span>Loading...</span>);
        }
        if (this.props.authenticated) {
            return (
                <span onClick={this.signOut}>Logout</span>
            );
        }
        return (<span onClick={this.signIn}>Login</span>);
    }

    signIn = () => {
        this.props.keycloak.login();
    }

    signOut = () => {
        this.props.keycloak.logout();
    }
}

const mapStateToProps = (state) => {
    return {
        keycloak: state.auth.keycloak,
        authenticated: state.auth.keycloak.authenticated
    }
}

export default connect(mapStateToProps, { signIn, signOut })(AuthButtonComponent);