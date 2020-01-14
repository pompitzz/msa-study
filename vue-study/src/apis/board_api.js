import axios from "axios";

function requestSaveBoard(board) {
    console.log('saveboard', board);
    return axios.post(`${process.env.VUE_APP_BASEURL}/api/boards`, board);
}

function requesUpdateBoard(payload) {
    console.log('payloadUpdate', payload);
    return axios.put(`${process.env.VUE_APP_BASEURL}/api/boards/${payload.id}`, payload.boardWrite);
}

function queryBoard(pageRequest) {
    return axios.get(`${process.env.VUE_APP_BASEURL}/api/boards/${pageRequest.id}`, {
        params: {
            page: pageRequest.page,
            sort: pageRequest.sort,
            size: pageRequest.size,
        }
    });
}

function queryBoardsByTitle(queryInfo) {
    return axios.get(`${process.env.VUE_APP_BASEURL}/api/boards`, {
        params: {
            title: queryInfo.title,
            page: queryInfo.page,
            sort: queryInfo.sort,
            size: 6
        }
    })
}


function requestBoardContentForModifyBoard(boardId) {
    return axios.post(`${process.env.VUE_APP_BASEURL}/api/boards/modify/${boardId}`);
}

function deleteBoardRequest(boardId) {
    return axios.delete(`${process.env.VUE_APP_BASEURL}/api/boards/validate/${boardId}`);
}

function countBoardViews(id) {
    return axios.put(`${process.env.VUE_APP_BASEURL}/api/boards/count/${id}`);
}

export {
    requestSaveBoard,
    requesUpdateBoard,
    queryBoard,
    queryBoardsByTitle,
    countBoardViews,
    deleteBoardRequest,
    requestBoardContentForModifyBoard,
}