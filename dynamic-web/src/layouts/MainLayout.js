import React, { Component } from 'react';
import LoadingApplicationLayout from './LoadingApplicationLayout';
import DefaultApplicationLayout from './DefaultApplicationLayout';
import { connect } from 'react-redux';

class MainLayout extends Component {

  render() {
    if (this.props.loading) {
      return (<LoadingApplicationLayout />);
    } else {
      return (<DefaultApplicationLayout />);
    }
  }
}

const mapStateToProps = (state) => {
  return {
    loading: state.auth.loading,
  }
}

export default connect(mapStateToProps)(MainLayout);
