import React from 'react';
import {render} from 'react-dom';
import {HashRouter as Router} from 'react-router-dom';
import injectTapEventPlugin from 'react-tap-event-plugin';
import { createStore } from 'redux';
import { Provider } from 'react-redux';
import reducers from './reducers';
import {setSnackbarOpen} from './actions';
import routes from './routes';
import 'material-ui';
import './css/style.css';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';

const store = createStore(reducers);
injectTapEventPlugin();

// handle 401 response
axios.interceptors.response.use(function (response) {
    // const status = response.status;
    // const contentType = response.headers["content-type"];
    //
    // if(status === 200 && contentType.includes("text/html")) {
    //     window.location.href = response.request.responseURL;
    // }

    return response;
}, function (error) {
    const status = error.response.status;

    if (status === 401) {
        store.dispatch(setSnackbarOpen(true));
    }

    return Promise.reject(error);
});

render(
    <MuiThemeProvider>
        <Provider store={store}>
            <Router children={routes} />
        </Provider>
    </MuiThemeProvider>,
    document.getElementById('root')
);
