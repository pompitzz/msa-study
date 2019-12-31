import 'bootstrap-css-only/css/bootstrap.min.css'
import 'mdbvue/lib/css/mdb.min.css'
import '@fortawesome/fontawesome-free/css/all.min.css';
import Vue from 'vue'
import App from './App.vue'
import {router} from "./routes/route";
import {store} from "./store/store";

Vue.config.productionTip = false;

new Vue({
    render: h => h(App),
    router,
    store
}).$mount('#app');
