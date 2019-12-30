<template>
    <div id="app">
        <Test/>
        <transition name="page">
            <router-view/>
        </transition>
    </div>
</template>

<script>
    import Test from "./components/Header";

    export default {
        name: 'app',
        components: {
            Test,
        }
    }
</script>

<style>
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
</style>
