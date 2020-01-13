<template>
    <div class="fill-height box">
        <v-container>
            <h1 class="text-center">글쓰기</h1>
            <BoardEditor @submit="requestUpdateBoard"/>
        </v-container>
    </div>
</template>

<script>
    import BoardEditor from "../components/BoardEditor";

    export default {
        components: {
            BoardEditor
        },
        methods: {
            requestUpdateBoard(boardWrite) {
                this.$store.dispatch('MODIFY_BOARD', {
                    boardWrite,
                    id: this.$route.params.id,
                });
            }
        },
        created() {
            this.$store.dispatch('QUERY_MODIFY_BOARD', this.$route.params.id);
        }
    }
</script>

<style scoped>
</style>