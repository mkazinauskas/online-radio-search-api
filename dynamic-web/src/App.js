import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";
import './App.css';
import { Layout, Icon } from 'antd';
import RadioStationsView from './pages/radio-stations/RadioStationsView';
import LeftSideMenuComponent from './layouts/LeftSideMenuComponent';
import MainView from './pages/main/MainView';
import TopMenuComponent from './layouts/TopMenuComponent';

const { Header, Content, Footer } = Layout;

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
          <LeftSideMenuComponent collapsed={this.state.collapsed} />
          <Layout>
            <Header style={{ background: '#fff', padding: 0 }}>
              <Icon
                className="trigger"
                type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}
                onClick={this.toggle}
              />

              <TopMenuComponent />
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
                  <MainView />
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
