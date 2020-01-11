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
                            :items="boards"
                            :items-per-page="10"
                            :loading="loadingState"
                            :sort-by.sync="sortby"
                            hide-default-footer
                    >

                        <template v-slot:item.title="{item}">
                            <v-btn @click="moveToArticle(item)" class="text-none px-1 my-td" text>
                                {{titleLimit(item.title)}}
                            </v-btn>
                        </template>
                        <template v-slot:item.viewsCount="{item}" class="text-center">
                            <span class="text-center mx-3">{{item.viewsCount}}</span>
                        </template>
                        <template v-slot:item.lastModifiedDate="{item}" class="text-center">
                            {{item.lastModifiedDate | moment('YYYY-MM-DD')}}
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
                                :length="pageItems.totalPages"
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
    import {mapActions, mapState, mapMutations} from 'vuex';
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
            ...mapActions(['COUNT_MOVE_TO_ARTICLE', 'QUERY_BOARDS_BYTITLE']),
            ...mapMutations(['CLOSE_MODAL', 'OPEN_MODAL']),
            next(page) {
                this.pageRequest.page = page - 1;
                this.QUERY_BOARDS_BYTITLE(this.pageRequest);
            },
            moveToArticle(board) {
                this.articleInfo.id = board.id;
                this.articleInfo.href = board._links.self.href;
                this.COUNT_MOVE_TO_ARTICLE(this.articleInfo);
            },
            titleLimit(title) {
                return title.length > 70 ? title.substring(0, 40) + '...' : title;
            },
            moveToWritePage() {
                if (localStorage.getItem('email') === null) {
                    this.OPEN_MODAL({
                        title: '로그인이 필요합니다.',
                        content: `로그인을 한 사용자만 게시글 작성이 가능합니다.`,
                        option: '닫기',
                    })
                } else {
                    router.push('/board-write');
                }

            },
            queryBoardWithSortBy() {
                if (this.sortby.length !== 0) {
                    this.lastSortby = this.sortby[0];
                    this.pageRequest.sort = `${this.lastSortby},DESC`;
                    this.QUERY_BOARDS_BYTITLE(this.pageRequest);
                } else {
                    this.pageRequest.sort = `${this.lastSortby},ASC`;
                    this.QUERY_BOARDS_BYTITLE(this.pageRequest);
                }
            },
            serachBoards() {
                this.QUERY_BOARDS_BYTITLE(this.pageRequest);
            },
            modalEvent() {
                this.CLOSE_MODAL();
                router.push(`/article/${this.articleInfo.id}`);
            }

        },
        created() {
            this.QUERY_BOARDS_BYTITLE(this.pageRequest);
        },
        computed: {
            ...mapState(['pageInfo', 'boardList', 'loadingState']),
            boards() {
                return this.boardList;
            },
            pageItems() {
                return this.pageInfo;
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
        }
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
</style>