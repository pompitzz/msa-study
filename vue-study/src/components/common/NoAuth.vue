<template>
    <Modal @close="CLOSE_MODAL" v-if="showModal">
        <h5 slot="title"> {{modalTitle}} </h5>
        <p slot="description"> {{modalDescription}} </p>
        <div @click="CLOSE_MODAL" slot="close">
            <router-link class="text-white" to="/login">
                {{modalOption}}
            </router-link>
        </div>
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