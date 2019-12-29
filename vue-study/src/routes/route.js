import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from "../views/LoginView";
import ResisterView from "../views/ResisterView";

Vue.use(VueRouter);

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            redirect: '/login',
        },
        {
            path: '/login',
            component: LoginView
        },
        {
            path: '/resister',
            component: ResisterView
        },

    ]
});

export {router}