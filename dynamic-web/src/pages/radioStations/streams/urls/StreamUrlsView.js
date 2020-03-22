import React, { Component } from 'react';
import StreamUrlsHeader from './StreamUrlsHeader';
import StreamUrlsTable from './StreamUrlsTable';

class StreamUrlsView extends Component {

    render() {
        return (
            <div>
                <StreamUrlsHeader />
                {/* <div style={{ marginTop: 10, marginBottom: 10 }}>
                    <AddRadioStationStreamButton />
                </div> */}
                <StreamUrlsTable />
            </div>
        );
    }
}

export default StreamUrlsView;

