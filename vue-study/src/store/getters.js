export default {
    isAuthenticated(state) {
        return !!state.accessToken;
    },
    getSnackBarInfo(state) {
        return state.snackbarInfo;
    }
}