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
    var requestData = {
        url: "http://localhost:8080/oauth/token  ",
        method: "POST",
        timeout: 0,
        headers: {
            "Authorization": "Basic Y2xpZW50QXBwOnNlY3JldA==",
            "Content-Type": "multipart/form-data; boundary=--------------------------499393223548059946087348"
        },
        processData: false,
        mimeType: "multipart/form-data",
        contentType: false,
        data: form
    };
    console.log(requestData);
    return axios(requestData);
    // var config = {
    //     headers: {
    //         'Authorization': 'clientApp'+' ' + 'secret',
    //         'Accept' : 'application/json',
    //         'Content-Type' : 'application/x-www-form-urlencoded',
    //     },
    //     data: form,
    // };
    // var params = {
    //     username: info.email,
    //     password: info.password,
    //     grant_type: 'password'
    // };
    // return axios.post("http://localhost:8080/oauth/token/",  {
    //     params: params,
    //     withCredentials: true,
    //     auth: {
    //         username: 'clientApp',
    //         password: 'secret'
    //     }
    // })
}

export {requestJoinMember, requestLogin}