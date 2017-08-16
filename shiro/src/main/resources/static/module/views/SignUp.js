import React from 'react';
import Paper from 'material-ui/Paper';
import TextField from 'material-ui/TextField';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';
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

const hiddenFieldStyle = {
    display: 'none'
};

const buttonAreaStyle = {
    marginTop: '1em'
};

class SignUp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            departments: [],
            department: 0
        };
    }

    componentWillMount() {
        const that = this;

        axios.get('/api/departments')
            .then(function (oRes) {
                const data = oRes.data;

                if (data && _.isArray(data)) {
                    that.setState({
                        departments: data
                    });
                }
            })
            .catch(function (error) {
                console.error(`Error occur when get departments - ${error}`);
            });
    }

    handleDepartmentChange(value) {
        this.setState({
            department: value
        });
    }

    render() {
        const {departments, department} = this.state;

        return (
            <Paper style={paperStyle} zDepth={5}>
                <form style={formStyle} action="/api/users" method="post">
                    <TextField style={textFieldStyle} floatingLabelText="Username"
                               hintText="please enter username" name="username"/>
                    <TextField style={textFieldStyle} type="password" floatingLabelText="Password"
                               hintText="please enter password" name="password"/>
                    <TextField style={hiddenFieldStyle} type="hidden" name="department" value={department}/>
                    <SelectField style={textFieldStyle} value={department} hintText="Department"
                                 floatingLabelText="Department"
                                 onChange={(event, index, value) => this.handleDepartmentChange(value)}>
                        {departments.map((department) =>
                            <MenuItem key={department.id} value={department.id} primaryText={department.name}/>
                        )}
                    </SelectField>
                    <RaisedButton style={buttonAreaStyle} type="submit" label="Create an Account" primary={true}
                                  fullWidth={true}/>
                </form>
            </Paper>
        );
    }
}

export default SignUp;