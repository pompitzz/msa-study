<template>
    <div class="fill-height box">
        <v-container>
            <h1 class="text-center">글쓰기</h1>
            <v-menu offset-y>
                <template v-slot:activator="{ on }">
                    <v-btn
                            color="grey"
                            outlined
                            v-on="on"
                    >
                        카테고리
                        <v-icon right>mdi-arrow-down</v-icon>
                    </v-btn>
                </template>
                <v-list>
                    <v-list-item
                            :key="index"
                            @click="board.category = item.title"
                            v-for="(item, index) in items"
                    >
                        <v-list-item-title>{{ item.title }}</v-list-item-title>
                    </v-list-item>
                </v-list>
            </v-menu>
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

    export default {
        name: "Boards",
        data() {
            return {
                items: [
                    {title: '잡담'},
                    {title: '공부방'},
                    {title: '취업'},
                ],
                board: {
                    title: '',
                    content: '',
                    category: '',
                }
            }
        },
        components: {
            Editor
        },
        methods: {
            writeBoard() {
                if (this.isNotEmpty()) {
                    console.log(this.board);
                }
            },
            isNotEmpty() {
                return !(this.board.title === '' || this.board.components === '' || this.board.category === '');
            }
        }
    }
</script>

<style scoped>
</style>