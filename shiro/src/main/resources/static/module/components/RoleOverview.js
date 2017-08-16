import React, {Component} from 'react';
import PropTypes from 'prop-types';
import TextField from 'material-ui/TextField';

class RoleOverview extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {role, children, ...others} = this.props;

        return (
            <div>
                <TextField floatingLabelText="Name" hintText="please enter role name" defaultValue={role.name} />
                <br/>
                <TextField floatingLabelText="Description" multiLine={true} hintText="please enter role description" defaultValue={role.description} />
            </div>
        );
    }
}

RoleOverview.defaultProps = {
    role: {}
};

RoleOverview.propTypes = {
    role: PropTypes.object
};

export default RoleOverview;