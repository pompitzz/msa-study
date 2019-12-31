import {router} from "../routes/route";

const changeTokenInfo = (state, data) => {
    state.tokenInfo.accessToken = data ? data.access_token : null;
    state.tokenInfo.refreshToken = data ? data.refresh_token : null;
    state.tokenInfo.expires_in = data ? data.expires_in : null;
};

export default {
    SET_MODAL_TEXTS(state, info) {
        state.modalTitle = info.title;
        state.modalDescription = info.description;
        state.modalOption = info.option;
        state.modal = true;
    },
    CLOSE_MODAL(state) {
        state.modal = false;
    },
    OPEN_MODAL(state) {
        state.modal = true;
    },
    LOGIN(state, responseTokenInfo) {
        changeTokenInfo(state, responseTokenInfo);
        router.push('/main');
    },
    LOGOUT(state) {
        changeTokenInfo(state, null);
        router.push('/main');
    },
}