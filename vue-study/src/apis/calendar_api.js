import axios from "axios";

function requestAddEvent(event) {
    return axios.post(`${process.env.VUE_APP_BASEURL}/api/events`, event);
}

function requestQueryEvents(startDate) {
    return axios.get(`${process.env.VUE_APP_BASEURL}/api/events`, {
        params: {
            startDate
        }
    });
}

function requestDelteEvent(eventId){
    return axios.delete(`${process.env.VUE_APP_BASEURL}/api/events`, eventId);
}

function requestUpdateEvent(event){
    return axios.put(`${process.env.VUE_APP_BASEURL}/api/events`, event);
}

export {
    requestAddEvent,
    requestQueryEvents,
    requestDelteEvent,
    requestUpdateEvent
}