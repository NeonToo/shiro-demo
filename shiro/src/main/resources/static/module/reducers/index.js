import { combineReducers } from 'redux';
import signin from './signin';
import snackbar from './snackbar';

const reducers = combineReducers({
    signin,
    snackbar
});

export default reducers;