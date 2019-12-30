import Vue from 'vue'
import App from './App.vue'
import 'mdbootstrap/css/mdb.css'
import 'mdbootstrap/css/bootstrap.min.css'
import 'mdbootstrap/css/style.css'
import 'mdbootstrap/js/jquery.min'
import 'mdbootstrap/js/popper.min'
import 'mdbootstrap/js/bootstrap.min'
import '@fortawesome/fontawesome-free/css/all.min.css';
import {router} from "./routes/route";
import {store} from "./store/sotre";

Vue.config.productionTip = false;

new Vue({
    render: h => h(App),
    router,
    store
}).$mount('#app');
