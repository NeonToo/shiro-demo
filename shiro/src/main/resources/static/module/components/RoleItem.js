import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';
import {Card, CardHeader, CardText} from 'material-ui/Card';
import Checkbox from 'material-ui/Checkbox';

const checkboxStyle = {
    width: 'auto'
};

class RoleItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            expanded: false
        };
        this.handleExpandChange = this.handleExpandChange.bind(this);
    }

    handleExpandChange(isExpanded) {
        this.setState({
            expanded: isExpanded
        });
    }

    handleCheck(isChecked, roleId) {
        this.props.onCheck(roleId, isChecked);
    }

    render() {
        const {role, children, ...others} = this.props;
        const {expanded} = this.state;

        return (
            <Card expandable={true} expanded={expanded} onExpandChange={(isExpanded) => this.handleExpandChange(isExpanded)}>
                <CardHeader title={<Checkbox style={checkboxStyle} defaultChecked={role.assigned} label={role.name}
                                              onCheck={(event, isChecked) => this.handleCheck(isChecked, role.id)}/>}
                            actAsExpander={false} showExpandableButton={true}/>
                <CardText expandable={true}>
                    <Table fixedHeader={true}>
                        <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                            <TableRow>
                                <TableHeaderColumn>Resource</TableHeaderColumn>
                                <TableHeaderColumn>Read Restriction</TableHeaderColumn>
                                <TableHeaderColumn>Write Restriction</TableHeaderColumn>
                            </TableRow>
                        </TableHeader>
                        <TableBody showRowHover={true} displayRowCheckbox={false}>
                            {role.permissions && role.permissions.map((permission) =>
                                <TableRow key={permission.id}>
                                    <TableRowColumn>{permission.resource}</TableRowColumn>
                                    <TableRowColumn>{permission.read}</TableRowColumn>
                                    <TableRowColumn>{permission.write}</TableRowColumn>
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </CardText>
            </Card>
        );
    }
}

RoleItem.defaultProps = {
    role: {},
    onCheck: function () {
    }
};

RoleItem.propTypes = {
    role: PropTypes.object,
    onCheck: PropTypes.func
};

export default RoleItem;