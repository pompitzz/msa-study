import {queryMember, requestJoinMember, requestLogin} from "../apis/member_api";
import {deleteTokenInLocalStorage, setTokenInLocalStorage} from "../utils/oauth";
import {deleteAccessTokenInHeader, setSnackBarInfo} from "../apis/common_api";
import {router} from "../routes/route";

const state = {
    accessToken: localStorage.getItem('access_token'),
};

const getters = {
    isAuthenticated(state) {
        return !!state.accessToken;
    },
};

const mutations = {
    LOGIN(state) {
        state.accessToken = localStorage.getItem('access_token');
        router.push('/');
    },
    LOGOUT(state) {
        deleteTokenInLocalStorage();
        deleteAccessTokenInHeader();
        state.accessToken = null;
        if (router.currentRoute.name !== 'boards') {
            router.push('/');
        }
    },
};

const actions = {
    async REQUEST_JOIN(context, member) {
        try {
            context.commit('START_LOADING');
            const response = await requestJoinMember(member);
            context.commit('SET_SNACKBAR', setSnackBarInfo('회원가입이 완료되었습니다!', 'info', 'top'));
            context.commit('END_LOADING');
            router.push('/login');
            return response;
        } catch (e) {
            context.commit('END_LOADING');
            context.commit('OPEN_MODAL', {title: '회원가입에 실패', content: e.response.data.message, option1: '닫기',});
        }
    },
    async REQUEST_LOGIN(context, member) {
        try {
            context.commit('START_LOADING');
            const response = await requestLogin(member);
            setTokenInLocalStorage(response.data);

            const getMember = await queryMember();
            context.commit('LOGIN', response.data);
            greetingMember(context, getMember.data);
            context.commit('END_LOADING', response.data);

            return response;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: '로그인 실패', content: '아이디 혹은 비밀번호를 확인해주세요.', option1: '닫기',}
            )
        }
    },
    // async QUERY_MEMBER(context) {
    //     try {
    //         let text = '';
    //         if (response.data.role === 'USER') {
    //             text = `안녕하세요 ${response.data.name} 님!`;
    //         } else if (response.data.role === 'ADMIN') {
    //             text = `안녕하세요 마스터 님!`;
    //         }
    //         context.commit('SET_SNACKBAR', setSnackBarInfo(text, 'info', 'top'));
    //         return response.data;
    //     } catch (e) {
    //         // context.commit('OPEN_MODAL', {
    //         //     title: '사용자 등록 실패', content: `게시글 등록을 위해선 재 요청이 필요합니다.` + e, option1: '재요청',})
    //     }
    // },
};

const greetingMember = (context, data) => {
    let text = '';
    if (data.role === 'USER') {
        text = `안녕하세요 ${data.name} 님!`;
    } else if (data.role === 'ADMIN') {
        text = `안녕하세요 마스터 님!`;
    }
    context.commit('SET_SNACKBAR', setSnackBarInfo(text, 'info', 'top'));
};

export default {mutations, state, actions, getters};