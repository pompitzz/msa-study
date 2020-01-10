<template>
    <v-app class="theme--dark">
        <AppBar/>
        <v-content>
            <router-view/>
        </v-content>
        <v-footer app class="theme--dark"><span class="mx-auto">Copyright © {{new Date() | moment('YYYY')}} All Rights Reserved.</span>
        </v-footer>
    </v-app>
</template>

<script>
    import AppBar from "./components/AppBar";

    export default {
        name: 'App',
        components: {
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
</style>