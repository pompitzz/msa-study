import {requestJoinMember, requestLogin} from "../apis/api";
import {createInfo} from "../common";

export default {
    async REQUEST_JOIN(context, member) {
        try {
            const response = await requestJoinMember(member);
            context.commit('SET_MODAL_TEXTS', createInfo(
                '회원가입 성공!',
                '로그인 페이지로 이동합니다.',
                '이동'
            ));
            return response;
        } catch (e) {
            context.commit('SET_MODAL_TEXTS', createInfo(
                '회원가입 실패!',
                '다시한번 더 시도해주세요',
                'Close'
            ));
        }
    },

    async REQUEST_LOGIN(context, info) {
        try {
            const response = await requestLogin(info);
            context.commit('LOGIN', response.data);
            return response;
        } catch (e) {
            context.commit('SET_MODAL_TEXTS', createInfo(
                '로그인 실패!',
                '다시한번 더 시도해주세요',
                'CLOSE'
            ));
        }
    }
}