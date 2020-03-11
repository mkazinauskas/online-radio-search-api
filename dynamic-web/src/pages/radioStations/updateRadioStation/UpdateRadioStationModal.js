import React, { Component } from 'react';
import { Modal, Form, Input, Alert, Switch, Select } from 'antd';
import Axios from 'axios';
import { connect } from 'react-redux';

const DEFAULT_STATE = {
    loading: false,
    successMessage: null,
    errorMessage: null
}

class UpdateRadioStationModal extends Component {

    state = {
        ...DEFAULT_STATE
    }

    handleSubmit = e => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                this.setState({ loading: true, successMessage: null, errorMessage: null });
                const config = {
                    headers: {
                        Authorization: `Bearer ${this.props.token}`
                    }
                }

                let content = {
                    title: values.title,
                    website: values.website,
                    enabled: values.enabled,
                    genres: values.genres ? values.genres.map(id => { return { id } }) : []
                }

                Axios.patch(`/admin/radio-stations/${this.props.radioStation.id}`, content, config)
                    .then(() => this.setState({ ...this.state, successMessage: 'Radio station was updated' }))
                    .catch(() => this.setState({ ...this.state, errorMessage: 'Failed to update radio station' }))
                    .then(() => this.setState({ ...this.state, loading: false }));
            }
        });
    };

    onCancel = () => {
        this.props.form.resetFields();
        this.props.onModalClose();
    }

    render() {
        const { getFieldDecorator } = this.props.form;
        const radioStation = this.props.radioStation;

        const options = radioStation.genres
            .map(genre => <Select.Option value={genre.id}>{genre.title}</Select.Option>);

        const form = (
            <Form
                layout='vertical'
                onSubmit={this.handleSubmit}
            >
                <Form.Item label="Title">
                    {getFieldDecorator('title', {
                        initialValue: radioStation.title,
                        rules: [{ required: true, message: 'Please input radio station title!' }],
                    })(<Input />)}
                </Form.Item>
                <Form.Item label="Website">
                    {getFieldDecorator('website', {
                        initialValue: radioStation.website,
                        rules: [{ required: false, type: 'url', message: 'Please input radio station website!' }],
                    })(<Input />)}
                </Form.Item>
                <Form.Item label="Enabled">
                    {getFieldDecorator('enabled', {
                        initialValue: radioStation.enabled,
                        valuePropName: 'checked',
                    })(<Switch />)}
                </Form.Item>
                <Form.Item label="Genres">
                    {getFieldDecorator('genres', {
                        initialValue: radioStation.genres.map(genre => genre.id),
                    })(
                        <Select
                            showSearch
                            showArrow={false}
                            mode="multiple"
                            notFoundContent="null"
                            placeholder="Please select genre">
                            {options}
                        </Select>
                    )}
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
                <Modal
                    title="Update Radio Station"
                    visible={this.props.visible}
                    okText="Update"
                    okButtonProps={{ disabled: this.state.loading }}
                    onOk={this.handleSubmit}
                    onCancel={this.onCancel}
                >
                    <div style={{ marginBottom: 10 }}>
                        {successMessage}
                        {errorMessage}
                    </div>
                    {form}
                </Modal>
            </span>
        );
    }
}

const form = Form.create({ name: 'coordinated' })(UpdateRadioStationModal)

const mapStateToProps = (state) => {
    return {
        token: state.auth.token
    }
}

export default connect(mapStateToProps)(form);
