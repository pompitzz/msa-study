import {router} from "../routes/route";

const state = {
    loadingState: false,
    spinnerLoading: false,
    snackbar: {open: false, text: '', location: 'top',},
    modal: {open: false, title: '', content: '', option1: '', option2: '',},
    errorMessage: '잘못된 요청입니다.',


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

const getters = {};

const mutations = {
    CLOSE_MODAL(state) {
        state.modal.open = false;
    },
    OPEN_MODAL(state, modalTexts) {
        state.loadingState = false;
        state.modal.title = modalTexts.title;
        state.modal.content = modalTexts.content;
        state.modal.option1 = modalTexts.option1;
        state.modal.option2 = modalTexts.option2 ? modalTexts.option2 : null;
        state.modal.open = true;
    },
    START_LOADING(state) {
        state.loadingState = true;
    },
    END_LOADING(state) {
        state.loadingState = false;
    },
    SET_SNACKBAR(state, snackbarInfo) {
        state.snackbar.open = true;
        state.snackbar.text = snackbarInfo.text;
        state.snackbar.color = snackbarInfo.color;
        state.snackbar.location = snackbarInfo.location;
    },
    PUSH_ERROR_PAGE(state) {
        state.errorMessage = '잘못된 요청입니다.';
        router.push('/error');
    },
    START_SPINNER(state) {
        state.spinnerLoading = true;
    },
    END_SPINNER(state) {
        state.spinnerLoading = false;
    }

};

const actions = {};

export default {mutations, state, actions, getters};