import {router} from "../routes/route";
import {setAccessTokenInHeader} from "../api/api";

const changeTokenInfo = (state, data) => {
    state.tokenInfo.accessToken = data ? data.access_token : null;
    state.tokenInfo.refreshToken = data ? data.refresh_token : null;
    state.tokenInfo.expires_in = data ? data.expires_in : null;
};

export default {
    LOGIN_SUCCESS(state, responseTokenInfo) {
        changeTokenInfo(state, responseTokenInfo);
        setAccessTokenInHeader(state.tokenInfo.accessToken);
        router.push('/');
    },
    LOGOUT(state) {
        changeTokenInfo(state, null);
        setAccessTokenInHeader(state.tokenInfo.accessToken);
        router.push('/');
    },
    CLOSE_MODAL(state){
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