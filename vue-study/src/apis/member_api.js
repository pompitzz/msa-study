import axios from "axios";

function requestJoinMember(member) {
    member.email = member.email.toLowerCase();
    return axios.post(`${process.env.VUE_APP_BASEURL}/api/members/join`, member);
}

function requestLogin(member) {
    let form = new FormData();
    member.email = member.email.toLowerCase();
    form.append('username', member.email);
    form.append('password', member.password);
    form.append("grant_type", "password");
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

function queryMember() {
    return axios.get(`${process.env.VUE_APP_BASEURL}/api/members`);
}

export {
    requestLogin,
    requestJoinMember,
    queryMember
}