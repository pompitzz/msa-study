<template>
    <div class="fill-height box">
        <v-container>
            <h1 class="text-center">글쓰기</h1>
            <BoardEditor :board="board" @pass="setBoard"/>
        </v-container>
    </div>
</template>

<script>
    import BoardEditor from "../components/BoardEditor";
    import {mapActions} from "vuex";

    export default {
        components: {
            BoardEditor
        },
        data() {
            return {
                board: {
                    title: '',
                    content: '',
                    boardType: '',
                },
            }
        },
        methods: {
            ...mapActions(['SAVE_BOARD', 'QUERY_ARTICLE']),
            setBoard(editor) {
                this.SAVE_BOARD(editor);
            },
        },
        created() {
            this.QUERY_ARTICLE(this.$route.params.id).then(res => {
                this.board.title = res.title;
                this.board.content = res.content;
                this.board.boardType = res.boardType;
            });
        }
    }
</script>

<style scoped>
</style>