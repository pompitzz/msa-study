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


const setAccessTokenInHeader = (accessToken) => {
    axios.defaults.headers.common['Authorization'] = accessToken;
};

const deleteAccessTokenInHeader = () => {
    axios.defaults.headers.common['Authorization'] = null;
};
export {requestJoinMember, requestLogin, setAccessTokenInHeader, deleteAccessTokenInHeader, uploadImage}