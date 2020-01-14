<template>
    <div v-if="commentArr !== null">
        <div :key="comment.commentId" :style="`max-width: ${getWidth(comment.depth)}%`" class="ml-auto"
             v-for="(comment, index) in commentArr">
            <div v-if="comment.depth < 5">
                <div>
                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                    <h4 class="d-inline-block">{{comment.name}} <span class="mx-1"/>작성일 : {{comment.createDate |
                        moment('YYYY-MM-DD')}}</h4>
                    <v-btn @click="deleteComment(comment.commentId)"
                           v-if="isSameMember(comment.name)"
                           class="float-right ma-0 px-0 ml-0" text>삭제</v-btn>
                    <v-btn @click="editComment(index, comment)"
                           v-if="isSameMember(comment.name)"
                           class="float-right ma-0 px-0 " text>수정</v-btn>
                </div>
                <hr/>
                <p class="mt-3 mb-0">{{comment.content}}</p>
                <div class="my-2 text-right grey--text"
                >
                    <v-btn @click="createCommentofComment(comment.commentId)" class="elevation-2" small
                           style="font-size: 13px"
                           text>댓글 작성
                    </v-btn>
                </div>
                <CommentList :commentArr="comment.childrenResponseDto"
                />
            </div>
        </div>
        <Comment
                @modify="successModifyComment"
                @submit="successSaveComment"
        />
    </div>
</template>

<script>
    import Comment from "./Comment";

    export default {
        name: "CommentList",
        components: {
            Comment
        },
        props: ['commentArr'],
        computed: {
            pageRequest() {
                return this.$store.state.board.pageRequest;
            }
        },
        methods: {
            getWidth(depth) {
                return String(100 - (3 * depth));
            },
            getReverseArray(array) {
                if (array !== null) {
                    return array.reverse();
                }

                return null;
            },
            querySameParentComments(comment) {
                let page;
                if (comment.page === undefined) {
                    if (comment.childrenResponseDto === null) {
                        page = 0;
                    } else {
                        page = 1;
                    }
                } else {
                    page = comment.page;
                }

                this.$store.dispatch('QUERY_SAME_PARENT_COMMENTS', {
                    parentId: comment.commentId,
                })
                    .then(res => {
                        comment.isMore = !res.last;
                        comment.page = res.number + 1;
                        let content = res.content.reverse();
                        if (comment.childrenResponseDto === null) {
                            comment.childrenResponseDto = content;
                        } else {
                            comment.childrenResponseDto = content.concat(comment.childrenResponseDto);
                        }
                    })
                    .catch(() => {
                        this.$store.commit('OPEN_MODAL', {
                            title: '댓글 더보기 실패',
                            content: '다시 한번 더 시도해주세요.',
                            option: '닫기',
                        });
                    });
            },
            editComment(index, comment) {
                this.$store.commit('OPEN_COMMENT_MODAL', {
                    id: comment.commentId,
                    parentId: '',
                    parentName: '',
                    content: comment.content,
                    boardId: this.$store.getters.getBoardId,
                    index: index,
                })
            },
            successSaveComment() {
                this.$store.dispatch('QUERY_BOARD', this.pageRequest);
            },
            successModifyComment() {
                console.log('aa', this.pageRequest);
                this.$store.dispatch('QUERY_BOARD', this.pageRequest);
            },
            deleteComment(commentId) {
                this.$store.dispatch('DELETE_COMMENT', commentId).then(() => this.$store.dispatch('QUERY_BOARD', this.pageRequest))
            },
            createCommentofComment(parentId) {
                if (localStorage.getItem('email') == null) {
                    this.$store.commit('SET_SNACKBAR', {
                        text: '댓글 작성을 위해 로그인하셔야 합니다.',
                        color: 'error',
                        location: 'top'
                    });
                } else {
                    this.$store.commit('OPEN_COMMENT_MODAL', {
                        id: '',
                        parentId: parentId,
                        parentName: '',
                        content: '',
                        boardId: this.$store.getters.getBoardId,
                    });
                }
            },
            isSameMember(name) {
                return name === localStorage.getItem('name');
            },
        },
    }
</script>

<style scoped>

</style>