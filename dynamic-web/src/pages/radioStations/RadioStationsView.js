import React, { Component } from 'react';
import RadioStationsTableComponent from './RadioStationsTableComponent';
import AddRadioStationButton from './addStation/AddRadioStationButton';

class RadioStationsView extends Component {

    render() {
        return (
            <div>
                <div style={{ marginBottom: 10 }}>
                    <AddRadioStationButton />
                </div>
                <RadioStationsTableComponent />
            </div>
        );
    }
}

export default RadioStationsView;

