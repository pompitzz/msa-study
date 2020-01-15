import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import {router} from './routes/route'
import store from "./store/store";
import VueMoment from 'vue-moment'
import moment from 'moment'


import 'tui-editor/dist/tui-editor.css';
import 'tui-editor/dist/tui-editor-contents.css';
import 'codemirror/lib/codemirror.css';
import 'highlight.js/styles/github.css';

Vue.config.productionTip = false;
Vue.use(VueMoment, {
    moment,
});

const ignoreWarnMessage = 'The .native modifier for v-on is only valid on components but it was used on <div>.';
Vue.config.warnHandler = function (msg, vm, trace) {
    // `trace` is the component hierarchy trace
    if (msg === ignoreWarnMessage) {
        msg = null;
        vm = null;
        trace = null;
    }
};

new Vue({
    vuetify,
    router,
    store,
    render: h => h(App)
}).$mount('#app');
