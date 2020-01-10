<template>
    <div>
        <v-select
                :items="items"
                :label="label"
                @input="setBoardType"
                dark
                dense
                outlined
                style="width: 145px; height:57px;"
        ></v-select>

        <v-text-field class="mt-5" dark
                      height="50" label="제목" outlined
                      placeholder="제목을 입력하세요"
                      v-model="editor.title"
        ></v-text-field>
        <editor height="500px"
                v-model="editor.content"/>
        <v-btn @click="writeBoard"
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
                    {text: '공지사항'},
                    {text: '자유게시판'},
                    {text: '공부방'},
                ],
                editor: this.board,
                label: '',
            }
        },
        components: {
            Editor
        },
        props: ['board'],
        methods: {
            isNotEmpty() {
                return !(this.editor.title === '' || this.editor.content === '' || this.editor.boardType === '');
            },
            setBoardType(value) {
                if (value === '공부방') {
                    this.editor.boardType = 'STUDY';
                } else if (value === '자유게시판') {
                    this.editor.boardType = 'FREE'
                } else if (value === '공지사항') {
                    this.editor.boardType = 'NOTICE'
                }
            },
            writeBoard() {
                if (this.isNotEmpty()) {
                    this.$emit('pass', this.editor);
                }
            }
        },
        created() {
            if (this.board.boardType === '') {
                this.label = '카테고리';
            } else {
                this.label = this.board.boardType;
            }
        }
    }
</script>

<style scoped>

</style>