import {
    countBoardViews,
    queryBoardsByTitle,
    queryMember,
    queryMembers,
    requestJoinMember,
    requestLogin,
    setSnackBarInfo,
    requestSaveBoard,
    requesUpdateBoard,
} from "../api/api";


export default {

    async REQUEST_JOIN(context, member) {
        try {
            const response = await requestJoinMember(member);
            context.commit('OPEN_MODAL', setModalTexts(true));
            return response;
        } catch (e) {
            context.commit('OPEN_MODAL', {
                title: e.response.data.message,
                content: '다시 한번 더 시도해주세요.',
                option: '닫기',
            });
        }
    },

    async REQUEST_LOGIN(context, member) {
        try {
            const response = await requestLogin(member);
            localStorage.setItem('email', member.email);
            context.commit('LOGIN', response.data);
            return response;
        } catch (e) {
            context.commit('OPEN_MODAL', {
                    title: '로그인 실패',
                    content: '아이디 혹은 비밀번호를 확인해주세요.',
                    option: '닫기',
                }
            )
        }
    },
    // async UPLOAD_IMAGE(context, image) {
    //     try {
    //         const response = await uploadImage(image);
    //         return response.data;
    //     } catch (e) {
    //         console.log(e);
    //     }
    // },


    async COUNT_MOVE_TO_ARTICLE(context, articleInfo) {
        try {
            const response = await countBoardViews(articleInfo.id);
            context.commit('MOVE_TO_ARTICLE', articleInfo);
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {
                title: '게시글 조회에 실패하였습니다.',
                content: `다시 한번 더 시도해주세요.\n` + e,
                option: '재시도',
            })
        }
    },

    async QUERY_BOARDS_BYTITLE(context, queryInfo) {
        try {
            context.commit('PAGE_LOADING');
            const response = await queryBoardsByTitle(queryInfo);
            context.commit('SET_BOARD_PAGES', response.data);
            return response.data;
        } catch (e) {
            // context.commit('SET_SNACKBAR', setSnackBarInfo('너무 많은 검색은 서버에 무리를 줄 수 있습니다.!', 'error'))
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
                title: '사용자 등록 실패',
                content: `게시글 등록을 위해선 재 요청이 필요합니다.` + e,
                option: '재요청',
            })
        }
    },

    async QUERY_MEMBERS(context) {
        try {
            const response = await queryMembers();
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {
                title: '사용자 조회 실패.',
                content: `사용자 조회에 실패했습니다.` + e,
                option: '재시도',
            })
        }
    },


}

const setModalTexts = (isSuccess) => {
    if (isSuccess) {
        return {
            title: '회원가입 성공!',
            content: '로그인 페이지로 이동합니다.',
            option: '이동',
        }
    }
};
