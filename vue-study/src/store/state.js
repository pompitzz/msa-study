export default {
    loadingState: false,
    modal: {
        open: false,
        title: '',
        content: '',
        option: '',
        route: '',
    },
    articleUrl: '',
    pageInfo:'',
    boardList: [],
    accessToken: localStorage.getItem('access_token'),
    // ============ Rules ============ //
    emailRules: [
        v => !!v || '이메일을 작성해주세요.',
        v => /.+@.+\..+/.test(v) || '이메일 형식으로 작성해주세요.',
    ],
    nameRules: [
        v => !!v || '이름을 작성해주세요.',
    ],
    passwordRules: [
        v => !!v || '비밀번호를 작성해주세요',
    ],
}