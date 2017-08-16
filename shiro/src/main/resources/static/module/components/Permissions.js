import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow} from 'material-ui/Table';
import PermissionItem from './PermissionItem';

class Permissions extends Component {
    constructor(props) {
        super(props);
        this.state = {
            resources: {
                ALL: {
                    name: 'ALL',
                    assigned: false,
                    permissionIndex: -1,
                    read: 'NO_RESTRICTION',
                    write: 'NO_RESTRICTION'
                },
                USER: {
                    name: 'USER',
                    assigned: false,
                    permissionIndex: -1,
                    read: 'NO_RESTRICTION',
                    write: 'NO_RESTRICTION'
                },
                ROLE: {
                    name: 'ROLE',
                    assigned: false,
                    permissionIndex: -1,
                    read: 'NO_RESTRICTION',
                    write: 'NO_RESTRICTION'
                }
            }
        };
        this.handleCheck = this.handleCheck.bind(this);
        this.handleReadRestrictionChange = this.handleReadRestrictionChange.bind(this);
        this.handleWriteRestrictionChange = this.handleWriteRestrictionChange.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        const {permissions} = nextProps;
        const {resources} = this.state;

        permissions.map((permission, index) => {
            const resource = resources[permission.resource];

            resource.assigned = permission.assigned;
            resource.permissionIndex = index;
            resource.read = permission.read;
            resource.write = permission.write;

            // return resource;
        });
    }

    handleCheck(resourceIndex, isChecked) {
        const resource = this.state.resources[resourceIndex];
        const hasAssigned = resource.assigned;

        resource.assigned = isChecked;
        this.props.onPermissionAssign(resource, hasAssigned, function(permissionIndex) {
            resource.permissionIndex = permissionIndex;
        });
    }

    handleReadRestrictionChange(resourceIndex, readRestrictionIndex) {
        const permissionIndex = this.state.resources[resourceIndex].permissionIndex;

        this.state.resources[resourceIndex].read = readRestrictionIndex;
        this.props.onPermissionReadRestrictionChange(permissionIndex, readRestrictionIndex);
    }

    handleWriteRestrictionChange(resourceIndex, writeRestrictionIndex) {
        const permissionIndex = this.state.resources[resourceIndex].permissionIndex;

        this.state.resources[resourceIndex].write = writeRestrictionIndex;
        this.props.onPermissionWriteRestrictionChange(permissionIndex, writeRestrictionIndex);
    }

    render() {
        const {children, ...others} = this.props;
        const {resources} = this.state;

        return (
            <Table fixedHeader={true}>
                <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                    <TableRow>
                        <TableHeaderColumn>Assigned</TableHeaderColumn>
                        <TableHeaderColumn>Resource</TableHeaderColumn>
                        <TableHeaderColumn>Read Restriction</TableHeaderColumn>
                        <TableHeaderColumn>Write Restriction</TableHeaderColumn>
                    </TableRow>
                </TableHeader>
                <TableBody showRowHover={true} displayRowCheckbox={false}>
                    {Object.keys(resources).map((resourceName, resourceIndex) =>
                        <PermissionItem key={resourceIndex} resource={resources[resourceName]}
                                        onCheck={this.handleCheck}
                                        onReadRestrictionChange={this.handleReadRestrictionChange}
                                        onWriteRestrictionChange={this.handleWriteRestrictionChange}/>
                    )}
                    {/*{resources.map((resource, resourceIndex) =>*/}
                        {/*<PermissionItem key={resourceIndex} resource={resource}*/}
                                        {/*onCheck={this.handleCheck}*/}
                                        {/*onReadRestrictionChange={this.handleReadRestrictionChange}*/}
                                        {/*onWriteRestrictionChange={this.handleWriteRestrictionChange}/>*/}
                    {/*)}*/}
                </TableBody>
            </Table>
        );
    }
}

Permissions.defaultProps = {
    permissions: [],
    onPermissionAssign: function() {},
    onPermissionReadRestrictionChange: function () {
    },
    onPermissionWriteRestrictionChange: function () {
    }
};

Permissions.propTypes = {
    permissions: PropTypes.array,
    onPermissionAssign: PropTypes.func,
    onPermissionReadRestrictionChange: PropTypes.func,
    onPermissionWriteRestrictionChange: PropTypes.func
};

export default Permissions;