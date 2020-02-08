import React, { Component } from 'react';
import { Table } from 'antd';
import Axios from 'axios';
// import { connect } from 'react-redux';

const columns = [
    {
        title: 'Name',
        dataIndex: 'name',
        sorter: true,
        render: name => `${name.first} ${name.last}`,
        width: '20%',
    },
    {
        title: 'Gender',
        dataIndex: 'gender',
        filters: [{ text: 'Male', value: 'male' }, { text: 'Female', value: 'female' }],
        width: '20%',
    },
    {
        title: 'Email',
        dataIndex: 'email',
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
            .then(console.log)
            .catch(console.log);
    }

    render() {
        return (
            <Table
                columns={columns}
                rowKey={record => record.login.uuid}
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
