import {ActionTypes} from "../actions";

const initialSignedIn = {
    signedIn: false,
    signedInUser: {}
};

function signin(state = initialSignedIn, action) {
    switch(action.type) {
        case ActionTypes.IS_SIGNEDIN:
            return {
                ...state,
                signedIn: action.isSignedIn
            };
        case ActionTypes.SIGNEDIN_USER:
            return {
                ...state,
                signedInUser: action.signedInUser
            };
        case ActionTypes.SIGNEDIN_INFO:
            return Object.assign({}, state, action.signedInInfo);
        default:
            return state;
    }
}

export default signin;