import React from 'react';
import {withRouter} from 'react-router-dom';
import {connect} from 'react-redux';
import {setSignedInInfo} from './../actions';
import Paper from 'material-ui/Paper';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';

const paperStyle = {
    margin: '10% auto 0 auto',
    width: '50%',
    padding: '20px 0',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
};

const formStyle = {
    width: '60%',
    textAlign: 'center'
};

const textFieldStyle = {
    display: 'block',
    width: '100%'
};

const buttonAreaStyle = {
    marginTop: '1em'
};

class SignIn extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            actionUrl: `api/signin`
        };
        this.handleFormSubmit = this.handleFormSubmit.bind(this);
    }

    componentWillMount() {
        const {location} = this.props;

        if (location.state && location.state.prevLocation) {
            this.state.actionUrl += `?route=${location.state.prevLocation}`;
        }
    }

    handleFormSubmit() {
        const {location, history, changeSignedInInfo} = this.props;
        const signInForm = document.forms['signInForm'];

        axios.post(this.state.actionUrl, {
            username: signInForm.username.value,
            password: signInForm.password.value
        })
            .then(function (oRes) {
                const data = oRes.data;

                if (data) {
                    changeSignedInInfo({
                        signedIn: true,
                        signedInUser: data
                    });
                    history.replace(location.state.prevLocation);
                }
            })
            .catch(function (error) {

            });

        // axios.post(this.state.actionUrl, {
        //     username: signInForm.username.value,
        //     password: signInForm.password.value
        // }, {
        //     headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        // })
        //     .then(function(oRes) {
        //         console.log(oRes.data);
        //         history.replace(location.state.prevLocation);
        //     })
        //     .catch(function(error) {
        //
        //     });
    }

    render() {
        const {actionUrl} = this.state;

        return (
            <Paper style={paperStyle} zDepth={5}>
                <form style={formStyle} action={actionUrl} method="post" name="signInForm">
                    <TextField style={textFieldStyle} floatingLabelText="Username"
                               hintText="please enter username" name="username"/>
                    <TextField style={textFieldStyle} type="password" floatingLabelText="Password"
                               hintText="please enter password" name="password"/>
                    {/*<RaisedButton style={buttonAreaStyle} type="submit" label="Submit" primary={true} fullWidth={true}/>*/}
                    <RaisedButton style={buttonAreaStyle} type="button" label="Submit" primary={true} fullWidth={true}
                                  onTouchTap={this.handleFormSubmit}/>
                </form>
            </Paper>
        );
    }
}

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

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(SignIn));