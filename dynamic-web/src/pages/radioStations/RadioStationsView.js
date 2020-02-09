import React, { Component } from 'react';
import RadioStationsTable from './RadioStationsTable';
import AddRadioStationButton from './addStation/AddRadioStationButton';

class RadioStationsView extends Component {

    render() {
        return (
            <div>
                <div style={{ marginBottom: 10 }}>
                    <AddRadioStationButton />
                </div>
                <RadioStationsTable />
            </div>
        );
    }
}

export default RadioStationsView;

