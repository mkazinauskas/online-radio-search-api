import React, { Component } from 'react';
import { BrowserRouter as Router } from "react-router-dom";
import './App.css';
import { Layout, Icon } from 'antd';
import LeftSideMenuComponent from './layouts/LeftSideMenuComponent';
import TopMenuComponent from './layouts/TopMenuComponent';
import ContentComponent from './layouts/ContentComponent';

const { Header, Footer } = Layout;

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
            <ContentComponent />
            <Footer>Test</Footer>
          </Layout>
        </Layout>
      </Router>
    );
  }
}

export default App;
