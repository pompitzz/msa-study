<template>
    <Modal>
        <slot slot="title"> {{modalTitle}}</slot>
        <slot slot="description"> {{modalDescription}}</slot>
        <router-link class="text-white" slot="option" to="/login">
            {{modalOption}}
        </router-link>
    </Modal>
</template>

<script>
    import Modal from "./Modal";
    import {mapMutations, mapState} from "vuex";

    export default {
        name: "NoAuth",
        computed: {
            ...mapState([
                'showModal',
                'modalTitle',
                'modalDescription',
                'modalOption'
            ])
        },
        components: {
            Modal
        },
        methods: {
            ...mapMutations(['CLOSE_MODAL']),
        }
    }
</script>

<style scoped>

</style>