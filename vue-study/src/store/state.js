export default {
    loadingState: false,
    modal: {
        open: false,
        title: '',
        content: '',
        option: '',
    },
    tokenInfo: {
        accessToken: '',
        refreshToken: '',
        expires_in: '',
    },

    // ============ Rules ============ //
    emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
    ],
    nameRules: [
        v => !!v || 'Name is required',
    ],
    passwordRules: [
        v => !!v || 'Password is required',
    ]
}