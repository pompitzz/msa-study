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
        state.accessToken = null;
        router.push('/');
    },

    CLOSE_MODAL(state) {
        state.modal.open = false;
    },
    OPEN_MODAL(state, modalTexts){
        state.loadingState = false;
        state.modal.title = modalTexts.title;
        state.modal.content = modalTexts.content;
        state.modal.option = modalTexts.option;
        state.modal.open = true;
    },
    START_LOADING(state){
        state.loadingState = true;
    }
}