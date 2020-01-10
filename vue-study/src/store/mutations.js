import {router} from "../routes/route";
import {setAccessTokenInHeader, deleteAccessTokenInHeader} from "../api/api";
import {setTokenInLocalStorage, deleteTokenInLocalStorage} from "../utils/oauth";

export default {

    LOGIN(state, responseTokenInfo) {
        setTokenInLocalStorage(responseTokenInfo);
        setAccessTokenInHeader(responseTokenInfo.access_token);
        state.accessToken = localStorage.getItem("access_token");
        state.loadingState = false;
        router.push('/');
    },
    LOGOUT(state) {
        deleteTokenInLocalStorage();
        deleteAccessTokenInHeader();
        localStorage.removeItem("email");
        state.accessToken = null;
        router.push('/');
    },
    SUCCESS_SAVE_BOARD(state, boardInfo) {
        state.articleUrl = boardInfo._links.self.href;
        router.push(`/article/${boardInfo.content}`);
    },
    SET_BOARD_PAGES(state, boardPagesInfo) {
        state.loadingState = false;
        state.boardList = boardPagesInfo._embedded.boardListResponseDtoList;
        boardPagesInfo.page.number += 1;
        state.pageInfo = boardPagesInfo.page;
    },
    MOVE_TO_ARTICLE(state, articleInfo){
        state.articleUrl = articleInfo.href;
        router.push(`/article/${articleInfo.id}`);
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
    }
}