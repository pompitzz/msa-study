import Vue from 'vue'
import Vuex from 'vuex'
import actions from './actions'
import getters from './getters'
import mutations from "./mutations";

Vue.use(Vuex);

export const store = new Vuex.Store({
    state: {
        modal: false,
        modalTitle: '',
        modalDescription: '',
        modalOption: '',
        tokenInfo: {
            accessToken: '',
            refreshToken: '',
            expires_in: '',
        }
    },
    getters,
    mutations,
    actions
});
