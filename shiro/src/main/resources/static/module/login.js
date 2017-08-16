import React from 'react';
import {render} from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import injectTapEventPlugin from 'react-tap-event-plugin';
import Login from './views/SignIn';

injectTapEventPlugin();

render(
    <MuiThemeProvider>
        <div>Render</div>
    </MuiThemeProvider>,
    document.getElementById('root')
);