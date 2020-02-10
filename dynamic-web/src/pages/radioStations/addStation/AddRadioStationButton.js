import React, { Component } from 'react';
import { Button, Icon } from 'antd';
import { connect } from 'react-redux';
import AddRadioStationModal from './AddRadioStationModal';
import { ADMIN } from '../../../auth/resourceRoleType';
import { ONLINE_RADIO_SEARCH_API } from '../../../auth/resourceTypes';

class AddRadioStationButton extends Component {

    state = {
        visible: false,
    };

    showModal = () => {
        this.setState({ visible: true });
    };

    handleModalClose = e => {
        this.setState({ visible: false });
    };

    render() {
        if (!this.props.authenticated || !this.props.hasAdminRole) {
            return null;
        }
        return (
            <div>
                <Button type="primary" onClick={this.showModal}>
                    <Icon type="plus-circle" theme="filled" />
                    Add new station
                </Button>
                <AddRadioStationModal
                    key={new Date().getMilliseconds()}
                    visible={this.state.visible}
                    onModalClose={this.handleModalClose}
                />
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    const hasAdminRole = state.auth.authenticated ? state.auth.keycloak.hasResourceRole(ADMIN, ONLINE_RADIO_SEARCH_API) : false;
    return {
        authenticated: state.auth.authenticated,
        hasAdminRole
    }
}

export default connect(mapStateToProps)(AddRadioStationButton);
