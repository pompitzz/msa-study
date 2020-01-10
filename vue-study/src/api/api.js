import axios from 'axios'

const config = {
    baseUrl: 'http://localhost:8080'
};

function requestJoinMember(member) {
    return axios.post(`${config.baseUrl}/api/members/join`, member);
}

function requestLogin(member) {
    let form = new FormData();
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

function uploadFormData(image) {
    const data = new FormData();
    image.forEach(i => data.append('images', i));
    data.append('name', 'james');
    data.append('email', 'wqdwq@gamil.com');
    return data;
}

function uploadImage(image) {

    const data = uploadFormData(image);

    const requestData = {
        url: `${config.baseUrl}/upload`,
        data: data,
        method: 'POST',
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    };

    return axios(requestData);
}

function requestSaveBoard(board) {
    console.log('board', board);
    return axios.post(`${config.baseUrl}/api/boards`, board);
}

function queryArticle(id) {
    return axios.get(`${config.baseUrl}/api/boards/${id}`);
}

function queryBoards(pageRequest) {
    return axios.get(`${config.baseUrl}/api/boards`, {
        params: {
            page: pageRequest.page,
            sort: pageRequest.sort,
            size: 7
        }
    })
}

function validateBoardMember(emailAndBoardId) {
    return axios.get(`${config.baseUrl}/api/boards/validate`, {
        params: {
            email: emailAndBoardId.email,
            boardId: emailAndBoardId.id
        }
    });
}

function countBoardViews(id) {
    return axios.put(`${config.baseUrl}/api/boards/count/${id}`);
}

const setAccessTokenInHeader = (accessToken) => {
    axios.defaults.headers.common['Authorization'] = "Bearer " + accessToken;
};

const deleteAccessTokenInHeader = () => {
    axios.defaults.headers.common['Authorization'] = null;
};
export {
    requestJoinMember,
    requestLogin,
    setAccessTokenInHeader,
    deleteAccessTokenInHeader,
    uploadImage,
    requestSaveBoard,
    queryArticle,
    queryBoards,
    validateBoardMember,
    countBoardViews
}