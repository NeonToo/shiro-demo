import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {NavLink} from 'react-router-dom';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';

class AssignedUsers extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: []
        };
    }

    render() {
        const {users, children, ...others} = this.props;

        return (
            <Table fixedHeader={true}>
                <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                    <TableRow>
                        <TableHeaderColumn>ID</TableHeaderColumn>
                        <TableHeaderColumn>Name</TableHeaderColumn>
                    </TableRow>
                </TableHeader>
                <TableBody showRowHover={true} displayRowCheckbox={false}>
                    {users.map((user, index) =>
                        <TableRow key={index}>
                            <TableRowColumn>
                                <NavLink to={`/users/${user.id}`} className="nav-link">{user.id}</NavLink>
                            </TableRowColumn>
                            <TableRowColumn>{user.username}</TableRowColumn>
                        </TableRow>
                    )}
                </TableBody>
            </Table>
        );
    }
}

AssignedUsers.defaultProps = {
    users: []
};

AssignedUsers.propTypes = {
    users: PropTypes.array
};

export default AssignedUsers;