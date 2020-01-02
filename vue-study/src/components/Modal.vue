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
                <v-btn v-if="modal.option === '닫기'" color="indigo" class="white--text mb-3 mr-3" @click="CLOSE_MODAL">{{modal.option}}</v-btn>
                <v-btn v-else color="indigo" class="white--text mb-3 mr-3" @click="toLoginPage">{{modal.option}}</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    import {mapState, mapMutations} from 'vuex'
    import {router} from "../routes/route";

    export default {
        name: "Modal",
        computed:{
            ...mapState(['modal'])
        },
        methods:{
            ...mapMutations(['CLOSE_MODAL']),
            toLoginPage() {
                this.CLOSE_MODAL();
                router.push('/login');
            }
        }
    }
</script>

<style scoped>

</style>