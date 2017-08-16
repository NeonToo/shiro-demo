import React from 'react';
import {withRouter} from 'react-router-dom';
import {connect} from 'react-redux';
import {setSnackbarOpen} from './../actions';
import SignInNotice from './../components/SignInNotice';
import AppBar from 'material-ui/AppBar';
import Guest from '../components/Guest';
import SignedIn from './../components/SignedIn';
import SideBar from './../components/SideBar';

class AppContainer extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isSideBarOpen: false
        };
        this.handleSnackBarToggle = this.handleSnackBarToggle.bind(this);
        this.handleSideBarToggle = this.handleSideBarToggle.bind(this);
    }

    handleSnackBarToggle() {
        const {location, history, changeSnackBarOpen} = this.props;

        history.push({
            pathname: '/signin',
            state: {
                prevLocation: location.pathname
            }
        });
        changeSnackBarOpen(false);

    }

    handleSideBarToggle() {
        this.setState({
            isSideBarOpen: !this.state.isSideBarOpen
        });
    }

    render() {
        const {signedInInfo, isSnackBarOpen} = this.props;
        const {isSideBarOpen} = this.state;

        return (
            <div>
                <SignInNotice open={isSnackBarOpen} onActionTouchTap={this.handleSnackBarToggle}/>
                <AppBar title="SAP Demo" onLeftIconButtonTouchTap={this.handleSideBarToggle}
                        iconElementRight={signedInInfo.signedIn ? <SignedIn user={signedInInfo.signedInUser} /> :
                            <Guest/>}/>
                <SideBar open={isSideBarOpen} onToggle={this.handleSideBarToggle}/>
                <div>{this.props.children}</div>
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        signedInInfo: state.signin,
        isSnackBarOpen: state.snackbar.open
    }
};

const mapDispatchToProps = dispatch => {
    return {
        changeSnackBarOpen(isOpen) {
            dispatch(setSnackbarOpen(isOpen));
        }
    };
};

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(AppContainer));