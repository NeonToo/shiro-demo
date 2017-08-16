import React from 'react';
import {Route, Switch} from 'react-router-dom';
import AppContainer from './containers/AppContainer';
import SignUp from './views/SignUp';
import SignIn from './views/SignIn';
import UserList from './views/UserList';
import UserDetail from './views/UserDetail';
import RoleList from './views/RoleList';
import RoleDetail from './views/RoleDetail';

const routeObjects = [
    {
        path: '/signin',
        exact: true,
        component: SignIn
    },
    {
        path: '/signup',
        exact: true,
        component: SignUp
    },
    {
        path: '/users',
        exact: true,
        component: UserList
    },
    {
        path: '/users/:id',
        exact: true,
        component: UserDetail
    },
    {
        path: '/roles',
        exact: true,
        component: RoleList
    },
    {
        path: '/roles/:id',
        exact: true,
        component: RoleDetail
    },
    {
        path: '/role',
        exact: true,
        component: RoleDetail
    }
];

const App = () => (
    <AppContainer>
        {routeObjects.map((route, index) =>
            <Route key={index} exact={route.exact} path={route.path} component={route.component} />
        )}
    </AppContainer>
);

// const App = () => (
//     <AppContainer>
//         <Route path="/users" component={UserList}/>
//         <Route exact path="/roles" component={RoleList}/>
//         <Route path="/roles/:id" component={RoleDetail}/>
//     </AppContainer>
// );

const routes = (
    <Switch>
        {/*<Route path="/signin" component={SignIn}/>*/}
        <Route path="/" component={App}/>
    </Switch>
);

export default routes;