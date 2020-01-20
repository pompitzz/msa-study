import Vue from 'vue'
import VueRouter from 'vue-router'
import {validateAdmin} from "../apis/admin_api";
import store from "../store/store";

const isAuthenticationMember = (to, from, next) => {
    if (localStorage.getItem('access_token') !== null) {
        next()
    } else {
        next('/no-auth');
    }
};

const isAdmin = (to, from, next) => {
    validateAdmin().then(() => next())
        .catch(() => store.commit('OPEN_MODAL', {
            title: '접속 권한 없음.',
            content: '관리자전용 페이지 입니다',
            option1: '닫기',
        }))
};

Vue.use(VueRouter);

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            redirect: '/boards',
        },
        {
            path: '/main',
            name: 'main',
            component: () => import('../views/Calendar')
        },
        {
            path: '/blog/temp',
            component: () => import('../views/Temp')
        },
        {
            path: '/register',
            name: 'register',
            component: () => import('../views/Register')
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('../views/Login')
        },
        {
            path: '/memo',
            name: 'memo',
            component: () => import('../views/Memo'),
            beforeEnter: (to, from, next) => isAuthenticationMember(to, from, next),
        },
        {
            path: '/no-auth',
            name: 'no-auth',
            component: () => import('../views/NoAuth')
        },
        {
            path: '/board-write',
            name: 'boardWrite',
            component: () => import('../views/BoardWrite'),
            beforeEnter: (to, from, next) => isAuthenticationMember(to, from, next),

        },
        {
            path: '/boards',
            name: 'boards',
            component: () => import('../views/Boards')
        },
        {
            path: '/board/:id',
            name: 'board',
            component: () => import('../views/Board'),
        },
        {
            path: '/board-modify/:id',
            name: 'boardModify',
            component: () => import('../views/BoardModify'),
        },
        {
            path: '/admin',
            component: () => import('../views/Admin'),
            beforeEnter: (to, form, next) => isAdmin(to, form, next),
        },
        {
            path: '/error',
            name: 'error`',
            component: () => import('../views/Error'),
        },
        {
            path: '*',
            name: 'NoPage',
            component: () => import('../views/NoPage')
        }


    ]
});

export {router}

