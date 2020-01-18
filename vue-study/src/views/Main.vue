<template>
    <div class="fill-height">
        <v-row align="center" class="fill-height" justify="center">
            <h1 class="display-2">Main Page</h1>
        </v-row>
        <Modal/>
    </div>
</template>

<script>
    import Modal from "../components/Modal";

    export default {
        name: "Main",
        components: {Modal},
        data() {
            return {
                dialog: false,
                two: 90,
                three: 85,
                four: 80,
                items: ['Foo', 'Bar', 'Fizz', 'Buzz'],
                model: 'Foo',
            }
        },
    }
</script>

<style scoped>
    code {
        box-shadow: none !important;
    }
</style>