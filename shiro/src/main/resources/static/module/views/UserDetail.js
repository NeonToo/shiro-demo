import React from 'react';
import {withRouter} from 'react-router-dom';
import {Card, CardTitle, CardActions, CardText} from 'material-ui/Card';
import {Tabs, Tab} from 'material-ui/Tabs';
import RaisedButton from 'material-ui/RaisedButton';
import Roles from './../components/Roles';
import axios from 'axios';

class UserDetail extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: {
                roles: [
                    {
                        id: 1,
                        name: 'ADMIN',
                        permissions: []
                    }
                ]
            }
        };
        this.handleSave = this.handleSave.bind(this);
        this.handleRoleAssign = this.handleRoleAssign.bind(this);
    }

    componentWillMount() {
        const that = this;
        const {match} = this.props;
        const userId = match.params.id;

        axios.get(`/api/users/${userId}`)
            .then(function(oRes) {
                const user = oRes.data;

                if(user) {
                    const roles = user.roles;

                    roles.map((role) => {
                        role.assigned = true;
                        return role;
                    });
                    that.setState({
                        user: user
                    });
                }
            })
            .catch(function(error) {
               console.error(`Error occur when get user detail - ${error}`);
            });
    }

    handleSave() {
        const that = this;
        const {match} = this.props;
        const userId = match.params.id;
        const {user} = this.state;
        const newUser = Object.assign({}, user, {
            roles: user.roles.filter((role) => {
                return role.assigned;
            })
        });

        console.log(newUser);
        // axios.put(`/api/users/${userId}`, newUser)
        //     .then(function(oRes) {
        //
        //     })
        //     .catch(function(error) {
        //         console.error(`Error occur when update user - ${error}`);
        //     });
    }

    handleRoleAssign(role, hasAssigned, fnAssignDone) {
       const roles = this.state.user.roles;

        if(hasAssigned) {
            const originRole = roles[role.roleIndex];

            originRole.assigned = !originRole.assigned;
        } else {
            const newRole = {
                id: role.id,
                name: role.name,
                assigned: true
            };
            fnAssignDone(roles.push(newRole) - 1);
        }
        this.setState({
            user: Object.assign({}, this.state.user, {
                roles: roles
            })
        });
    }

    render() {
        console.log('user detail render');
        const {user} = this.state;

        return (
            <Card>
                <CardTitle title={`${user.id} - ${user.username}`}/>
                <CardActions>
                    <RaisedButton label="Save" primary={true} onTouchTap={this.handleSave} />
                    <RaisedButton label="Actions"/>
                </CardActions>
                <CardText>
                    <Tabs>
                        <Tab label="Assigned Roles">
                            <Roles roles={user.roles} onRoleAssign={this.handleRoleAssign} />
                        </Tab>
                    </Tabs>
                </CardText>
            </Card>
        );
    }
}

export default withRouter(UserDetail);