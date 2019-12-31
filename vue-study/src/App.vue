<template>
    <div id="app">
        <Navbar/>
        <router-view/>
    </div>
</template>

<script>
    import Navbar from "./components/Navbar";

    export default {
        name: 'app',
        components: {
            Navbar,
        }
    }
</script>

<style>
    @import url('https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap');
    @import url('https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap');

    html body {
        padding: 0;
        margin: 0;
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
    }

    .shadow-xl {
        box-shadow: 0 1rem 2.5rem -0.8rem rgba(0, 0, 0, 0.56), 0 0.25rem 1.5rem rgba(0, 0, 0, 0.12), 0 0.5rem 0.75rem -0.3rem rgba(0, 0, 0, 0.2);;
    }

    /* Router Transition */
    .page-enter-active, .page-leave-active {
        transition: opacity;
    }

    .page-enter, .page-leave-to {
        opacity: 0;
    }

    .only-close-modal .modal-content {
        margin-top: 50%;
    }
</style>
