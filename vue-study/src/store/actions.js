import {requestJoinMember, requestLogin, uploadImage} from "../api/api";


export default {

    async REQUEST_JOIN(context, member) {
        try {
            const response = await requestJoinMember(member);
            context.commit('OPEN_MODAL', setModalTexts(true));
            return response;
        } catch (e) {
            context.commit('OPEN_MODAL', setModalTexts(false));
        }
    },

    async REQUEST_LOGIN(context, member) {
        try {
            const response = await requestLogin(member);
            context.commit('LOGIN', response.data);
            return response;
        } catch (e) {
            context.commit('OPEN_MODAL', {
                    title: '로그인 실패',
                    content: '다시 한번 더 시도해주세요.',
                    option: '닫기'
                }
            )
        }
    },

    async UPLOAD_IMAGE(context, image) {
        try {
            const response = await uploadImage(image);
            console.log(response);
            return response.data;
        } catch (e) {
            console.log(e);
        }
    }

}

const setModalTexts = (isSuccess) => {
    if (isSuccess) {
        return {
            title: '회원가입 성공!',
            content: '로그인 페이지로 이동합니다.',
            option: '이동',
        }
    } else {
        return {
            title: '회원가입 실패',
            content: '다시 한번 더 시도해주세요.',
            option: '닫기'
        }
    }
};