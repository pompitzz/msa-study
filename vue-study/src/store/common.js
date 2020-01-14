import {router} from "../routes/route";

const state = {
    loadingState: false,
    snackbar: {open: false, text: '', location: 'top',},
    modal: {open: false, title: '', content: '', option: '',},
    // ============ Rules ============ //
    emailRules: [
        v => !!v || '이메일을 작성해주세요.',
        v => /.+@.+\..+/.test(v) || '이메일 형식으로 작성해주세요.',
        v => v.search(/\s/) === -1 || '공백을 제거해주세요!'
    ],
    nameRules: [
        v => !!v || '이름을 작성해주세요.',
        v => v.search(/\s/) === -1 || '공백을 제거해주세요!'
    ],
    passwordRules: [
        v => !!v || '비밀번호를 작성해주세요',
        v => v.search(/\s/) === -1 || '공백을 제거해주세요!'
    ],
};

const getters = {

};

const mutations = {
    CLOSE_MODAL(state) {
        state.modal.open = false;
    },
    CLOSE_MODAL_AND_MOVE(state, route){
        state.modal.open = false;
        router.push(route);
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
    END_LOADING(state){
        state.loadingState = false;
    },
    SET_SNACKBAR(state, snackbarInfo) {
        state.snackbar.open = true;
        state.snackbar.text = snackbarInfo.text;
        state.snackbar.color = snackbarInfo.color;
        state.snackbar.location = snackbarInfo.location;
    }
};

const actions = {
};

export default {mutations, state, actions, getters};