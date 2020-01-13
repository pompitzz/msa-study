import axios from 'axios'
import {setTokenInLocalStorage} from "../utils/oauth";
import store from "../store/store";

const config = {
    baseUrl: 'http://localhost:8080'
};

function requestJoinMember(member) {
    member.email = member.email.toLowerCase();
    return axios.post(`${config.baseUrl}/api/members/join`, member);
}

function requestLogin(member) {
    let form = new FormData();
    member.email = member.email.toLowerCase();
    form.append('username', member.email);
    form.append('password', member.password);
    form.append("grant_type", "password");
    const requestData = {
        url: `${config.baseUrl}/oauth/token`,
        method: "POST",
        auth: {
            username: process.env.VUE_APP_CLIENTID,
            password: process.env.VUE_APP_CLIENTSECRET,
        },
        data: form
    };
    return axios(requestData);
}

// function uploadFormData(image) {
//     const data = new FormData();
//     image.forEach(i => data.append('images', i));
//     data.append('name', 'james');
//     data.append('email', 'wqdwq@gamil.com');
//     return data;
// }

// function uploadImage(image) {
//     const data = uploadFormData(image);
//     const requestData = {
//         url: `${config.baseUrl}/upload`,
//         data: data,
//         method: 'POST',
//         headers: {
//             'Content-Type': 'multipart/form-data'
//         }
//     };
//     return axios(requestData);
// }

function requestSaveBoard(board) {
    console.log('saveboard', board);
    return axios.post(`${config.baseUrl}/api/boards`, board);
}

function requesUpdateBoard(payload) {
    console.log('payloadUpdate', payload);
    return axios.put(`${config.baseUrl}/api/boards/${payload.id}`, payload.boardWrite);
}

function queryBoard(pageRequest) {
    return axios.get(`${config.baseUrl}/api/boards/${pageRequest.id}`, {
        params: {
            page: pageRequest.page,
            sort: pageRequest.sort,
            size: pageRequest.size,
        }
    });
}

function requestDeleteComment(commentId) {
    return axios.delete(`${config.baseUrl}/api/comments/${commentId}`);
}


function queryComments(pageAndIdInfo) {
    return axios.get(`${config.baseUrl}/api/comments/${pageAndIdInfo.id}`, {
        params: {
            page: pageAndIdInfo.pageRequest.page,
            sort: pageAndIdInfo.pageRequest.sort,
            size: pageAndIdInfo.pageRequest.size,
        }
    });
}

function queryBoardsByTitle(queryInfo) {
    return axios.get(`${config.baseUrl}/api/boards`, {
        params: {
            title: queryInfo.title,
            page: queryInfo.page,
            sort: queryInfo.sort,
            size: 6
        }
    })
}

function requestContentForModifyBoard(boardId) {
    return axios.post(`${config.baseUrl}/api/boards/modify/${boardId}`);
}

function deleteBoardRequest(boardId) {
    return axios.delete(`${config.baseUrl}/api/boards/validate/${boardId}`);
}

function countBoardViews(id) {
    return axios.put(`${config.baseUrl}/api/boards/count/${id}`);
}

function validateAdmin() {
    return axios.head(`${config.baseUrl}/api/admin/validate`);
}

function queryMembers() {
    return axios.get(`${config.baseUrl}/api/admin/members`);
}


function queryMember(email) {
    console.log('query');
    return axios.get(`${config.baseUrl}/api/members`, {params: {email: email}})
}

function saveComment(comment) {
    return axios.post(`${config.baseUrl}/api/comments`, comment);
}

function modifyComment(comment) {
    return axios.put(`${config.baseUrl}/api/comments/${comment.id}`, '', {
        params: {
            content: comment.content
        }
    })
}

const deleteAccessTokenInHeader = () => {
    axios.defaults.headers.common['Authorization'] = null;
};

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

function requestRefreshToken() {
    let form = new FormData();
    form.append("grant_type", "refresh_token");
    form.append('refresh_token', localStorage.getItem('refresh_token'));
    const requestData = {
        url: `${config.baseUrl}/oauth/token`,
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

function setSnackBarInfo(text, color, location) {
    return {
        text: text,
        color: color,
        location: location,
    }
}

export {
    requestJoinMember,
    requestLogin,
    deleteAccessTokenInHeader,
    requestSaveBoard,
    queryBoard,
    requestContentForModifyBoard,
    countBoardViews,
    queryBoardsByTitle,
    requesUpdateBoard,
    validateAdmin,
    queryMember,
    queryMembers,
    requestRefreshToken,
    setSnackBarInfo,
    saveComment,
    queryComments,
    requestDeleteComment,
    deleteBoardRequest,
    modifyComment,
}
