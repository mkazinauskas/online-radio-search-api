import React, { Component } from 'react';
import { Modal, Form, Select, Input, Button, Icon } from 'antd';

const { Option } = Select;

class AddRadioStationModalComponent extends Component {

    state = { visible: false };

    handleSubmit = e => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
            }
        });
    };

    handleSelectChange = value => {
        console.log(value);
        this.props.form.setFieldsValue({
            note: `Hi, ${value === 'male' ? 'man' : 'lady'}!`,
        });
    };

    showModal = () => {
        this.setState({
            visible: true,
        });
    };

    handleOk = e => {
        console.log(e);
        this.setState({
            visible: false,
        });
    };

    handleCancel = e => {
        console.log(e);
        this.setState({
            visible: false,
        });
    };

    render() {
        const { getFieldDecorator } = this.props.form;
        return (
            <span>
                <Button type="primary" onClick={this.showModal}>
                    <Icon type="plus-circle" theme="filled" />
                    Add new station
                </Button>
                <Modal
                    title="Add New Radio Station"
                    visible={this.state.visible}
                    onOk={this.handleOk}
                    onCancel={this.handleCancel}
                >
                    <Form labelCol={{ span: 5 }} wrapperCol={{ span: 12 }} onSubmit={this.handleSubmit}>
                        <Form.Item label="Note">
                            {getFieldDecorator('note', {
                                rules: [{ required: true, message: 'Please input your note!' }],
                            })(<Input />)}
                        </Form.Item>
                        <Form.Item label="Gender">
                            {getFieldDecorator('gender', {
                                rules: [{ required: true, message: 'Please select your gender!' }],
                            })(
                                <Select
                                    placeholder="Select a option and change input text above"
                                    onChange={this.handleSelectChange}
                                >
                                    <Option value="male">male</Option>
                                    <Option value="female">female</Option>
                                </Select>,
                            )}
                        </Form.Item>
                        <Form.Item wrapperCol={{ span: 12, offset: 5 }}>
                            <Button type="primary" htmlType="submit">
                                Submit
          </Button>
                        </Form.Item>
                    </Form>
                </Modal>
            </span>
        );
    }
}

export default Form.create({ name: 'coordinated' })(AddRadioStationModalComponent);

