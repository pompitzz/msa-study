import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import {router} from './routes/route'
import store from "./store/store";

import 'tui-editor/dist/tui-editor.css';
import 'tui-editor/dist/tui-editor-contents.css';
import 'codemirror/lib/codemirror.css';
import 'highlight.js/styles/github.css';

Vue.config.productionTip = false;


new Vue({
    vuetify,
    router,
    store,
    render: h => h(App)
}).$mount('#app');
