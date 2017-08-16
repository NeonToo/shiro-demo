import React from 'react';
import {NavLink} from 'react-router-dom';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';
import Users from '../components/Users';
import axios from 'axios';
import _ from 'lodash';

const linkStyle = {
    textDecoration: 'none'
};

class UserList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: []
        }
    }

    componentWillMount() {
        // this.setState({
        //     users: [{
        //         id: 1,
        //         username: 'User1'
        //     }, {
        //         id: 2,
        //         username: 'User2'
        //     }, {
        //         id: 3,
        //         username: 'User3'
        //     }]
        // });
        const that = this;

        axios.get('http://localhost:9001/api/users')
            .then(function (oRes) {
                const data = oRes.data;

                if (_.isArray(data) && data.length > 0) {
                    that.setState({
                        users: data
                    });
                }
            })
            .catch(function (error) {
                console.error(`Error occurred when get users - ${error}`);
            });
    }

    render() {
        const {users} = this.state;

        return (
            <Users users={users} />
        );

        // return (
        //     <Table fixedHeader={true}>
        //         <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
        //             <TableRow>
        //                 <TableHeaderColumn>ID</TableHeaderColumn>
        //                 <TableHeaderColumn>Username</TableHeaderColumn>
        //             </TableRow>
        //         </TableHeader>
        //         <TableBody showRowHover={true} displayRowCheckbox={false}>
        //             {users.map((user, index) =>
        //                 <TableRow key={index}>
        //                     <TableRowColumn>
        //                         <NavLink to={`/${user.id}`} style={linkStyle}>{user.id}</NavLink>
        //                     </TableRowColumn>
        //                     <TableRowColumn>{user.username}</TableRowColumn>
        //                 </TableRow>
        //             )}
        //         </TableBody>
        //     </Table>
        // );
    }
}

export default UserList;