<template>
    <div>
        <v-select
                label="카테고리"
                :items="items"
                @input="setBoardType"
                v-model="model"
                dark
                dense
                outlined
                style="width: 145px; height:57px;"
        ></v-select>
        <v-text-field class="mt-5" dark
                      height="50" label="제목" outlined
                      placeholder="제목을 입력하세요"
                      v-model="boardWrite.title"
        ></v-text-field>
        <v-card>
            <editor height="500px"
                    v-model="boardWrite.content"/>
        </v-card>
        <v-btn @click="writeBoard"
               color="indigo"
               class="mt-5 float-right mr-3"
               dark>
            글쓰기
        </v-btn>
    </div>
</template>

<script>
    import Editor from '@toast-ui/vue-editor/src/Editor.vue'

    export default {
        name: "Writer",
        data() {
            return {
                items: ['공지사항', '자유게시판', '공부'],
                model: '',
            }
        },
        computed: {
            boardWrite() {
                return this.$store.state.board.boardWrite;
            }
        },
        components: {
            Editor
        },
        props: ['board'],
        methods: {
            isNotEmpty() {
                return !(this.boardWrite.title === '' || this.boardWrite.content === '' || this.boardWrite.boardType === '');
            },
            setBoardType(value) {
                if (value === '공부') {
                    this.boardWrite.boardType = 'STUDY';
                } else if (value === '자유게시판') {
                    this.boardWrite.boardType = 'FREE'
                } else if (value === '공지사항') {
                    this.boardWrite.boardType = 'NOTICE'
                }
            },
            writeBoard() {
                if (this.isNotEmpty()) {
                    return this.$emit("submit", this.boardWrite);
                } else {
                    this.$store.commit('SET_SNACKBAR', {text: '빈칸을 모두 작성해주세요', color: 'error', location: 'top'});
                }
            },
            setItems(boardType) {
                if (boardType === 'NOTICE') {
                    this.items = ['공지사항', '자유게시판', '공부'];
                } else if (boardType === 'FREE') {
                    this.items = ['자유게시판', '공지사항', '공부'];
                } else if (boardType === 'STUDY') {
                    this.items = ['공부', '자유게시판', '공지사항'];
                }
            }
        },
        created() {
            let boardType = this.boardWrite.boardType;
            if (boardType !== '') {
                this.model = boardType;
                this.setItems(boardType);


            }
            console.log(this.boardWrite.boardType);
        },
        destroyed() {
            this.$store.commit('CLEAR_BOARD_WRITE');
        }
    }
</script>

<style scoped>

</style>