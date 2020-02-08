import React from 'react';
import AuthButtonComponent from './AuthButtonComponent';
import { Menu, Icon } from 'antd';

export default () => (
    <Menu style={{ float: 'right', lineHeight: '64px' }} theme="light" mode="horizontal">
        <Menu.Item key='1' onClick={()=>alert('Go')}>
            <Icon type="user" />
            <AuthButtonComponent />
        </Menu.Item>
    </Menu>
);;