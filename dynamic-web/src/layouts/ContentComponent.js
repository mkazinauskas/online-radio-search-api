import React from 'react';
import { Layout } from 'antd';
import {
    Switch,
    Route
} from "react-router-dom";
import RadioStationsView from '../pages/radioStations/RadioStationsView';
import MainView from '../pages/main/MainView';
import { HOME, RADIO_STATIONS } from './pathTypes'

export default () => (
    <Layout.Content
        style={{
            margin: 10,
            padding: 10,
            background: '#fff',
            minHeight: 280,
        }}
    >
        <Switch>
            <Route exact path={HOME}>
                <MainView />
            </Route>
            <Route exact path={RADIO_STATIONS}>
                <RadioStationsView />
            </Route>
        </Switch>
    </Layout.Content >
);;