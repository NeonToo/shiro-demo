import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {setSignedInInfo} from './../actions';
import IconMenu from 'material-ui/IconMenu';
import MenuItem from 'material-ui/MenuItem';
import Divider from 'material-ui/Divider';
import IconButton from 'material-ui/IconButton';
import AccountCircleIcon from 'material-ui/svg-icons/action/account-circle';
import {white} from 'material-ui/styles/colors'

class SignedIn extends React.Component {
    constructor(props) {
        super(props);
        this.handleSignOut = this.handleSignOut.bind(this);
    }

    handleSignOut() {
        const {changeSignedInInfo} = this.props;

        axios.get('api/signout')
            .then(function(oRes) {
                changeSignedInInfo({
                    signedIn: false,
                    signedInUser: {}
                });
            })
            .catch(function(error) {
            });
    }

    render() {
        const {user} = this.props;

        return (
            <IconMenu iconButtonElement={
                <IconButton><AccountCircleIcon color={white}/></IconButton>
            }>
                <MenuItem primaryText={`Signed in as ${user.username}`}/>
                <Divider/>
                <MenuItem primaryText="Sign Out" onTouchTap={this.handleSignOut}/>
            </IconMenu>
        );
    }
}

SignedIn.defaultProps = {
    user: {}
};

SignedIn.propTypes = {
    user: PropTypes.object
};

const mapStateToProps = state => {
    return {
        isSignedIn: state.signin.signedIn
    };
};

const mapDispatchToProps = dispatch => {
    return {
        changeSignedInInfo(signedInInfo) {
            dispatch(setSignedInInfo(signedInInfo));
        }
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(SignedIn);