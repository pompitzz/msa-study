import Vue from 'vue'
import Vuex from 'vuex'
import comment from "./comment";
import board from "./board";
import common from "./common";
import admin from "./admin";
import member from "./member";
Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        common,
        member,
        admin,
        comment,
        board,
    },
});
