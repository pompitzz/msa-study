import {requestDeleteComment, saveComment, modifyComment, querySamParentComments} from "../api/api";

const state = {
    comment: {
        id: '',
        parentId: '',
        parentName: '',
        boardId: '',
        content: '',
        index: '',
    },
    commentPageReqeust: {
        page: 1,
        size: 2,
        sort: 'createDate,DESC',
    },
    dialog: false,
    isLast: false,
    commentList: [],
};

const mutations = {
    OPEN_COMMENT_MODAL(state, comment) {
        state.comment = comment;
        state.dialog = true;
    },
    CLOSE_COMMENT_MODAL(state) {
        state.dialog = false;
    },
    SET_COMMENT_LIST(state, commentList) {
        console.log('commentList', commentList);
        state.commentList = commentList.content.reverse();
        // printarray(state.commentList);
        // reverseArrayChildren(state.commentList);
        // printarray(state.commentList);
        state.isLast = commentList.last;
    },
    ADD_COMMENT_LIST(state, commentList) {
        let addCommentList = commentList.content.reverse();
        state.commentList = addCommentList.concat(state.commentList);
        state.isLast = commentList.last;
    },
    SUCESS_SAVE_COMMENT(state, savedComment) {
        state.commentList = state.commentList.concat(savedComment);
        state.dialog = false;
    },
    DELETE_ONE_COMMENTLIST(state, index) {
        state.commentList.splice(index, 1);
    },
    SUCESS_MODIFY_COMMENT(state, payload) {
        state.commentList[payload.index].content = payload.comment.content;
        state.dialog = false;
    }
};

function reverseArrayChildren(commentList) {
    for (const list of commentList) {
        if (list.childrenResponseDto !== null) {
            list.childrenResponseDto.reverse();
        }
    }
}

function printarray(commentList) {
    console.log('commentList iter');
    for (const list of commentList) {
        if (list.childrenResponseDto !== null) {
            for (const child of list.childrenResponseDto) {
                console.log('commentList iter2');
                console.log(child.commentId);
            }
        }
    }
}

const actions = {
    async SAVE_COMMENT(context, comment) {
        try {
            const response = await saveComment(comment);
            context.commit('SUCESS_SAVE_COMMENT', response.data);
            context.commit('SET_SNACKBAR', {text: '댓글 작성 성공!', color: 'info', location: 'bottom'});
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {
                title: '댓글 작성 실패.',
                content: `댓글 작성에 실패했습니다.` + e,
                option: '닫기',
            });
            return Promise.reject(e);
        }
    },
    async MODIFY_COMMENT(context, comment) {
        try {
            const response = await modifyComment(comment);
            console.log(response.data);
            context.commit('SUCESS_MODIFY_COMMENT', {
                index: comment.index,
                comment: response.data
            });
            context.commit('SET_SNACKBAR', {text: '댓글 수정 성공!', color: 'info', location: 'bottom'});
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {
                title: '댓글 작성 실패.',
                content: `댓글 작성에 실패했습니다.` + e,
                option: '닫기',
            });
            return Promise.reject(e);
        }
    },

    async DELETE_COMMENT(context, payload) {
        try {
            const response = await requestDeleteComment(payload.commentId);
            context.commit('DELETE_ONE_COMMENTLIST', payload.index);
            context.commit('SET_SNACKBAR', {text: '댓글 삭제 성공!', color: 'info', location: 'bottom'});
            return response.data;
        } catch (e) {
            alert(e, e.response);
            return Promise.reject(e);
        }
    },
    async QUERY_SAME_PARENT_COMMENTS(context, payload) {
        try {
            const response = await querySamParentComments(payload);
            console.log('query same res', response.data);
            return response.data;
        } catch (e) {
            alert(e, e.response);
            return Promise.reject(e);
        }
    },
};
export default {mutations, state, actions};