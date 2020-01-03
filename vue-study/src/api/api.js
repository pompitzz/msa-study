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

function setAccessTokenInHeader(accessToken) {
    axios.defaults.headers.common['Authorization'] = accessToken ? `Bearer ${accessToken}` : null;
}

export {requestJoinMember, requestLogin, setAccessTokenInHeader}