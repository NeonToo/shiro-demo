import React from 'react';
import {withRouter, NavLink} from 'react-router-dom';

const navAreaStyle = {
    margin: '12px 8px'
};

const navLinkStyle = {
    color: 'white',
    padding: 'auto 10px'
};

const spanStyle = {
    margin: '0 5px'
};

class Guest extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {location} = this.props;

        return (
            <div style={navAreaStyle}>
                <NavLink to={{pathname: "/signin", state: {prevLocation: location.pathname}}} className="nav-link" style={navLinkStyle}>Sign In</NavLink>
                <span style={spanStyle}>Or</span>
                <NavLink to="/signup" className="nav-link" style={navLinkStyle}>Sign Up</NavLink>
            </div>
        );
    }
}

export default withRouter(Guest);