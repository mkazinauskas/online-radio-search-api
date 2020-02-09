import React, { Component } from 'react';
import RadioStationsTableComponent from './RadioStationsTableComponent';
import AddRadioStationModalComponent from './AddRadioStationModalComponent';

class RadioStationsView extends Component {

    render() {
        return (
            <div>
                <div style={{ marginBottom: 10 }}>
                    <AddRadioStationModalComponent />
                </div>
                <RadioStationsTableComponent />
            </div>
        );
    }
}

export default RadioStationsView;

