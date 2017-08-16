import React from 'react';
import {withRouter} from 'react-router-dom';
import {Card, CardTitle, CardActions, CardText} from 'material-ui/Card';
import {Tabs, Tab} from 'material-ui/Tabs';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import Users from '../components/Users';
import Permissions from '../components/Permissions';
import axios from 'axios';

class RoleDetail extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            pathname: this.props.location.pathname,
            role: {
                name: '',
                description: '',
                permissions: []
            }
        };
        this.handleSave = this.handleSave.bind(this);
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
        this.handlePermissionAssign = this.handlePermissionAssign.bind(this);
        this.handleReadRestrictionChange = this.handleReadRestrictionChange.bind(this);
        this.handleWriteRestrictionChange = this.handleWriteRestrictionChange.bind(this);
    }

    componentWillMount() {
        const {match, location} = this.props;
        const pathname = location.pathname;

        if (pathname === '/role') { // new

        } else { // detail
            const that = this;
            const roleId = match.params.id;

            axios.get(`/api/roles/${roleId}`)
                .then(function (oRes) {
                    const data = oRes.data;

                    if (data) {
                        const permissions = data.permissions;

                        that.setState({
                            role: Object.assign({}, data, {
                                permissions: permissions.map((permission) => {
                                    permission.assigned = true;
                                    return permission;
                                })
                            })
                        });
                    }
                })
                .catch(function (error) {

                });
        }
    }

    handleSave() {
        if (this.props.location.pathname === '/role') { // new
            const newRole = Object.assign({}, this.state.role, {
                permissions: this.state.role.permissions.filter((permission) => {
                    return permission.assigned;
                })
            });

            axios.post('/api/roles', newRole)
                .then(function (oRes) {
                    const role = oRes.data;
                })
                .catch(function (error) {
                    console.error(`Error occur when new role - ${error}`);
                })
        } else { //update

        }
        //
        // console.log(Object.assign({}, this.state.role, {
        //     permissions: this.state.role.permissions.filter((permission) => {
        //         return permission.assigned;
        //     })
        // }));
    }

    handleNameChange(event) {
        this.setState({
            role: Object.assign({}, this.state.role, {name: event.target.value})
        });
    }

    handleDescriptionChange(event) {
        this.setState({
            role: Object.assign({}, this.state.role, {description: event.target.value})
        });
    }

    handlePermissionAssign(resource, hasAssigned, fnAssignDone) {
        if (hasAssigned) {
            const originPermission = this.state.role.permissions[resource.permissionIndex];

            originPermission.assigned = !originPermission.assigned;
        } else {
            const newPermission = {
                resource: resource.name,
                read: resource.read,
                write: resource.write,
                assigned: true
            };

            fnAssignDone(this.state.role.permissions.push(newPermission) - 1);
        }
    }

    handleReadRestrictionChange(index, changedRead) {
        this.state.role.permissions[index].read = changedRead;
    }

    handleWriteRestrictionChange(index, changedWrite) {
        this.state.role.permissions[index].write = changedWrite;
    }

    render() {
        const pathname = this.props.location.pathname;
        const {role} = this.state;

        return (
            <Card>
                <CardTitle title={pathname !== '/role' ? `${role.id} - ${role.name}` : 'New Role'}
                           subtitle={pathname !== '/role' ? role.description : ''}/>
                <CardActions>
                    <RaisedButton label="Save" primary={true} onTouchTap={this.handleSave}/>
                    <RaisedButton label="Actions"/>
                </CardActions>
                <CardText>
                    <Tabs>
                        <Tab label="Overview">
                            <TextField floatingLabelText="Name" hintText="please enter role name"
                                       value={role.name} onChange={this.handleNameChange}/>
                            <br/>
                            <TextField floatingLabelText="Description" multiLine={true}
                                       hintText="please enter role description" value={role.description}
                                       onChange={this.handleDescriptionChange}/>
                        </Tab>
                        <Tab label="Access Restriction">
                            <Permissions permissions={role.permissions} onPermissionAssign={this.handlePermissionAssign}
                                         onPermissionReadRestrictionChange={this.handleReadRestrictionChange}
                                         onPermissionWriteRestrictionChange={this.handleWriteRestrictionChange}/>
                        </Tab>
                        <Tab label="Assigned Users">
                            <Users users={role.users}/>
                        </Tab>
                    </Tabs>
                </CardText>
            </Card>
        );
    }
}

export default withRouter(RoleDetail);