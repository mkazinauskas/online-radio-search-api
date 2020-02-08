import React, { Component } from 'react';
import { Modal, Form, Input, Button, Icon, Alert } from 'antd';
import Axios from 'axios';
import { connect } from 'react-redux';

class AddRadioStationModalComponent extends Component {

    state = {
        visible: false,
        loading: false,
        submitted: false,
        successMessage: '',
        errorMessage: ''
    };

    handleSubmit = e => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                this.setState({ ...this.state, loading: true, successMessage: '', errorMessage: '' });
                const config = {
                    headers: {
                        Authorization: `Bearer ${this.props.token}`
                    }
                }

                const content = {
                    ...values
                }

                Axios.post('/admin/radio-stations', content, config)
                    .then(() => this.setState({ ...this.state, successMessage: 'Radio station was added', errorMessage: null }))
                    .catch(() => this.setState({ ...this.state, successMessage: null, errorMessage: 'Failed to add radio station' }))
                    .finally(this.setState({ ...this.state, loading: false }));
            }
        });
    };

    showModal = () => {
        this.setState({
            visible: true,
        });
    };

    handleCancel = e => {
        this.setState({
            visible: false,
        });
    };

    render() {
        const { getFieldDecorator } = this.props.form;

        const addStationButton = (
            <Button type="primary" onClick={this.showModal}>
                <Icon type="plus-circle" theme="filled" />
                Add new station
            </Button>
        );

        const form = (
            <Form labelCol={{ span: 5 }} wrapperCol={{ span: 12 }} onSubmit={this.handleSubmit}>
                <Form.Item label="Title">
                    {getFieldDecorator('title', {
                        rules: [{ required: true, message: 'Please input radio station title!' }],
                    })(<Input />)}
                </Form.Item>
            </Form>
        );

        const successMessage = this.state.successMessage
            ? (<Alert message={this.state.successMessage} showIcon type="success" />)
            : '';

        const errorMessage = this.state.errorMessage
            ? (<Alert message={this.state.errorMessage} showIcon type="error" />)
            : '';
        return (
            <span>
                {addStationButton}
                <Modal
                    title="Add New Radio Station"
                    visible={this.state.visible}
                    okText="Add"
                    okButtonProps={{ disabled: this.state.loading }}
                    onOk={this.handleSubmit}
                    onCancel={this.handleCancel}
                >
                    {successMessage}
                    {errorMessage}
                    {form}
                </Modal>
            </span>
        );
    }
}

const form = Form.create({ name: 'coordinated' })(AddRadioStationModalComponent)

const mapStateToProps = (state) => {
    return {
        authenticated: state.auth.authenticated,
        token: state.auth.token
    }
}

export default connect(mapStateToProps)(form);
