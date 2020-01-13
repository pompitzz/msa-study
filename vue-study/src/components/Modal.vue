<template>
    <v-dialog v-model="modal.open" persistent max-width="290">
        <v-card>
            <v-card-title>
                {{modal.title}}
            </v-card-title>
            <hr>
            <v-card-text class="mt-3">
                {{modal.content}}
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn @click="modalOption" class="white--text mb-3 mr-3" color="indigo">{{modal.option}}</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    import {mapState, mapMutations} from 'vuex'

    export default {
        name: "Modal",
        computed:{
            ...mapState(['modal'])
        },
        methods: {
            ...mapMutations(['CLOSE_MODAL']),
            modalOption() {
                if (this.modal.option === '닫기') {
                    this.CLOSE_MODAL();
                    window.scrollTo(0, document.body.scrollHeight || document.documentElement.scrollHeight);
                } else {
                    this.$emit('pass');
                }
            }
        }
    }
</script>

<style scoped>

</style>