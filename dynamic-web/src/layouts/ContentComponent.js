import React from 'react';
import { Layout } from 'antd';
import {
    Switch,
    Route
} from "react-router-dom";
import RadioStationsView from '../pages/radio-stations/RadioStationsView';
import MainView from '../pages/main/MainView';

export default () => (
    <Layout.Content
        style={{
            margin: '10px',
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
    </Layout.Content>
);;