<template>
    <!-- Modal -->
    <transition aria-hidden="true" aria-labelledby="exampleModalCenterTitle" class="modal fadeInUp"
                id="exampleModalCenter"
                role="dialog"
                tabindex="-1">

        <!-- Add .modal-dialog-centered to .modal-dialog to vertically center the modal -->
        <div class="modal-dialog modal-dialog-centered" role="document">


            <div class="modal-content  p-3 mx-auto">
                <div class="modal-header">
                    <h5 class="modal-title text-danger" id="exampleModalLongTitle">
                        <slot name="title">Modal title</slot>
                    </h5>
                </div>
                <div class="p-3">
                    <slot name="description">description</slot>
                    <button class="btn btn-secondary d-flex ml-auto" name="close" type="button">
                        <slot name="close">Close</slot>
                    </button>
                </div>
            </div>
        </div>
    </transition>
</template>

<script>
    export default {
        name: "Modal"
    }
</script>

<style scoped>
    .modal-dialog {
        max-width: 500px;
        margin-top: 0;
        margin-bottom: 0;
    }

    .modal-header h5 {
        font-weight: bold;
    }
</style>