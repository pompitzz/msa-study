<template>
    <transition>
        <mdb-modal :show="modal" @close="CLOSE_MODAL" class="only-close-modal">
            <div class="p-2">
                <mdb-modal-header>
                    <mdb-modal-title color="warning">
                        <h5 class="red-text my-auto">
                            <slot name="title">
                                Title
                            </slot>
                        </h5>
                    </mdb-modal-title>
                </mdb-modal-header>
                <mdb-modal-body>
                    <slot name="description">
                        다시 한번 더 시도해주세요
                    </slot>
                    <br>
                    <mdb-btn @click.native="CLOSE_MODAL" class="d-flex ml-auto" color="secondary">
                        <slot name="option">TEST</slot>
                    </mdb-btn>
                </mdb-modal-body>
            </div>
        </mdb-modal>
    </transition>
</template>
<script>
    import {mdbBtn, mdbModal, mdbModalTitle, mdbModalHeader, mdbModalBody} from 'mdbvue'
    import {mapMutations, mapState} from 'vuex'

    export default {
        name: "Main",
        components: {
            mdbBtn,
            mdbModal,
            mdbModalTitle,
            mdbModalHeader,
            mdbModalBody,
        },
        computed: {
            ...mapState(['modal']),
        },
        methods: {
            ...mapMutations(['CLOSE_MODAL']),
        }
    }
</script>

<style scoped>
    .modal-dialog {
        margin-top: 500px;
    }

    .modal-content {
        margin-top: 300px;
    }
</style>