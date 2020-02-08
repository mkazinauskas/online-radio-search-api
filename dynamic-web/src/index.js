import React from 'react';
import ReactDOM from 'react-dom';
import MainLayout from './layouts/MainLayout';
import 'antd/dist/antd.css';
import './index.css';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import authReducers from './auth/reducers';

const authStore = createStore(authReducers);

ReactDOM.render(
  <Provider store={authStore}>
    <MainLayout />
  </Provider>,
  document.getElementById('root')
);
