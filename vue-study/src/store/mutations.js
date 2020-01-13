import {router} from "../routes/route";
import {deleteAccessTokenInHeader} from "../api/api";
import {setTokenInLocalStorage, deleteTokenInLocalStorage} from "../utils/oauth";

export default {

    LOGIN(state, responseTokenInfo) {
        setTokenInLocalStorage(responseTokenInfo);
        state.accessToken = localStorage.getItem('access_token');
        state.loadingState = false;
        router.push('/');
    },
    LOGOUT(state) {
        deleteTokenInLocalStorage();
        deleteAccessTokenInHeader();
        state.accessToken = null;
        localStorage.removeItem("email");
        router.push('/');
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
    MOVE_TO_ARTICLE(state, articleInfo){
        state.articleUrl = articleInfo.href;
        router.push(`/board/${articleInfo.id}`);
    }
    ,
    CLOSE_MODAL(state) {
        state.modal.open = false;
    },
    OPEN_MODAL(state, modalTexts) {
        state.loadingState = false;
        state.modal.title = modalTexts.title;
        state.modal.content = modalTexts.content;
        state.modal.option = modalTexts.option;
        state.modal.open = true;
    },
    START_LOADING(state) {
        state.loadingState = true;
    },
    PAGE_LOADING(state) {
        state.pageLoading = true;
    },
    SET_SNACKBAR(state, snackbarInfo) {
        state.snackbar.open = true;
        state.snackbar.text = snackbarInfo.text;
        state.snackbar.color = snackbarInfo.color;
        state.snackbar.location = snackbarInfo.location;
    }
}