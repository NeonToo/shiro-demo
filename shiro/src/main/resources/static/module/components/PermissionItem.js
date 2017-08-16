import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {TableRow, TableRowColumn} from 'material-ui/Table';
import Checkbox from 'material-ui/Checkbox';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';

const restrictions = ['NO_RESTRICTION', 'OWNER', 'DEPARTMENT'];

const checkboxStyle = {
    width: 'auto'
};

class PermissionItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            resource: Object.assign({}, this.props.resource)
        }
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            resource: nextProps.resource
        });
    }

    handleCheck(isChecked, resourceIndex) {
        this.props.onCheck(resourceIndex, isChecked);
        this.setState({
            resource: Object.assign({}, this.state.resource, {assigned: isChecked})
        });
    }

    handleReadRestrictionChange(readRestrictionIndex, resourceIndex) {
        this.props.onReadRestrictionChange(resourceIndex, readRestrictionIndex);
        this.setState({
            resource: Object.assign({}, this.state.resource, {read: readRestrictionIndex})
        });
    }

    handleWriteRestrictionChange(writeRestrictionIndex, resourceIndex) {
        this.props.onWriteRestrictionChange(resourceIndex, writeRestrictionIndex);
        this.setState({
            resource: Object.assign({}, this.state.resource, {write: writeRestrictionIndex})
        });
    }

    render() {
        const {children, ...others} = this.props;
        const {resource} = this.state;

        return (
            <TableRow>
                <TableRowColumn>
                    <Checkbox style={checkboxStyle} defaultChecked={resource.assigned}
                              onCheck={(event, isChecked) => this.handleCheck(isChecked, resource.index)}/>
                </TableRowColumn>
                <TableRowColumn>{resource.name}</TableRowColumn>
                <TableRowColumn>
                    <SelectField autoWidth={true} value={resource.read} disabled={!resource.assigned}
                                 onChange={(event, key, value) => this.handleReadRestrictionChange(value, resource.index)}
                                 style={{marginRight: '1em'}}>
                        {restrictions.map((restriction, readRestrictionIndex) =>
                            <MenuItem key={readRestrictionIndex}
                                      value={restriction} primaryText={restriction}/>
                        )}
                        {/*{restrictions.map((restriction, readRestrictionIndex) =>*/}
                        {/*<MenuItem key={readRestrictionIndex}*/}
                        {/*value={readRestrictionIndex}*/}
                        {/*label={restriction} primaryText={restriction}/>*/}
                        {/*)}*/}
                    </SelectField>
                </TableRowColumn>
                <TableRowColumn>
                    <SelectField autoWidth={true} value={resource.write} disabled={!resource.assigned}
                                 onChange={(event, key, value) => this.handleWriteRestrictionChange(value, resource.index)}>
                        {restrictions.map((restriction, writeRestrictionIndex) =>
                            <MenuItem key={writeRestrictionIndex} value={restriction} primaryText={restriction}/>
                        )}
                        {/*{restrictions.map((restriction, writeRestrictionIndex) =>*/}
                        {/*<MenuItem key={writeRestrictionIndex} value={writeRestrictionIndex}*/}
                        {/*label={restriction}*/}
                        {/*primaryText={restriction}/>*/}
                        {/*)}*/}
                    </SelectField>
                </TableRowColumn>
            </TableRow>
        );
    }
}

PermissionItem.defaultProps = {
    resource: {},
    onCheck: function () {
    },
    onReadRestrictionChange: function () {
    },
    onWriteRestrictionChange: function () {
    }
};

PermissionItem.propTypes = {
    resource: PropTypes.object,
    onCheck: PropTypes.func,
    onReadRestrictionChange: PropTypes.func,
    onWriteRestrictionChange: PropTypes.func
};

export default PermissionItem;