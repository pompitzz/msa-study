import {requestAddEvent, requestQueryEvents} from "../apis/calendar_api";
import store from "./store";
import {setSnackBarInfo} from "../apis/common_api";

const state = {
    calendar: {
        startDate: '',
        startTime: '',
        endDate: '',
        endTime: '',
        content: '',
        color: '',
        hasTime: false,
    },
    colors: ['blue', 'indigo', 'deep-purple', 'green', 'orange', 'grey darken-1'],
    events: [],
    dialog: false,
};

const mutations = {
    OPEN_CALENDAR_DIALOG(state, payload) {
        state.calendar.startDate = payload.date;
        state.calendar.startTime = payload.time;
        state.calendar.hasTime = payload.hasTime;
        state.dialog = true;
    },
    CLOSE_CALENDAR_DIALOG(state) {
        state.dialog = false;
    },
    ADD_EVENTS(state, evnet) {
        state.events[0] = evnet;
        state.events[1] = evnet;
        state.events[2] = evnet;
        state.events.forEach(e => e.color = state.colors[3]);
        state.dialog = false;
    }
};


const actions = {
    async REQUEST_ADD_EVENT(context, calendar) {
        try {
            const event = {
                name: calendar.title,
                start: calendar.startDate + getTime(calendar.startTime),
                end: calendar.endDate + getTime(calendar.endTime),
            };

            const response = await requestAddEvent(event);
            console.log('일정 추가 완료');
            console.log(response.data);
            // 이벤트에 그냥 더할것인지 다시 조회할것인지 생각하기
            context.commit('ADD_EVENTS', event);
            store.commit('SET_SNACKBAR', setSnackBarInfo('일정이 추가 되었습니다.', 'info', 'top'))
        } catch (e) {
            console.log('일정 추가 에러' + e);
        }
    },

    async REQEUST_QUERY_EVENTS_BY_MONTH(context, startDate) {
        try {
            const response = await requestQueryEvents(startDate);
            console.log('일정 조회');
            console.log(response.data);
            // 이벤트 채워 넣기
        } catch (e) {
            console.log('일정 조회 에러' + e);
        }
    },

    async REQEUST_DELETE_EVENT(context, eventId) {
        try {
            const response = await requestDelteEvent(eventId);
            console.log('일정 삭제 성공');
            // 이벤트를 뺼껀지, 혹은 다시 조회할껀지
            store.commit('SET_SNACKBAR', setSnackBarInfo('일정이 삭제 되었습니다.', 'info', 'top'))

        } catch (e) {
            console.log('일정 삭제 실패' + e)
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
            console.log('업데이트 성공');
            // 이벤트를 변경할 것인지, 다시 조회할것인지
        }
        catch (e) {
            console.log('이벤트 수정 실패')
        }
    }
};

const getTime = (time) => {
    return time === '' ? '' : ` ${time}`;
};

export default {mutations, state, actions};