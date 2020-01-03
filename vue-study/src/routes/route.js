
import Vue from 'vue'
import VueRouter from 'vue-router'
import Register from "../views/Register";
import Login from "../views/Login";
import NoAuth from "../views/NoAuth";
import Main from "../views/Main";
import Memo from "../views/Memo";
import store from "../store/store";


const isAuthenticationMember = (to, from, next) => {
    if (store.getters.isAuthenticated) {
        next()
    } else {
        next('/no-auth');
    }
};

Vue.use(VueRouter);

const router =  new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'main',
            component: Main
        },
        {
            path: '/register',
            name: 'register',
            component: Register
        },
        {
            path: '/login',
            name: 'login',
            component: Login
        },
        {
            path: '/memo',
            name: 'memo',
            component: Memo,
            beforeEnter: (to, from, next) =>
                isAuthenticationMember(to, from, next),
        },
        {
            path: '/no-auth',
            name: 'no-auth',
            component: NoAuth
        }
    ]
});

export {router}

