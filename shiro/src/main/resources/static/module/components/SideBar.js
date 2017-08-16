import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import PropTypes from 'prop-types';

const navigations = [
    {
        path: '/users',
        label: 'Users'
    },
    {
        path: '/roles',
        label: 'Roles'
    }
];

class SideBar extends Component {
    constructor(props) {
        super(props);
    }

    handleRequestChange(isOpen) {
        console.log(`The sidebar open status is - ${isOpen}`);
        this.props.onToggle();
    }

    handleNavigation(sPath) {
        const {history} = this.props;

        history.push(sPath);
        this.props.onToggle();
    }

    render() {
        const {children, ...others} = this.props;

        return (
            <Drawer docked={false} open={this.props.open} onRequestChange={(isOpen) => this.handleRequestChange(isOpen)}>
                {navigations.map((navigation, index) =>
                    <MenuItem key={index} primaryText={navigation.label} onTouchTap={() => this.handleNavigation(navigation.path)} />
                )}
            </Drawer>
        );
    }
}

SideBar.defaultProps = {
    open: false,
    onToggle: function(){}
};

SideBar.propTypes = {
    open: PropTypes.bool,
    onToggle: PropTypes.func
};

export default withRouter(SideBar);