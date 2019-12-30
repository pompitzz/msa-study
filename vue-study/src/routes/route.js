import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from "../views/LoginView";
import ResisterView from "../views/Register";
import MainView from "../views/MainView";
import {store} from "../store/sotre";
import NoAuth from "../components/common/NoAuth";

Vue.use(VueRouter);
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
    }
;
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
            component: MainView
        },
        {
            path: '/login',
            name: 'login',
            component: LoginView
        },
        {
            path: '/register',
            name: 'register',
            component: ResisterView
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