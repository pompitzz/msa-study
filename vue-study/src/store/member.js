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
    LOGIN(state, responseTokenInfo) {
        setTokenInLocalStorage(responseTokenInfo);
        state.accessToken = localStorage.getItem('access_token');
        router.push('/');
    },
    LOGOUT(state) {
        deleteTokenInLocalStorage();
        deleteAccessTokenInHeader();
        state.accessToken = null;
        localStorage.removeItem("email");
        router.push('/');
    },
};

const actions = {
    async REQUEST_JOIN(context, member) {
        try {
            context.commit('START_LOADING');
            const response = await requestJoinMember(member);
            context.commit('OPEN_MODAL', {title: '회원가입 성공!', content: '로그인 페이지로 이동합니다.', option: '이동',});
            return response;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: e.response.data.message, content: '다시 한번 더 시도해주세요.', option: '닫기',});
        }
    },
    async REQUEST_LOGIN(context, member) {
        try {
            context.commit('START_LOADING');
            const response = await requestLogin(member);
            localStorage.setItem('email', member.email);
            context.commit('LOGIN', response.data);
            context.commit('END_LOADING', response.data);
            return response;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: '로그인 실패', content: '아이디 혹은 비밀번호를 확인해주세요.', option: '닫기',}
            )
        }
    },
    async QUERY_MEMBER(context, email) {
        try {
            const response = await queryMember(email);
            let text = '';
            if (response.data.role === 'USER') {
                text = `안녕하세요 ${response.data.name} 님!`;
            } else if (response.data.role === 'ADMIN') {
                text = `안녕하세요 마스터 님!`;
            }
            localStorage.setItem('name', response.data.name);
            context.commit('SET_SNACKBAR', setSnackBarInfo(text, 'info', 'top'));
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {
                title: '사용자 등록 실패', content: `게시글 등록을 위해선 재 요청이 필요합니다.` + e, option: '재요청',})
        }
    },
};

export default {mutations, state, actions, getters};