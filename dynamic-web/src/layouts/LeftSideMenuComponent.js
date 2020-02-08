import React, { Component } from 'react';
import { createBrowserHistory } from "history";

import { Link } from "react-router-dom";

import { Layout, Menu, Icon } from 'antd';

const { Sider } = Layout;

class LeftSideMenuComponent extends Component {

    render() {
        const history = createBrowserHistory();
        return (
            <Sider trigger={null} collapsible collapsed={this.props.collapsed}>
                <div className="logo" />
                <Menu theme="dark" mode="inline" defaultSelectedKeys={[history.location.pathname]}>
                    <Menu.Item key="/">
                        <Link to="/" ><Icon type="database" /><span>Main</span></Link>
                    </Menu.Item>
                    <Menu.Item key="/radio-stations">
                        <Link to="/radio-stations" ><Icon type="database" /><span>Radio Stations</span></Link>
                    </Menu.Item>
                    <Menu.Item key="2">
                        <Icon type="video-camera" />
                        <span>nav 2</span>
                    </Menu.Item>
                    <Menu.Item key="3">
                        <Icon type="upload" />
                        <span>nav 3</span>
                    </Menu.Item>
                </Menu>
            </Sider>
        )
    }
}

export default LeftSideMenuComponent;