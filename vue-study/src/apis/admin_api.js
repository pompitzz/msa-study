import axios from "axios";

function validateAdmin() {
    return axios.head(`${process.env.VUE_APP_BASEURL}/api/admin/validate`);
}

function queryMembers() {
    return axios.get(`${process.env.VUE_APP_BASEURL}/api/admin/members`);
}


export {
    validateAdmin,
    queryMembers
}