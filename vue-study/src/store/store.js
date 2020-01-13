import Vue from 'vue'
import Vuex from 'vuex'
import actions from './actions'
import getters from './getters'
import mutations from "./mutations";
import comment from "./comment";
import state from "./state"
import board from "./board";
Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        comment,
        board,
    },
    state,
    getters,
    mutations,
    actions
});
