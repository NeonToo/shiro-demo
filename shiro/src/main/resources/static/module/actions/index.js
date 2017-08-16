export const ActionTypes = {
    IS_SIGNEDIN: 'IS_SIGNEDIN',
    IS_SNACKBAR_OPEN: 'IS_SNACKBAR_OPEN',
    SIGNEDIN_USER: 'SIGNEDIN_USER',
    SIGNEDIN_INFO: 'SIGNEDIN_INFO'
};

export function setSignedIn(isSignedIn) {
    return {
        type: ActionTypes.IS_SIGNEDIN,
        isSignedIn
    };
}

export function setSignedInUser(signedInUser) {
    return {
        type: ActionTypes.SIGNEDIN_USER,
        signedInUser
    };
}

export function setSignedInInfo(signedInInfo) {
    return {
        type: ActionTypes.SIGNEDIN_INFO,
        signedInInfo
    };
}

export function setSnackbarOpen(isOpen) {
    return {
        type: ActionTypes.IS_SNACKBAR_OPEN,
        isOpen
    };
}