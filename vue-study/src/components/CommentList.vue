<template>
    <div v-if="commentList !== null">
        <div :key="comment.commentId" :style="`max-width: ${getWidth(comment.depth)}%`" class="ml-auto"
             v-for="(comment, index) in commentList">
            <div v-if="comment.depth < 5">
                <div>
                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                    <h4 class="d-inline-block">{{comment.name}} <span class="mx-1"/>작성일 : {{comment.createDate |
                        moment('YYYY-MM-DD')}}</h4>
                    <v-btn class="float-right ma-0 px-0 ml-0" text>삭제</v-btn>
                    <v-btn @click="editComment(index, comment)" class="float-right ma-0 px-0 " text>수정</v-btn>
                </div>
                <hr/>
                <p class="mt-3">{{comment.content}}</p>
                <div class="my-2 text-right grey--text"
                     v-if="comment.isMore"
                >
                    <v-btn @click="querySameParentComments(comment)" class="elevation-2" small style="font-size: 13px"
                           text>더보기
                    </v-btn>
                </div>
                <CommentList :commentList="comment.childrenResponseDto"/>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "CommentList",
        props: ['commentList', 'boardId'],
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
                    commentPageReqeust: {
                        page: page,
                        size: 2,
                        sort: 'createDate,DESC',
                    }
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
        },
    }
</script>

<style scoped>

</style>