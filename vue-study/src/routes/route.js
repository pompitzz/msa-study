import {store} from "../store/store";
import NoAuth from "../components/common/NoAuth";

import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../components/Login";
import Register from "../components/Register";
import Main from "../components/Main";

const isAuthenticationMember = (to, from, next) => {
    if (store.getters.isAuthenticated) {
        next()
    } else {
        const modalTexts = {
            title: "접근권환 없음",
            description: "현재 페이지에 접속하기 위해서는 로그인을 해주세요",
            option: "로그인 페이지로 이동"
        };
        store.commit('SET_MODAL_TEXTS', modalTexts);
        next(`/no-auth?returnPath=${encodeURIComponent(from.path)}`);
    }
};

Vue.use(VueRouter);

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            redirect: '/main',
        },
        {
            path: '/main',
            name: 'main',
            component: Main
        },
        {
            path: '/login',
            name: 'login',
            component: Login
        },
        {
            path: '/register',
            name: 'register',
            component: Register
        },
        {
            path: '/board',
            name: 'board',
            beforeEnter: (to, from, next) => isAuthenticationMember(to, from, next),
        },
        {
            path: '/no-auth',
            name: 'no-auth',
            component: NoAuth
        }

    ]
});
router.afterEach((to) => {
        if (to.name !== 'no-auth')
            store.commit('CLOSE_MODAL');
    }
);


export {router}
