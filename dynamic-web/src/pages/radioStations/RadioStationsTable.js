import React, { Component } from 'react';
import { Result, Button, Table } from 'antd';
import Axios from 'axios';

const columns = [
    {
        title: 'Id',
        dataIndex: 'radioStation.id',
        width: '10%',
    },
    {
        title: 'Title',
        dataIndex: 'radioStation.title',
    },
    {
        title: 'Actions',
        width: '20%',
    },
];

class RadioStationsTable extends Component {

    state = {
        data: [],
        pagination: {},
        filter: {
            page: 0,
            size: 10
        },
        error: false,
        loading: true,
    };

    componentDidMount() {
        this.loadData();
    }

    loadData = () => {
        this.setState({ ...this.state, loading: true, error: false });

        const urlSearchParams = new URLSearchParams();
        urlSearchParams.set('sort', 'id,desc');
        urlSearchParams.set('page', this.state.filter.page);
        urlSearchParams.set('size', this.state.filter.size);

        Axios.get('/radio-stations?' + urlSearchParams.toString())
            .then((response) => {
                let data = [];

                if (response.data._embedded && response.data._embedded.radioStationResourceList) {
                    data = response.data._embedded.radioStationResourceList;
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

    handleTableChange = (page) => {
        this.setState({
            ...this.state,
            data: [],
            filter: {
                page: page.current - 1,
                size: page.pageSize
            }
        }, this.loadData);
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
                rowKey={record => record.radioStation.id}
                dataSource={this.state.data}
                pagination={this.state.pagination}
                loading={this.state.loading}
                onChange={this.handleTableChange}
            />
        );
    }
}

export default RadioStationsTable;