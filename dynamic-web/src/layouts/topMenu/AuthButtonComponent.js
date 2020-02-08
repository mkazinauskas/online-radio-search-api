import React, { Component } from 'react';

import { signIn, signOut } from '../../auth/actions';
import { connect } from 'react-redux';

class AuthButtonComponent extends Component {

    render() {
        if (this.props.loading) {
            return (<span>Loading...</span>);
        }
        if (this.props.authenticated) {
            return (
                <span onClick={this.props.signOut}>Logout</span>
            );
        }
        return (<span onClick={this.props.signIn}>Login</span>);
    }
}

const mapStateToProps = (state) => {
    return {
        loading: state.auth.loading,
        authenticated: state.auth.authenticated
    }
}

export default connect(mapStateToProps, { signIn, signOut })(AuthButtonComponent);