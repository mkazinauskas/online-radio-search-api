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

class RadioStationsTableComponent extends Component {

    state = {
        data: [],
        pagination: {},
        loading: false,
    };

    componentDidMount() {
        // const config = {
        //     headers: {
        //         Authorization: `Bearer ${this.props.token}`
        //     }
        // }

        Axios.get('/radio-stations')
            .then((response) => {
                console.log(response);
                this.setState({
                    ...this.state, 
                    data: response.data._embedded.radioStationResourceList
                })

            })
            .catch(console.log);
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

export default RadioStationsTableComponent;

// connect(mapStateToProps)(RadioStationsView);;
