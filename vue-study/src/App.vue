<template>
    <v-app class="theme--dark">
        <AppBar/>
        <v-content>
            <router-view/>
        </v-content>

        <v-footer fixed app class="theme--dark"><span class="mx-auto">Copyright © {{new Date() | moment('YYYY')}} All Rights Reserved.</span>
        </v-footer>

        <Modal/>

        <v-snackbar
                :bottom="snackbar.location === 'bottom'"
                :color="snackbar.color"
                :timeout="timeout"
                :top="snackbar.location === 'top'"
                v-model="snackbar.open">
            <h3 class="white--text">{{snackbar.text}}</h3>

            <v-btn @click="snackbar.open = false" color="white--text" text>
                CLOSE
            </v-btn>
        </v-snackbar>
    </v-app>
</template>

<script>
    import AppBar from "./components/AppBar";
    import Modal from "./components/Modal";
    /* TODO
       - 에러 처리 확인
       - 스토리지 시간 클리어
     */
    export default {
        name: 'App',
        data() {
            return {
                timeout: 5000,
            }
        },
        components: {
            Modal,
            AppBar
        },
        computed: {
            snackbar() {
                return this.$store.state.common.snackbar;
            }
        },

    };
</script>
