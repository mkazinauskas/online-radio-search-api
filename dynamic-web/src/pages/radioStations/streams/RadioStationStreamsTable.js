import { Button, Result, Table } from 'antd';
import Axios from 'axios';
import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { createURLRadioStationStreams } from '../../../layouts/pathTypes';
import DeleteRadioStationStreamButton from './delete/DeleteRadioStationStreamButton';
import RadioStationStreamLatestInfoButton from './latestInfo/RadioStationStreamLatestInfoButton';
import UpdateRadioStationStreamSongsButton from './songs/UpdateRadioStationStreamSongsButton';

const columns = [
    {
        title: 'Id',
        dataIndex: 'id',
        width: '10%',
    },
    {
        title: 'Url',
        dataIndex: 'url',
        width: '40%',
    },
    {
        title: 'Preview',
        key: 'preview',
        dataIndex: 'url',
        render: (text, record) => {
            return (
                <audio controls>
                    <source src={record.url} type="audio/ogg" />
                    <source src={record.url} type="audio/mpeg" />
                    <source src={record.url} type="audio/wav" />
                    Your browser does not support the audio element.
                </audio>
            );
        }
    },
    {
        title: 'Actions',
        key: 'operation',
        render: (text, record) => {
            return (
                <span>
                    <UpdateRadioStationStreamSongsButton
                        key={`fetch-${record.id}`}
                        radioStationId={record.radioStationId}
                        id={record.id}
                    />
                    <RadioStationStreamLatestInfoButton
                        key={`fetch-${record.id}`}
                        radioStationId={record.radioStationId}
                        id={record.id}
                    />
                    <DeleteRadioStationStreamButton
                        key={`delete-${record.id}`}
                        radioStationId={record.radioStationId}
                        id={record.id}
                    />
                </span>
            )
        },
    }
];

class RadioStationStreamsTable extends Component {

    state = {
        data: [],
        pagination: {
            total: null,
            pageSize: null,
            current: null
        },
        filter: {
            page: 0,
            size: 10,
        },
        error: false,
        loading: true,
    };

    componentDidMount() {
        this.loadDataWithSearchParams();
        this.unregisterHistoryListener = this.props.history.listen(() => {
            this.loadDataWithSearchParams();
        });
    }

    componentWillUnmount() {
        this.unregisterHistoryListener();
    }

    loadDataWithSearchParams = () => {
        const params = new URLSearchParams(this.props.history.location.search);

        let page = params.get('page');
        if (!page) {
            page = 1;
        }

        let size = params.get('size');
        if (!size) {
            size = 10;
        }

        const filter = {
            page: page - 1,
            size,
        };

        this.setState(
            { ...this.state, filter },
            this.loadData
        );
    }

    loadData = () => {
        this.setState({ ...this.state, loading: true, error: false });

        const urlSearchParams = new URLSearchParams();
        urlSearchParams.set('sort', 'id,desc');
        urlSearchParams.set('page', this.state.filter.page);
        urlSearchParams.set('size', this.state.filter.size);

        const radioStationId = this.props.match.params.radioStationId;

        Axios.get(`/radio-stations/${radioStationId}/streams?${urlSearchParams.toString()}`)
            .then((response) => {
                let data = [];
                if (response.data._embedded && response.data._embedded.radioStationStreamResourceList) {
                    data = response.data._embedded.radioStationStreamResourceList.map(element => {
                        return { ...element.radioStationStream, radioStationId }
                    });
                }

                if (!data.length && response.data.page.totalPages > 1) {
                    //If last item from page deleted, we want to go back to latest existing page
                    this.redirectToPage(response.data.page.totalPages, response.data.page.size);
                }

                this.setState({
                    ...this.state,
                    data,
                    pagination: {
                        total: response.data.page.totalElements,
                        pageSize: response.data.page.size,
                        current: response.data.page.number + 1
                    }
                })

            })
            .catch(() => { this.setState({ ...this.state, error: true }) })
            .then(() => this.setState({ ...this.state, loading: false }));
    }

    redirectToPage = (page, size) => {
        const urlSearchParams = new URLSearchParams();
        urlSearchParams.set('page', page);
        urlSearchParams.set('size', size);

        const radioStationId = this.props.match.params.radioStationId;
        this.props.history.push(createURLRadioStationStreams(radioStationId) + '?' + urlSearchParams.toString());
    }

    handleTableChange = (page) => {
        this.redirectToPage(page.current, page.pageSize);
    }

    render() {
        if (this.state.error) {
            return (
                <Result
                    status="error"
                    title="Failed to load radio stations"
                    subTitle="Please wait until service will be working again"
                    extra={[
                        <Button type="primary" key="console" onClick={this.loadData}>Retry</Button>,
                    ]}
                >
                </Result>
            );
        }
        return (
            <Table
                columns={columns}
                rowKey={record => record.id}
                dataSource={this.state.data}
                pagination={this.state.pagination}
                loading={this.state.loading}
                onChange={this.handleTableChange}
            />
        );
    }
}

export default withRouter(RadioStationStreamsTable);