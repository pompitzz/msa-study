import {router} from "../routes/route";
import {setAccessTokenInHeader} from "../apis/api";

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
        state.showModal = true;
    },
    CLOSE_MODAL(state) {
        state.showModal = false;
    },
    LOGIN(state, responseTokenInfo) {
        changeTokenInfo(state, responseTokenInfo);
        setAccessTokenInHeader(responseTokenInfo.access_token);
        router.push('/main');
    },
    LOGOUT(state) {
        changeTokenInfo(state, null);
        setAccessTokenInHeader(null);
        router.push('/main');
    },
}