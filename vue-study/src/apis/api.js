import axios from 'axios'

const config = {
    baseUrl: 'http://localhost:8080'
};

function requestJoinMember(member) {
    return axios.post(`${config.baseUrl}/api/members/join`, member);
}

function requestLogin(info) {
    let form = new FormData();
    form.append('username', info.email);
    form.append('password', info.password);
    form.append("grant_type", "password");
    const requestData = {
        url: "http://localhost:8080/oauth/token",
        method: "POST",
        auth: {
            username: 'clientApp',
            password: 'secret'
        },
        mimeType: "multipart/form-data",
        data: form
    };
    return axios(requestData);
}

function setAccessTokenInHeader(accessToken) {
    axios.defaults.headers.common['Authorization'] = accessToken ? `Bearer ${accessToken}` : null;
    console.log(axios.defaults.headers);
}

export {requestJoinMember, requestLogin, setAccessTokenInHeader}