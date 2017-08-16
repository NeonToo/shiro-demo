import React from 'react';
import {withRouter, NavLink} from 'react-router-dom';
import {Card, CardTitle, CardActions, CardText} from 'material-ui/Card';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';
import RaisedButton from 'material-ui/RaisedButton';

const linkStyle = {
    textDecoration: 'none'
};

class RoleList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            roles: []
        };
        this.handleNew = this.handleNew.bind(this);
    }

    componentWillMount() {
        const that = this;

        axios.get('/api/roles')
            .then(function(oRes) {
                const roles = oRes.data;

                that.setState({
                    roles: roles
                });
            })
            .catch(function(error) {
                console.error(`Error occur when get roles - ${error}`);
            });
    }

    handleNew() {
        this.props.history.push('role');
    }

    render() {
        const {roles} = this.state;

        return (
            <Card>
                <CardTitle title="Roles"/>
                <CardActions>
                    <RaisedButton label="New" primary={true} onTouchTap={this.handleNew}/>
                </CardActions>
                <CardText>
                    <Table fixedHeader={true}>
                        <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                            <TableRow>
                                <TableHeaderColumn>ID</TableHeaderColumn>
                                <TableHeaderColumn>Name</TableHeaderColumn>
                            </TableRow>
                        </TableHeader>
                        <TableBody showRowHover={true} displayRowCheckbox={false}>
                            {roles.map((role, index) =>
                                <TableRow key={index}>
                                    <TableRowColumn>
                                        <NavLink to={`/roles/${role.id}`} style={linkStyle}>{role.id}</NavLink>
                                    </TableRowColumn>
                                    <TableRowColumn>{role.name}</TableRowColumn>
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </CardText>
            </Card>
        );
    }
}

export default withRouter(RoleList);