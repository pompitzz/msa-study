<template>
    <v-app class="theme--dark">
        <AppBar/>
        <v-content>
            <router-view/>
        </v-content>
        <v-footer app class="theme--dark"><span class="mx-auto">Copyright © {{new Date() | moment('YYYY')}} All Rights Reserved.</span>
        </v-footer>
        <Modal/>
    </v-app>
</template>

<script>
    import AppBar from "./components/AppBar";
    import Modal from "./components/Modal";

    export default {
        name: 'App',
        components: {
            Modal,
            AppBar
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
    }
</style>