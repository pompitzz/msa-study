<template>
    <div>

        <button @click="modalOpen" style="margin: 50px">Click</button>

        <Modal @close="showModal = false" v-if="showModal">
            <h5 slot="title"> 테스트1 </h5>
            <p slot="description"> 설명 </p>
            <div @click="showModal = false" slot="close">Close</div>
        </Modal>
    </div>
</template>

<script>
    import Modal from "./common/Modal";

    export default {
        name: "Main",
        data() {
            return {
                showModal: false,
            }
        },
        components: {
            Modal
        },
        methods: {
            modalOpen() {
                this.showModal = true;
            }
        }
    }
</script>

<style scoped>

</style>