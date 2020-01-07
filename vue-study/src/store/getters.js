export default {
    isAuthenticated(state) {
        return !!state.accessToken;
    }
}