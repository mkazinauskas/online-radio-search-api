import React from 'react';
import AuthButtonComponent from '../auth/AuthButtonComponent';
import { Menu, Icon } from 'antd';

const TopMenu = () => (
    <Menu style={{ float: 'right', lineHeight: '64px' }} theme="light" mode="horizontal">
        <Menu.Item key='1'>
            <Icon type="user" />
            <AuthButtonComponent />
        </Menu.Item>
    </Menu>
);

export default TopMenu;