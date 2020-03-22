import React, { Component } from 'react';
import StreamUrlsHeader from './StreamUrlsHeader';
import StreamUrlsTable from './StreamUrlsTable';
import CreateStreamUrlButton from './create/CreateStreamUrlButton';

class StreamUrlsView extends Component {

    render() {
        return (
            <div>
                <StreamUrlsHeader />
                <div style={{ marginTop: 10, marginBottom: 10 }}>
                    <CreateStreamUrlButton />
                </div>
                <StreamUrlsTable />
            </div>
        );
    }
}

export default StreamUrlsView;

