import {
    requestJoinMember, requestLogin,
    uploadImage, requestSaveBoard,
    queryArticl, validateBoardMember
    , countBoardViews, queryBoardsByTitle
} from "../api/api";
import {router} from "../routes/route";


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
            context.commit('SUCCESS_SAVE_BOARD', response.data);
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {
                    title: '게시글 작성 실패',
                    content: '다시 한번 더 시도해주세요.',
                    option: '닫기',
                }
            )
        }
    },

    async QUERY_ARTICLE(context, id) {
        try {
            const response = await queryArticle(id);
            console.log(response.data);
            return response.data;
        } catch (e) {
            console.log(e);
        }
    },


    async VALIDATE_MODIFY_MEMBER(context, emailAndBoardId) {
        try {
            const response = await validateBoardMember(emailAndBoardId);
            router.push(`/article-modify/${emailAndBoardId.id}`);
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {
                    title: '수정 권한이 없습니다.',
                    content: `작성자만 수정이 가능합니다.\n` + e,
                    option: '닫기',
                }
            )
        }
    },

    async VALIDATE_DELETE_MEMBER(context, emailAndBoardId) {
        try {
            const response = await validateBoardMember(emailAndBoardId);
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {
                    title: '삭제 권한이 없습니다.',
                    content: `작성자 혹은 관리자만 수정이 가능합니다.\n` + e,
                    option: '닫기',
                }
            )
        }
    },


    async COUNT_MOVE_TO_ARTICLE(context, articleInfo) {
        try {
            const response = await countBoardViews(articleInfo.id);
            context.commit('MOVE_TO_ARTICLE', articleInfo);
            return response.data;
        } catch (e) {
            console.log(e);
        }
    },

    async QUERY_BOARDS_BYTITLE(context, queryInfo) {
        try {
            context.commit('PAGE_LOADING');
            const response = await queryBoardsByTitle(queryInfo);
            console.log(response.data);
            context.commit('SET_BOARD_PAGES', response.data);
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
            option: '닫기',
        }
    }
};