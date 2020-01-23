const setTokenInLocalStorage = (tokenInfo) => {
    // console.log('SET LOCAL STORAGE')
    localStorage.setItem("access_token", 'Bearer ' + tokenInfo.access_token);
    localStorage.setItem("refresh_token", tokenInfo.refresh_token);
};

const deleteTokenInLocalStorage = () => {
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
    localStorage.removeItem("email");
    localStorage.removeItem("name");
};
export {deleteTokenInLocalStorage, setTokenInLocalStorage}