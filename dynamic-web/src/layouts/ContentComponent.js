import React from 'react';
import { Layout } from 'antd';
import {
    Switch,
    Route
} from "react-router-dom";
import RadioStationsView from '../pages/radioStations/RadioStationsView';
import MainView from '../pages/main/MainView';
import { HOME, RADIO_STATIONS, SONGS } from './pathTypes'
import SongsView from '../pages/songs/SongsView';

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
            <Route path={RADIO_STATIONS}>
                <RadioStationsView />
            </Route>
            <Route path={SONGS}>
                <SongsView />
            </Route>
        </Switch>
    </Layout.Content >
);;