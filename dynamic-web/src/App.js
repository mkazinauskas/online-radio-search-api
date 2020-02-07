import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import './App.css';
import { Layout, Menu, Icon } from 'antd';
import RadioStationsView from './pages/radio-stations/RadioStationsView';
import AuthButtonComponent from './auth/AuthButtonComponent';

const { Header, Content, Sider, Footer } = Layout;

class App extends Component {

  state = {
    collapsed: false,
  };

  toggle = () => {
    this.setState({
      collapsed: !this.state.collapsed,
    });
  };

  render() {
    return (
      <Router>
        <Layout>
          <Sider trigger={null} collapsible collapsed={this.state.collapsed}>
            <div className="logo" />
            <Menu theme="dark" mode="inline" defaultSelectedKeys={['1']}>
              <Menu.Item key="/radio-stations">
                <Link to="/radio-stations"><Icon type="database" />Radio Stations</Link>
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
          <Layout>
            <Header style={{ background: '#fff', padding: 0 }}>
              <Icon
                className="trigger"
                type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}
                onClick={this.toggle}
              />
              <Menu style={{ float: 'right', lineHeight: '64px' }} theme="light" mode="horizontal">
                <Menu.Item key='1'>
                  <Icon type="user" />
                  <AuthButtonComponent />
                </Menu.Item>
              </Menu>
            </Header>
            <Content
              style={{
                margin: '24px 16px',
                padding: 24,
                background: '#fff',
                minHeight: 280,
              }}
            >
              <Switch>
                <Route exact path="/">
                  <div>
                    Just home
                  </div>
                </Route>
                <Route exact path="/radio-stations">
                  <RadioStationsView />
                </Route>
              </Switch>
            </Content>
            <Footer>Test</Footer>
          </Layout>
        </Layout>
      </Router>
    );
  }
}

export default App;
