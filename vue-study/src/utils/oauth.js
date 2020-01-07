const setTokenInLocalStorage = (tokenInfo) => {
    localStorage.setItem("access_token", tokenInfo.access_token);
    localStorage.setItem("refresh_token", tokenInfo.refresh_token);
};

const deleteTokenInLocalStorage = () => {
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token")
};
export {deleteTokenInLocalStorage, setTokenInLocalStorage}