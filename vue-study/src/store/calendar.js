import {requestAddEvent, requestQueryEvents, requestEventDetail} from "../apis/calendar_api";
import store from "./store";
import {setSnackBarInfo} from "../apis/common_api";

const state = {
    event: initEvent(),
    events: [],
    eventAddDialog: false,
    eventDetailDialog: false,
    eventDetail: {},
};

const mutations = {
    OPEN_CALENDAR_DIALOG(state, payload) {
        state.event.startDate = payload.date;
        state.event.startTime = payload.time;
        // console.log(state.calendar);
        state.eventAddDialog = true;
    },
    CLOSE_CALENDAR_DIALOG(state) {
        state.eventAddDialog = false;
    },
    ADD_EVENT(state, getEvent) {
        state.events.push(getEvent);
        state.eventAddDialog = false;
        state.event = initEvent();
    },
    ADD_EVENTS(state, events) {
        state.events = [];
        events.forEach(e => {
            state.events.push(makeEvent(e));
        })
    },
    SHOW_EVENT_DETAIL(state, event) {
        state.event = event;
        state.eventDetailDialog = true;
    },
    CLOSE_EVENT_DETAIL(state) {
        state.eventDetailDialog = false;
        state.event = initEvent();
    }
};


const actions = {
    async REQUEST_ADD_EVENT(context, calendar) {
        try {
            const response = await requestAddEvent(calendar);

            const addedEvent = makeEvent(response.data);
            console.log('addedEvent', addedEvent);
            context.commit('ADD_EVENT', addedEvent);
            store.commit('SET_SNACKBAR', setSnackBarInfo('일정이 추가 되었습니다.', 'info', 'top'))
        } catch (e) {
            store.commit('SET_SNACKBAR', setSnackBarInfo('일정 추가 실패.', 'error', 'top'))
        }
    },

    async REQEUST_QUERY_EVENTS_BY_DATE(context, date) {
        try {
            const response = await requestQueryEvents(date);
            context.commit('ADD_EVENTS', response.data);
            console.log(response.data);
        } catch (e) {
            store.commit('SET_SNACKBAR', setSnackBarInfo('이벤트 전체 조회를 실패하였습니다.', 'error', 'top'))
        }
    },

    async REQUEST_DETAIL_EVENT(context, eventId) {
        try {
            console.log('dispated');
            const respone = await requestEventDetail(eventId);
            console.log('dispatch Event', respone.data);
            context.commit('SHOW_EVENT_DETAIL', respone.data);
        } catch (e) {
            store.commit('SET_SNACKBAR', setSnackBarInfo('이벤트 상세 조회를 실패하였습니다.', 'error', 'top'))
        }
    },

    async REQEUST_DELETE_EVENT(context, eventId) {
        try {
            const response = await requestDelteEvent(eventId);
            // console.log('일정 삭제 성공');
            // 이벤트를 뺼껀지, 혹은 다시 조회할껀지
            store.commit('SET_SNACKBAR', setSnackBarInfo('일정이 삭제 되었습니다.', 'info', 'top'))

        } catch (e) {
            // console.log('일정 삭제 실패' + e)
        }
    },

    async REQUEST_UPDATE_EVENT(context, event) {
        try {
            const event = {
                name: calendar.title,
                start: calendar.startDate + getTime(calendar.startTime),
                end: calendar.endDate + getTime(calendar.endTime),
            };

            const response = await requestUpdateEvent(event);
            // console.log('업데이트 성공');
            // 이벤트를 변경할 것인지, 다시 조회할것인지
        } catch (e) {
            // console.log('이벤트 수정 실패')
        }
    }
};

const getTime = (time) => {
    return time === null ? '' : ` ${time}`;
};

const colors = ['blue', 'indigo', 'deep-purple', 'green', 'orange', 'red'];


const makeEvent = (event) => {
    return {
        id: event.id,
        name: event.title,
        start: event.startDate + getTime(event.startTime),
        end: event.endDate + getTime(event.endTime),
        color: colors[Math.floor(Math.random() * 6)]
    }
};

function initEvent() {
    return {
        id: '',
        startDate: '',
        startTime: '',
        endDate: '',
        endTime: '',
        content: '',
        title: '',
    }
}

export default {mutations, state, actions};