import axios from 'axios'


function requestDeleteComment(commentId) {
    return axios.delete(`${process.env.VUE_APP_BASEURL}/api/comments/${commentId}`);
}

function queryComments(pageAndIdInfo) {
    return axios.get(`${process.env.VUE_APP_BASEURL}/api/comments/${pageAndIdInfo.id}`, {
        params: {
            page: pageAndIdInfo.pageRequest.page,
            sort: pageAndIdInfo.pageRequest.sort,
            size: pageAndIdInfo.pageRequest.size,
        }
    });
}

function saveComment(comment) {
    return axios.post(`${process.env.VUE_APP_BASEURL}/api/comments`, comment);
}

function modifyComment(comment) {
    return axios.put(`${process.env.VUE_APP_BASEURL}/api/comments/${comment.id}`, '', {
        params: {
            content: comment.content
        }
    })
}



export {
    saveComment,
    queryComments,
    requestDeleteComment,
    modifyComment,
}
