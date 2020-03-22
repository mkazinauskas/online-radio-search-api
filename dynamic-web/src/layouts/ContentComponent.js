import React from 'react';
import { Layout } from 'antd';
import {
    Switch,
    Route
} from "react-router-dom";
import RadioStationsView from '../pages/radioStations/RadioStationsView';
import RadioStationStreamsView from '../pages/radioStations/streams/RadioStationStreamsView';
import MainView from '../pages/main/MainView';
import { HOME, RADIO_STATIONS, SONGS, RADIO_STATION_STREAMS, RADIO_STATION_SONGS, RADIO_STATION_STREAM_URLS } from './pathTypes'
import SongsView from '../pages/songs/SongsView';
import RadioStationSongsView from '../pages/radioStations/songs/RadioStationSongsView';
import StreamUrlsView from '../pages/radioStations/streams/urls/StreamUrlsView';

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
            <Route exact path={RADIO_STATION_STREAMS}>
                <RadioStationStreamsView />
            </Route>
            <Route exact path={RADIO_STATION_SONGS}>
                <RadioStationSongsView />
            </Route>
            <Route exact path={RADIO_STATION_STREAM_URLS}>
                <StreamUrlsView />
            </Route>
            <Route exact path={SONGS}>
                <SongsView />
            </Route>
        </Switch>
    </Layout.Content >
);;