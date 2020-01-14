import axios from "axios";
import store from "../store/store";
import {setTokenInLocalStorage} from "../utils/oauth";

/*
    모든 요청 전 header에 access_token을 담아 전송한다.
 */
axios.interceptors.request.use(
    config => {
        config.headers.Authorization = localStorage.getItem('access_token');
        console.log('Interceptors Request is', config, new Date());
        return config
    },
    error => {
        console.log('Interceptors Request Error is', error.response, new Date());
        return Promise.reject(error);
    }
);

/*
    만료된 access_token으로 요청시 Access token exprited가 발생하면 refresh 토큰으로 새로운 토큰을 받는다.
 */
axios.interceptors.response.use(
    response => {
        console.log('Interceptors Response is ', response, new Date());
        return response;
    },
    function (error) {
        console.log('Interceptors Response Error is ', error.response, new Date());

        if (isExpiredToken(error)) {
            store.commit('SET_SNACKBAR', setSnackBarInfo('토큰이 만료되어 재발급 합니다.', 'error', 'top'));
            return requestRefreshToken().then(res => {
                setTokenInLocalStorage(res.data);
                store.commit('SET_SNACKBAR', setSnackBarInfo('토큰이 재발급 되었습니다.', 'info', 'top')
                )
            })
                .catch(error => store.commit('SET_SNACKBAR', setSnackBarInfo('재발급을 실패하였습니다.' + error, 'error', 'top')));
        }

        return Promise.reject(error);
    }
);

function requestRefreshToken() {
    let form = new FormData();
    form.append("grant_type", "refresh_token");
    form.append('refresh_token', localStorage.getItem('refresh_token'));
    const requestData = {
        url: `${process.env.VUE_APP_BASEURL}/oauth/token`,
        method: "POST",
        auth: {
            username: process.env.VUE_APP_CLIENTID,
            password: process.env.VUE_APP_CLIENTSECRET,
        },
        data: form
    };
    return axios(requestData);
}

function isExpiredToken(error) {
    let errorDescription = error.response.data.error_description;
    return errorDescription.substring(0, 20) === 'Access token expired';
}

function setSnackBarInfo(text, color, location) {
    return {
        text: text,
        color: color,
        location: location,
    }
}

const deleteAccessTokenInHeader = () => {
    axios.defaults.headers.common['Authorization'] = null;
};

export {
    deleteAccessTokenInHeader,
    setSnackBarInfo
}