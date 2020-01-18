import Vue from 'vue'
import VueRouter from 'vue-router'
import Register from "../views/Register";
import Login from "../views/Login";
import NoAuth from "../views/NoAuth";
import Main from "../views/Main";
import Memo from "../views/Memo";
import BoardWrite from "../views/BoardWrite";
import Boards from "../views/Boards";
import Board from "../views/Board";
import BoardModify from "../views/BoardModify";
import Admin from "../views/Admin";
import {validateAdmin} from "../apis/admin_api";
import store from "../store/store";
import Calander from "../views/Calendar";
import Temp from "../views/Temp";

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
            component: Calander
        },
        {
            path: '/blog/temp',
            name: 'main',
            component: Temp
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
            beforeEnter: (to, from, next) => isAuthenticationMember(to, from, next),
        },
        {
            path: '/no-auth',
            name: 'no-auth',
            component: NoAuth
        },
        {
            path: '/board-write',
            name: 'boardWrite',
            component: BoardWrite,
            beforeEnter: (to, from, next) => isAuthenticationMember(to, from, next),

        },
        {
            path: '/boards',
            name: 'boards',
            component: Boards,
        },
        {
            path: '/board/:id',
            name: 'board',
            component: Board,
        },
        {
            path: '/board-modify/:id',
            name: 'boardModify',
            component: BoardModify,
        },
        {
            path: '/admin',
            component: Admin,
            beforeEnter: (to, form, next) => isAdmin(to, form, next),
        }


    ]
});

export {router}

