<template>
    <div class="fill-height box">
        <v-container>
            <h1 class="text-center">글쓰기</h1>
            <v-select
                    :items="items"
                    label="카테고리"
                    dark
                    outlined
                    dense
                    style="width: 145px; height:57px;"
                    @input="setBoardType"
            ></v-select>

            <v-text-field class="mt-5" dark
                          height="50" label="제목" outlined
                          placeholder="제목을 입력하세요"
                          v-model="board.title"
            ></v-text-field>
            <editor height="500px"
                    v-model="board.content"/>

            <v-btn @click="writeBoard"
                   class="mt-5 float-right mr-3"
                   dark>
                글쓰기
            </v-btn>
        </v-container>
    </div>
</template>

<script>
    import Editor from '@toast-ui/vue-editor/src/Editor.vue'
    import {mapActions} from 'vuex'

    export default {
        name: "Boards",
        data() {
            return {
                items: [
                    {text: '공지사항'},
                    {text: '자유게시판'},
                    {text: '공부방'},
                ],
                board: {
                    title: '',
                    content: '',
                    author: '유저이름',
                    viewsCount: 0,
                    email: localStorage.getItem('email'),
                    boardType: '',
                },
                href: '',
            }
        },
        components: {
            Editor
        },
        methods: {
            ...mapActions(['SAVE_BOARD']),
            writeBoard() {
                if (this.isNotEmpty()) {
                    this.SAVE_BOARD(this.board).then(res => this.href = res._links.self.href);
                    console.log(this.href);
                }
            },
            isNotEmpty() {
                return !(this.board.title === '' || this.board.content === '' || this.board.category === '');
            },
            setBoardType(value) {
                if (value === '공부방') {
                    this.board.boardType = 'STUDY';
                } else if (value === '자유게시판') {
                    this.board.boardType = 'FREE'
                } else if (value === '공지사항') {
                    this.board.boardType = 'NOTICE'
                }
            }
        }
    }
</script>

<style scoped>
</style>