import {router} from "../routes/route";
import {
    countBoardViews, requestBoardContentForModifyBoard, deleteBoardRequest
    , queryBoardsByTitle, queryBoard, requesUpdateBoard, requestSaveBoard
} from "../apis/board_api";

const state = {
    boardList: [],
    pageLoading: false,
    pageInfo: {},
    boardUrl: '',
    board: {id: '', title: '', author: '', content: '', email: '', createDate: '',},
    boardWrite: {title: '', content: '', boardType: '',},
    pageRequest: {id: '', page: 0, sort: 'createDate,DESC', size: 3,},
};

const getters = {
    getBoardId(state) {
        return state.board.id;
    }
};

const mutations = {
    SET_BOARD(state, boardInfo) {
        state.board.id = boardInfo.id;
        state.board.title = boardInfo.title;
        state.board.author = boardInfo.author;
        state.board.content = boardInfo.content;
        state.board.email = boardInfo.email;
        state.board.createDate = boardInfo.createDate;
    },
    ACEEPT_MODIFY_BOARD(state, payload) {
        state.boardWrite.title = payload.title;
        state.boardWrite.content = payload.content;
        state.boardWrite.boardType = payload.boardType;
        router.push(`/board-modify/${payload.id}`);
    },
    SET_MODIFY_BOARD(state, payload) {
        state.boardWrite.title = payload.title;
        state.boardWrite.content = payload.content;
        state.boardWrite.boardType = payload.boardType;
    },
    CLEAR_BOARD_WRITE(state) {
        state.boardWrite.title = '';
        state.boardWrite.content = '';
        state.boardWrite.boardType = '';
    },
    SUCCESS_MODIFY_AND_SAVE_BOARD(state, id) {
        router.push(`/board/${id}`);
    },
    SET_BOARD_ID(state, id) {
        state.pageRequest.id = id;
    },
    SET_BOARD_PAGES(state, boardPagesInfo) {
        state.pageLoading = false;

        if (boardPagesInfo.page.totalElements === 0) {
            state.boardList = [];
        } else {
            state.boardList = boardPagesInfo._embedded.boardListResponseDtoList;
        }

        boardPagesInfo.page.number += 1;
        state.pageInfo = boardPagesInfo.page;
    },
    MOVE_TO_ARTICLE(state, articleInfo) {
        state.boardUrl = articleInfo.href;
        router.push(`/board/${articleInfo.id}`, () => {
        });

    }
    ,

    PAGE_LOADING(state) {
        state.pageLoading = true;
    },
};

const actions = {
    async QUERY_BOARD(context, pageRequest) {
        try {
            const response = await queryBoard(pageRequest);
            context.commit('SET_BOARD', response.data);
            context.commit('SET_COMMENT_LIST', response.data.commentResponseDtoList);
            return response.data;
        } catch (e) {
            return Promise.reject(e);
        } finally {
            context.commit('END_SPINNER');
        }
    },
    async QUERY_MORE_COMMENT(context, pageRequest) {
        try {
            const response = await queryBoard(pageRequest);
            context.commit('ADD_COMMENT_LIST', response.data.commentResponseDtoList);
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: '게시글 조회 실패', content: '다시 한번 더 시도해주세요.', option1: '닫기',}
            )
        }
    },
    async MODIFY_VALIDATE_BOARD(context, boardId) {
        try {
            const response = await requestBoardContentForModifyBoard(boardId);
            context.commit('ACEEPT_MODIFY_BOARD', response.data);
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: '수정 권한이 없습니다.', content: `작성자만 수정이 가능합니다.` + e, option1: '닫기',});
        }
    },
    async DELETE_BOARD(context, boardId) {
        try {
            const response = await deleteBoardRequest(boardId);
            context.commit('CLOSE_MODAL');
            router.push('/boards');
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: '삭제 권한이 없습니다.', content: `작성자 혹은 관리자만 삭제가 가능합니다.` + e, option1: '닫기',})
        }
    },
    async SAVE_BOARD(context, board) {
        try {
            const response = await requestSaveBoard(board);
            context.commit('SUCCESS_MODIFY_AND_SAVE_BOARD', response.data.content);
            context.commit('SET_SNACKBAR', {text: '게시글 작성 완료!', color: 'info', location: 'top'});
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: '게시글 작성 실패', content: '다시 한번 더 시도해주세요.', option1: '닫기',})
        }
    },
    async MODIFY_BOARD(context, payload) {
        try {
            const response = await requesUpdateBoard(payload);
            context.commit('SUCCESS_MODIFY_AND_SAVE_BOARD', payload.id);
            context.commit('SET_SNACKBAR', {text: '게시글 수정 완료!', color: 'info', location: 'top'});
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: '게시글 수정 실패', content: '다시 한번 더 시도해주세요.', option1: '닫기',})
        }
    },
    async QUERY_MODIFY_BOARD(context, id) {
        try {
            const response = await requestBoardContentForModifyBoard(id);
            context.commit('SET_MODIFY_BOARD', response.data);
            return response.data;
        } catch (e) {
            console.log('QUERY_MODIFY_BOARD 에러');
        }
    },
    async COUNT_MOVE_TO_ARTICLE(context, articleInfo) {
        try {
            const response = await countBoardViews(articleInfo.id);
            context.commit('MOVE_TO_ARTICLE', articleInfo);
            return response.data;
        } catch (e) {
            context.commit('END_SPINNER');
            context.commit('OPEN_MODAL', {
                title: '게시글 조회에 실패하였습니다.',
                content: `다시 한번 더 시도해주세요.\n` + e,
                option1: '재시도',
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
};

export default {mutations, state, actions, getters};