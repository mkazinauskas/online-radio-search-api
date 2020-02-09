import React, { Component } from 'react';
import { Table } from 'antd';
import Axios from 'axios';
// import { connect } from 'react-redux';

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
            size: 20
        },
        loading: true,
    };

    componentDidMount() {
        this.loadData();
    }

    loadData() {
        const urlSearchParams = new URLSearchParams();
        urlSearchParams.set('sort', 'id,desc');
        urlSearchParams.set('page', this.state.filter.page);
        urlSearchParams.set('size', this.state.filter.size);

        Axios.get('/radio-stations?' + urlSearchParams.toString())
            .then((response) => {
                this.setState({
                    ...this.state,
                    data: response.data._embedded.radioStationResourceList,
                    pagination: {
                        total: response.data.page.totalElements,
                        pageSize: response.data.page.size,
                        current: response.data.page.number + 1
                    }
                })

            })
            .catch(console.log)
            .then(() => this.setState({ ...this.state, loading: false }));
    }

    handleTableChange = (page) => {
        this.setState({
            ...this.state,
            data: [],
            loading: true,
            filter: {
                page: page.current - 1,
                size: page.pageSize
            }
        }, this.loadData);
    }

    render() {
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

// const mapStateToProps = (state) => {
//     return {
//         authenticated: state.auth.authenticated,
//         token: state.auth.token
//     }
// }

export default RadioStationsTable;

// connect(mapStateToProps)(RadioStationsView);;
