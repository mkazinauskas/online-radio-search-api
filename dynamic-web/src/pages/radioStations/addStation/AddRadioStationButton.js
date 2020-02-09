import React, { Component } from 'react';
import { Button, Icon } from 'antd';
import { connect } from 'react-redux';
import AddRadioStationModal from './AddRadioStationModal';

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
        if (!this.props.authenticated) {
            return null;
        }
        return (
            <div>
                <Button type="primary" onClick={this.showModal}>
                    <Icon type="plus-circle" theme="filled" />
                    Add new station
                </Button>
                <AddRadioStationModal
                    visible={this.state.visible}
                    onModalClose={this.handleModalClose}
                />
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        authenticated: state.auth.authenticated
    }
}

export default connect(mapStateToProps)(AddRadioStationButton);
