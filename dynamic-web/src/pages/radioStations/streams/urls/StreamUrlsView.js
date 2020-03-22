import React, { Component } from 'react';
import AddRadioStationStreamButton from './add/AddRadioStationStreamButton';
import RadioStationStreamsTable from './StreamUrlsTable';
import StreamUrlsHeader from './StreamUrlsHeader';

class StreamUrlsView extends Component {

    render() {
        return (
            <div>
                <StreamUrlsHeader />
                <div style={{ marginTop: 10, marginBottom: 10 }}>
                    <AddRadioStationStreamButton />
                </div>
                <RadioStationStreamsTable />
            </div>
        );
    }
}

export default StreamUrlsView;

