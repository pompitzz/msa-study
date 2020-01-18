<template>
    <div>
        <v-select
                label="카테고리"
                :items="items"
                @input="setBoardType"
                v-model="boardWrite.boardType"
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
                items: [
                    {text: '공지사항', value: 'NOTICE'},
                    {text: '자유게시판', value: 'FREE'},
                    {text: '공부', value: 'STUDY'},
                ],
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
            writeBoard() {
                if (this.isNotEmpty()) {
                    return this.$emit("submit", this.boardWrite);
                } else {
                    this.$store.commit('SET_SNACKBAR', {text: '빈칸을 모두 작성해주세요', color: 'error', location: 'top'});
                }
            },

        },
        destroyed() {
            this.$store.commit('CLEAR_BOARD_WRITE');
        }
    }
</script>

<style scoped>

</style>