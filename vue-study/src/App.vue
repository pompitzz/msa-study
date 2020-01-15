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

<style>
    a {
        text-decoration: none;
    }


    @media (min-width: 1200px) {
        .container {
            max-width: 1000px !important;
        }
    }

    .v-application code:after {
        content: none !important;
    }

    .v-application code {
        box-shadow: none !important;
    }

    .theme--light.v-card {
        background-color: #e0e0e0 !important;
    }

    .tui-editor-contents pre {
        background-color: #f5f5f5 !important;
        margin-top: 8 ox !important;
    }

    .tui-editor-contents hr {
        border-top: 1px solid black !important;
    }

    .row {
        margin: 0 auto !important;
    }

    blockquote {
        border-left: 4px solid #b8b8b8 !important;
    }

    .v-calendar-monthly .v-present button {
        background-color: #82b1ff !important;
    }

    .v-calendar-monthly .v-calendar-weekly__day-label {
        margin-bottom: 4px;
    }

    .v-calendar-daily .v-calendar-daily__interval-text {
        color: white !important;
    }

    .v-calendar-daily__intervals-body {
        margin-top: 4px !important;
    }

    .v-menu__content.theme--light.menuable__content__active {
        min-width: auto !important;
    }

    .tui-editor-contents h4 {
        margin-bottom: 5px !important;
    }
</style>