import React from 'react';
import PropTypes from 'prop-types';
import Snackbar from 'material-ui/Snackbar';

class SignInNotice extends React.Component {
    constructor(props) {
        super(props);
        this.state = {

        };
    }

    render() {
        const {open, ...others} = this.props;

        return (
            <Snackbar open={open} message="Please Sign In" action="Sign In" {...others} />
        );
    }
}

SignInNotice.defaultProps = {
    open: false
};

SignInNotice.propTypes = {
    open: PropTypes.bool
};

export default SignInNotice;