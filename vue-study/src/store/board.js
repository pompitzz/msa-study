import {
    queryBoard,
    requestContentForModifyBoard,
    deleteBoardRequest,
    requestSaveBoard,
    requesUpdateBoard
} from "../api/api";
import {router} from "../routes/route";

const state = {
    board: {
        id: '',
        title: '',
        author: '',
        content: '',
        email: '',
        lastModifiedDate: '',
    },
    boardWrite: {
        title: '',
        content: '',
        boardType: '',
    },
    isAuthor: false,
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
        state.board.lastModifiedDate = boardInfo.lastModifiedDate;

        state.isAuthor = boardInfo.email === localStorage.getItem('email');
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
};

const actions = {
    async QUERY_BOARD(context, pageRequest) {
        try {
            const response = await queryBoard(pageRequest);
            context.commit('SET_BOARD', response.data);
            context.commit('SET_COMMENT_LIST', response.data.commentResponseDtoList);
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: '게시글 조회 실패', content: '다시 한번 더 시도해주세요.', option: '닫기',});
            return Promise.reject(e);
        }
    },
    async QUERY_MORE_COMMENT(context, pageRequest) {
        try {
            const response = await queryBoard(pageRequest);
            context.commit('ADD_COMMENT_LIST', response.data.commentResponseDtoList);
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: '게시글 조회 실패', content: '다시 한번 더 시도해주세요.', option: '닫기',}
            )
        }
    },
    async MODIFY_VALIDATE_BOARD(context, boardId) {
        try {
            const response = await requestContentForModifyBoard(boardId);
            console.log(response.data);
            context.commit('ACEEPT_MODIFY_BOARD', response.data);
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: '수정 권한이 없습니다.', content: `작성자만 수정이 가능합니다.` + e, option: '닫기',});
        }
    },
    async DELETE_BOARD(context, boardId) {
        try {
            const response = await deleteBoardRequest(boardId);
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: '삭제 권한이 없습니다.', content: `작성자 혹은 관리자만 삭제가 가능합니다.` + e, option: '닫기',})
        }
    },
    async SAVE_BOARD(context, board) {
        try {
            const response = await requestSaveBoard(board);
            context.commit('SUCCESS_MODIFY_AND_SAVE_BOARD', response.data.content);
            context.commit('SET_SNACKBAR', {text: '게시글 작성 완료!', color: 'info', location: 'top'});
            return response.data;
        } catch (e) {
            console.log(e)
            context.commit('OPEN_MODAL', {title: '게시글 작성 실패', content: '다시 한번 더 시도해주세요.', option: '닫기',})
        }
    },
    async MODIFY_BOARD(context, payload) {
        try {
            const response = await requesUpdateBoard(payload);
            context.commit('SUCCESS_MODIFY_AND_SAVE_BOARD', payload.id);
            context.commit('SET_SNACKBAR', {text: '게시글 수정 완료!', color: 'info', location: 'top'});
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: '게시글 수정 실패', content: '다시 한번 더 시도해주세요.', option: '닫기',})
        }
    },
    async QUERY_MODIFY_BOARD(context, id) {
        try {
            const response = await requestContentForModifyBoard(id);
            context.commit('SET_MODIFY_BOARD', response.data);
            return response.data;
        } catch (e) {
            console.log('QUERY_MODIFY_BOARD 에러');
        }
    },
};

export default {mutations, state, actions, getters};