import React, {Component} from 'react';
import PropTypes from 'prop-types';
import RoleItem from './RoleItem';
import axios from 'axios';
import _ from 'lodash';

class Roles extends Component {
    constructor(props) {
        super(props);
        this.state = {
            roleMap: {}
        };
        this.handleCheck = this.handleCheck.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        const that = this;

        axios.get('/api/roles')
            .then(function (oRes) {
                const allRoles = oRes.data;

                if (_.isArray(allRoles) && allRoles.length > 0) {
                    const {roleMap} = that.state;
                    const assignedRoles = nextProps.roles;

                    // init roleMap
                    allRoles.map((role) => {
                        if (!roleMap[role.id]) {
                            role.assigned = false;
                            role.roleIndex = -1;
                            roleMap[role.id] = role;
                        }
                    });

                    assignedRoles.map((assignedRole, index) => {
                        const role = roleMap[assignedRole.id];

                        role.assigned = true;
                        role.roleIndex = index;
                    });

                    that.setState({
                        roleMap: roleMap
                    });
                }
            })
            .catch(function (error) {
                console.error(`Error occur when get roles - ${error}`);
            });
    }

    handleCheck(roleId, isChecked) {
        const role = this.state.roleMap[roleId];
        const hasAssigned = role.assigned;

        role.assigned = isChecked;
        this.props.onRoleAssign(role, hasAssigned, function (newRoleIndex) {
            role.roleIndex = newRoleIndex;
        });
    }

    render() {
        const {children, ...others} = this.props;
        const {roleMap} = this.state;

        return (
            <div>
                {Object.keys(roleMap).map((roleId) =>
                    <RoleItem key={roleId} role={roleMap[roleId]} onCheck={this.handleCheck}/>
                )}
            </div>
        );
    }
}

Roles.defaultProps = {
    roles: [],
    onRoleAssign: function () {
    }
};

Roles.propTypes = {
    roles: PropTypes.array,
    onRoleAssign: PropTypes.func
};

export default Roles;