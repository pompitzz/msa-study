<template>
    <div>
        <v-app class="theme--dark">
            <AppBar/>
            <v-content>
                <router-view/>
            </v-content>
            <Footer/>
            <Modal/>
            <SnackBar/>
            <Spinner/>
        </v-app>
    </div>
</template>

<script>
    import AppBar from "./components/AppBar";
    import Modal from "./components/Modal";
    import SnackBar from "./components/SnackBar";
    import Spinner from "./components/Spinner";
    import Footer from "./components/Footer";

    export default {
        name: 'App',
        components: {
            Footer,
            Modal,
            AppBar,
            SnackBar,
            Spinner
        },
    };
</script>
