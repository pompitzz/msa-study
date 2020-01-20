<template>
    <div class="fill-height">
        <v-container class="fill-height">
            <v-row align="center" class="fill-height mx-auto" justify="center">
                <v-card class="pa-3 mx- 3 boards-list" dark>
                    <v-card-title>
                        게시판
                        <v-spacer/>
                        <v-text-field
                                append-icon="mdi-magnify"
                                hide-details
                                label="검색"
                                v-model="pageRequest.title"
                        ></v-text-field>
                    </v-card-title>
                    <v-data-table
                            :headers="headers"
                            :items="boardList"
                            :items-per-page="10"
                            :loading="loadingState"
                            :sort-by.sync="sortby"
                            hide-default-footer
                    >

                        <template v-slot:item.title="{item}">
                            <div @click="moveToBoard(item)" class="my-cursor">
                                <v-btn @click="moveToBoard(item)" class="text-none px-1 my-td" text>
                                    {{titleLimit(item.title)}} <span class="ml-1" v-if="item.commentCount !== 0">({{item.commentCount}})</span>
                                </v-btn>
                            </div>
                        </template>
                        <template v-slot:item.author="{item}">
                            <div @click="moveToBoard(item)" class="my-cursor">
                                {{item.author}}
                            </div>
                        </template>
                        <template v-slot:item.viewsCount="{item}" class="text-center">
                            <div @click="moveToBoard(item)" class="my-cursor">
                                <span class="text-center mx-3">{{item.viewsCount}}</span>
                            </div>
                        </template>
                        <template v-slot:item.lastModifiedDate="{item}" class="text-center">
                            <div @click="moveToBoard(item)" class="my-cursor">
                                {{item.lastModifiedDate | moment('YYYY-MM-DD')}}
                            </div>
                        </template>
                    </v-data-table>
                    <div class="text-right">
                        <v-btn @click="moveToWritePage" class="ma-3"
                        >글쓰기
                        </v-btn>
                    </div>
                    <v-row>
                        <v-pagination
                                v-model="pageInfo.number"
                                :length="pageInfo.totalPages"
                                :total-visible="8"
                                @input="next"
                        ></v-pagination>
                    </v-row>
                </v-card>
            </v-row>
        </v-container>
        <Modal @pause="modalEvent"/>
    </div>
</template>
<script>
    import {router} from "../routes/route";
    import Modal from "../components/Modal";

    export default {
        name: "BoardWrite",
        components: {
            Modal
        },
        data() {
            return {
                headers: [
                    {text: '제목', value: 'title', align: 'left'},
                    {text: '작성자', value: 'author', sortable: false},
                    {text: '조회수', value: 'viewsCount'},
                    {text: '최근 수정일', value: 'lastModifiedDate'},
                ],
                pageRequest: {
                    page: 0,
                    sort: 'id,DESC',
                    title: '',
                },
                articleInfo: {
                    id: '',
                    href: '',
                },
                currentPage: Number,
                sortby: [],
                lastSortBy: '',
                timeout: null,
            }
        },
        methods: {
            next(page) {
                this.pageRequest.page = page - 1;
                this.$store.dispatch('QUERY_BOARDS_BYTITLE', this.pageRequest);
            },
            moveToBoard(board) {
                this.articleInfo.id = board.id;
                this.articleInfo.href = board._links.self.href;
                this.$store.commit('START_SPINNER');
                this.$store.dispatch('COUNT_MOVE_TO_ARTICLE', this.articleInfo);
            },
            titleLimit(title) {
                return title.length > 22 ? title.substring(0, 20) + '...' : title;
            },
            moveToWritePage() {
                if (localStorage.getItem('access_token') === null) {
                    this.$store.commit('OPEN_MODAL', {
                        title: '로그인이 필요합니다.',
                        content: `로그인을 한 사용자만 게시글 작성이 가능합니다.`,
                        option1: '닫기',
                    })
                } else {
                    router.push('/board-write');
                }

            },
            queryBoardWithSortBy() {
                if (this.sortby.length !== 0) {
                    this.lastSortby = this.sortby[0];
                    this.pageRequest.sort = `${this.lastSortby},DESC`;
                    this.$store.dispatch('QUERY_BOARDS_BYTITLE', this.pageRequest);
                } else {
                    this.pageRequest.sort = `${this.lastSortby},ASC`;
                    this.$store.dispatch('QUERY_BOARDS_BYTITLE', this.pageRequest);
                }
            },
            serachBoards() {
                this.$store.dispatch('QUERY_BOARDS_BYTITLE', this.pageRequest);
            },
            modalEvent() {
                this.$store.commit('CLOSE_MODAL');
                router.push(`/board/${this.articleInfo.id}`);
            },
        },

        computed: {
            loadingState() {
                return this.$store.state.common.loadingState;
            },
            boardList() {
                return this.$store.state.board.boardList;
            },
            pageInfo() {
                return this.$store.state.board.pageInfo;
            },
        },
        watch: {
            sortby: {
                handler() {
                    this.queryBoardWithSortBy();
                }
            },
            'pageRequest.title': {
                handler() {
                    clearTimeout(this.timeout);
                    this.timeout = setTimeout(() => {
                        this.serachBoards()
                    }, 800);
                }
            }
        },
        created() {
            this.$store.dispatch('QUERY_BOARDS_BYTITLE', this.pageRequest);
        },
    }
</script>

<style scoped>
    .boards-list {
        width: 100% !important;
    }

    .my-td {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }

    .my-cursor {
        cursor: pointer;
    }
</style>