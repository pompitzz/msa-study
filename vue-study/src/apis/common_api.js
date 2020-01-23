import axios from "axios";
import store from "../store/store";
import {router} from "../routes/route";
import {setTokenInLocalStorage} from "../utils/oauth";

/*
    모든 요청 전 header에 access_token을 담아 전송한다.
 */
axios.interceptors.request.use(
    config => {
        let accessToken = localStorage.getItem('access_token');
        if (accessToken !== null) {
            config.headers.Authorization = accessToken;
        }
        // console.log('Interceptors Request is', config, new Date());
        return config
    },
    error => {
        // console.log('Interceptors Request Error is', error.response, new Date());
        return Promise.reject(error);
    }
);

/*
    만료된 access_token으로 요청시 Access token exprited가 발생하면 refresh 토큰으로 새로운 토큰을 받는다.
 */
axios.interceptors.response.use(
    response => {
        // console.log('Interceptors Response is ', response, new Date());
        return response;
    },
    function (error) {
        // console.log('Interceptors Response Error is ', error.response, new Date());

        if (!error.response) {
            router.push('/error');
        }

        let status = error.response.status;
        if (status === 500) {
            store.commit('PUSH_ERROR_PAGE');
            return Promise.reject(error);
        }

        if (status === 401) {
            let errorData = error.response.data;
            if (errorData.error !== 'invalid_token') {
                return Promise.reject(error);
            }

            if (isExpiredToken(errorData)) {
                return attemptRefreshToken();
            } else {
                store.commit('LOGOUT_WITH_TOKEN_INVALIDE');
                store.commit('SET_SNACKBAR', setSnackBarInfo('토큰 정보가 잘못되었습니다. 다시 로그인 해주세요', 'error', 'top'));
            }
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

function isExpiredToken(errorData) {
    let errorDescription = errorData.error_description;
    return errorDescription.substring(0, 20) === 'Access token expired';
}

function attemptRefreshToken() {
    store.commit('SET_SNACKBAR', setSnackBarInfo('토큰이 만료되어 재발급 합니다.', 'error', 'top'));
    return requestRefreshToken().then(res => {
        setTokenInLocalStorage(res.data);
        store.commit('SET_SNACKBAR', setSnackBarInfo('토큰이 재발급 되었습니다.', 'info', 'top')
        )
    })
        .catch(() => {
            store.commit('SET_SNACKBAR', setSnackBarInfo('Refresh Token이 만료되었습니다. 다시 한번 로그인해주세요.', 'error', 'top'))
            store.commit('LOGOUT_WITH_TOKEN_INVALIDE');
        });
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