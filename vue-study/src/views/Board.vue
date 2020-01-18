<template>
    <div ref="container">
        <v-container>
            <h1 class="my-5 text-center">{{board.title}}</h1>
            <p>작성자 : <span class="ml-2">{{board.author}}</span>
                <span class="mx-5">|</span> 최근 작성일 : <span class="ml-2">{{board.lastModifiedDate |
                    moment('YYYY-MM-DD')}}</span>
            </p>
            <div class="mb-3" v-if="hasToken">
                <v-btn @click="modifyBoard" class="mx-2" color="grey" dark outlined small>수정</v-btn>
                <v-btn @click="openModal('게시글 삭제')" class="mx-2" color="grey" dark outlined small>삭제</v-btn>
            </div>
            <v-card class="pa-5" light>
                <viewer :value="board.content" dark="false"/>
            </v-card>

            <div class="mt-2 text-center" v-if="!isLast">
                <v-btn @click="requestMoreComments" class="mx-3"> 이전 댓글 더 보기</v-btn>
            </div>

            <v-row v-if="commentList">
                <v-col class="py-0" cols="12" sm="8" offset-sm="2">
                    <v-card :key="comment.commentId" class="my-2 pa-3" dark v-for="(comment, index) in commentList">
                        <div :style="`max-width: ${getWidth(comment.depth)}%`" class="ma-3">
                            <div>
                                <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                <h4 class="d-inline-block">{{comment.name}} <span class="mx-1"/>작성일 :
                                    {{comment.createDate | moment('YYYY-MM-DD')}}</h4>
                                <v-btn @click="openModal('댓글 삭제', comment.commentId)"
                                       class="float-right ma-0 px-0 ml-0"
                                       v-if="hasToken"
                                       text>
                                    삭제
                                </v-btn>
                                <v-btn @click="editComment(index, comment.content, comment.commentId)"
                                       class="float-right ma-0 px-0 "
                                       v-if="hasToken"
                                       text>수정
                                </v-btn>
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
                            <CommentList
                                    :commentArr="comment.childrenResponseDto"/>
                        </div>
                    </v-card>
                    <div class="mt-2 text-right">
                        <v-btn @click="createComment" class="mx-3">댓글 작성</v-btn>
                    </div>
                </v-col>
            </v-row>


        </v-container>
        <Modal @pass="deleteBoardOrComment"/>
        <Comment
                @submit="successSaveComment"
                @modify="successModifyComment"
        />
    </div>
</template>

<script>
    import Viewer from '@toast-ui/vue-editor/src/Viewer.vue'
    import Modal from "../components/Modal";
    import Comment from "../components/Comment";
    import CommentList from "../components/CommentList";

    export default {
        name: "Board",
        data() {
            return {
                commentId: '',
                title: '',
            }
        },
        components: {CommentList, Comment, Viewer, Modal},
        computed: {
            commentList() {
                return this.$store.state.comment.commentList;
            },
            board() {
                return this.$store.state.board.board;
            },
            isLast() {
                return this.$store.state.comment.isLast;
            },
            pageRequest() {
                return this.$store.state.board.pageRequest;
            },
            hasToken() {
                return this.$store.state.member.accessToken !== null;
            }
        },

        methods: {
            modifyBoard() {
                this.$store.dispatch('MODIFY_VALIDATE_BOARD', this.$route.params.id);
            },

            createComment() {
                if (localStorage.getItem('access_token') == null) {
                    this.$store.commit('SET_SNACKBAR', {
                        text: '댓글 작성을 위해 로그인하셔야 합니다.',
                        color: 'error',
                        location: 'top'
                    });
                } else {
                    this.$store.commit('OPEN_COMMENT_MODAL', {
                        id: '',
                        parentId: -1,
                        parentName: '',
                        content: '',
                        boardId: this.board.id,
                    });
                }
            },

            editComment(index, content, commentId) {
                this.$store.commit('OPEN_COMMENT_MODAL', {
                    id: commentId,
                    parentId: '',
                    parentName: '',
                    content: content,
                    boardId: this.board.id,
                })
            },
            successSaveComment() {
                console.log('successSaveComment 후 페이지 사이즈 증가');
                console.log(this.pageRequest.size);
                this.pageRequest.size++;
                console.log(this.pageRequest.size);
                this.$store.dispatch('QUERY_BOARD', this.pageRequest);
                window.scrollTo(0, document.body.scrollHeight || document.documentElement.scrollHeight);
            },
            successModifyComment() {
                this.$store.dispatch('QUERY_BOARD', this.pageRequest);
            },
            getWidth(depth) {
                return String(95 - (5 * depth));
            },
            requestMoreComments() {
                console.log('requestMoreComments 후 페이지 사이즈 증가');
                console.log(this.pageRequest.page);
                this.pageRequest.size = this.pageRequest.size + 3;
                console.log(this.pageRequest.page);
                this.$store.dispatch('QUERY_MORE_COMMENT', this.pageRequest);
            },
            querySameParentComments(comment) {
                console.log('commnet is ', comment);
                console.log('id is ', comment.commentId);
                let page = 1;
                if (comment.page !== undefined) {
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
                        console.log('response', res);
                        comment.isMore = !res.last;
                        comment.page = res.number + 1;
                        let content = res.content.reverse();
                        comment.childrenResponseDto = content.concat(comment.childrenResponseDto);
                        console.log('updated comment is ', comment);

                    })
                    .catch(() => {
                        this.$store.commit('OPEN_MODAL', {
                            title: '댓글 더보기 실패',
                            content: '다시 한번 더 시도해주세요.',
                            option1: '닫기',
                        });
                    });
            },
            getReverseArray(array) {
                if (array !== null) {
                    return array.reverse();
                }
                return null;
            },
            createCommentofComment(parentId) {
                if (localStorage.getItem('access_token') == null) {
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
                        boardId: this.board.id,
                    });
                }
            },
            deleteBoardOrComment() {
                console.log('deleteBoardOrComment');
                console.log(this.title);
                if (this.title === '게시글 삭제') {
                    this.$store.dispatch('DELETE_BOARD', this.$route.params.id);
                } else if (this.title === '댓글 삭제') {
                    this.$store.dispatch('DELETE_COMMENT', this.commentId)
                        .then(() => this.$store.dispatch('QUERY_BOARD', this.pageRequest))
                }
            },
            openModal(title, commentId) {
                this.title = title;
                console.log(this.title, commentId);
                if (!!commentId) this.commentId = commentId;
                this.$store.commit('OPEN_MODAL', {
                    title: title,
                    content: '정말로 삭제하시겠습니까?',
                    option1: '닫기',
                    option2: '삭제',
                });
            }
        },
        created() {
            this.$store.commit('SET_BOARD_ID', this.$route.params.id);
            this.$store.dispatch('QUERY_BOARD', this.pageRequest);
        }
    }
</script>

<style scoped>

</style>