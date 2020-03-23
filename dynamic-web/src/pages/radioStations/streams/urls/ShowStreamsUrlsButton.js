import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { createURLStreamUrls } from '../../../../layouts/pathTypes';
import { previousPath } from '../../../../utils/historyUtils';

class ShowRadioStationStreamsLink extends Component {

    render() {
        return (
            <Link to={{
                pathname: createURLStreamUrls(this.props.radioStationId, this.props.streamId),
                state: { previousPath: previousPath(this.props.location) }
            }}>Urls</Link>
        );
    }

}

export default withRouter(ShowRadioStationStreamsLink);