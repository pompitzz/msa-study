import {requestJoinMember, requestLogin, uploadImage, requestSaveBoard, queryArticle, queryBoards} from "../api/api";


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
            localStorage.setItem('email', member.email);
            return response;
        } catch (e) {
            context.commit('OPEN_MODAL', {
                    title: '로그인 실패',
                    content: '다시 한번 더 시도해주세요.',
                    option: '닫기',
                    route: '',
                }
            )
        }
    },

    async UPLOAD_IMAGE(context, image) {
        try {
            const response = await uploadImage(image);
            return response.data;
        } catch (e) {
            console.log(e);
        }
    },

    async SAVE_BOARD(context, board) {
        try {
            const response = await requestSaveBoard(board);
            console.log('response', response.data);
            context.commit('SUCCESS_SAVE_BOARD', response.data);
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {
                    title: '게시글 작성 실패',
                    content: '다시 한번 더 시도해주세요.',
                    option: '닫기',
                    route: '',
                }
            )
        }
    },

    async QUERY_ARTICLE(context, articleUrl) {
        try {
            const response = await queryArticle(articleUrl);
            console.log(response.data);
            return response.data;
        } catch (e) {
            console.log(e);
        }
    },

    async QUERY_BOARDS(context, pageRequest) {
        try {
            const response = await queryBoards(pageRequest);
            console.log(response.data);
            context.commit('SET_BOARD_PAGES', response.data);
            return response.data;
        }catch (e) {
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
            rotue: '/login'
        }
    } else {
        return {
            title: '회원가입 실패',
            content: '다시 한번 더 시도해주세요.',
            option: '닫기',
            route: '',
        }
    }
};