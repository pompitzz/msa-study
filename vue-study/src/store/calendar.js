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
        state.dialog = false;
    }
};


const actions = {
    REQUEST_ADD_EVENT(context, calendar) {
        console.log(calendar);
        const event = {
            name: calendar.title,
            start: calendar.startDate + getTime(calendar.startTime),
            end: calendar.endDate + getTime(calendar.endTime),
            color: 'red',
        };
        context.commit('ADD_EVENTS', event);
    }
};

const getTime = (time) => {
    return time === '' ? '' : ` ${time}`;
};

export default {mutations, state, actions};