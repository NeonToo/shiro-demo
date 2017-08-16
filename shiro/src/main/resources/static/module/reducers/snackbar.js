import {ActionTypes} from "../actions";

const initialSnackbar = {
    open: false
};

function snackbar(state = initialSnackbar, action) {
    switch(action.type) {
        case ActionTypes.IS_SNACKBAR_OPEN:
            return {
                ...state,
                open: action.isOpen
            };
        default:
            return state;
    }
}

export default snackbar;