import React, { Component } from 'react';

import { signIn, signOut } from '../../auth/actions';
import { connect } from 'react-redux';
import { Menu, Icon } from 'antd';

class AuthMenuComponent extends Component {

    wrapWithMenu(items) {
        return (
            <Menu style={{ float: 'right', lineHeight: '64px' }} theme="light" mode="horizontal">
                {items}
            </Menu>
        );
    }

    renderLoadingUser() {
        return this.wrapWithMenu(
            <Menu.Item key={0}>
                <Icon type="user" />
                <span>Loading...</span>
            </Menu.Item>
        );
    }

    renderUnauthorizedUser() {
        return this.wrapWithMenu(
            <Menu.Item key={0} onClick={this.props.signIn}>
                <Icon type="user" />
                <span>Login/Register</span>
            </Menu.Item>
        );
    }

    renderAuthorizedUser() {
        return this.wrapWithMenu(
            <Menu.Item key={0} onClick={this.props.signOut}>
                <Icon type="user" />
                <span>Logout</span>
            </Menu.Item>
        );
    }

    render() {
        if (this.props.loading) {
            return this.renderLoadingUser();
        }
        if (this.props.authenticated) {
            return this.renderAuthorizedUser();
        }
        return this.renderUnauthorizedUser();
    }
}

const mapStateToProps = (state) => {
    return {
        loading: state.auth.loading,
        authenticated: state.auth.authenticated
    }
}

export default connect(mapStateToProps, { signIn, signOut })(AuthMenuComponent);