import Vue from 'vue'
import Vuex from 'vuex'
import comment from "./comment";
import board from "./board";
import common from "./common";
import admin from "./admin";
import member from "./member";
import calendar from "./calendar";
Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        common,
        member,
        admin,
        comment,
        board,
        calendar
    },
});
