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
                <v-btn @click="submit" class="white--text mb-3 mr-3" color="indigo" v-if="modal.option2 !== null">
                    {{modal.option2}}
                </v-btn>
                <v-btn @click="modalOption" class="white--text mb-3 mr-3" color="indigo">{{modal.option1}}</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>

    export default {
        name: "Modal",
        computed: {
            modal() {
                return this.$store.state.common.modal;
            }
        },
        methods: {
            modalOption() {
                if (this.modal.option1 === '닫기') {
                    this.$store.commit('CLOSE_MODAL');
                } else {
                    this.$emit('pass');
                }
            },
            submit() {
                this.$emit('pass');
            }
        }
    }
</script>

<style scoped>

</style>