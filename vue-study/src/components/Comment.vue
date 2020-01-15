<template>
    <v-row class="comment-dialog" justify="center">
        <v-dialog max-width="600px" persistent v-model="dialog">
            <v-card class="my-0">
                <v-card-title>
                    <span class="headline" v-if="comment.id === ''">댓글 달기</span>
                    <span class="headline" v-else>댓글 수정</span>
                    <!--                    <span v-else class="headline">{{comment.parentName}}님에게 댓글 달기</span>-->
                </v-card-title>
                <v-card-text class="py-0">
                    <v-container class="pb-0">
                        <v-textarea
                                @keyup.enter="submit"
                                label="댓글을 작성해주세요."
                                v-if="comment.id ===''"
                                outlined
                                v-model="comment.content"
                        ></v-textarea>
                        <v-textarea
                                @keyup.enter="modify"
                                label="댓글을 작성해주세요."
                                outlined
                                v-else
                                v-model="comment.content"
                        ></v-textarea>
                    </v-container>
                </v-card-text>
                <v-card-actions class="pt-0">
                    <v-spacer></v-spacer>
                    <v-btn @click="close" class="mb-2 mr-3" color="blue darken-1" outlined>닫기</v-btn>
                    <v-btn @click="submit" class="mb-2 mr-3" color="blue darken-1" outlined v-if="comment.id === ''">댓글
                        달기
                    </v-btn>
                    <v-btn @click="modify" class="mb-2 mr-3" color="blue darken-1" outlined v-else>수정</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-row>
</template>

<script>
    export default {
        methods: {
            close() {
                this.$store.commit('CLOSE_COMMENT_MODAL')
            },
            submit() {
                this.$store.dispatch('SAVE_COMMENT', {
                    boardId: this.comment.boardId,
                    parentId: this.comment.parentId,
                    content: this.comment.content,
                }).then(() => this.$emit('submit'));
            },
            modify() {
                this.$store.dispatch('MODIFY_COMMENT', {
                    id: this.comment.id,
                    content: this.comment.content,
                }).then(() => this.$emit('modify'))
            },
            test() {
                this.$emit('test');
            },
        },
        computed: {
            comment() {
                return this.$store.state.comment.comment;
            },
            dialog() {
                return this.$store.state.comment.dialog;
            }

        }
    }
</script>

<style scoped>

</style>