export default {
    isAuthenticated(state) {
        return !!state.tokenInfo.accessToken;
    }
}