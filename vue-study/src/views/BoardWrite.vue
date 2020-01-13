<template>
    <div class="fill-height box">
        <v-container>
            <h1 class="text-center">글쓰기</h1>
            <BoardEditor @submit="requestSaveBoard"/>
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
            requestSaveBoard(boardWrite) {
                this.$store.dispatch('SAVE_BOARD', boardWrite);
            }
        }
    }
</script>

<style scoped>
</style>