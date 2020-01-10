<template>
    <div class="fill-height">
        <v-container class="fill-height">
            <v-row align="center" class="fill-height mx-auto" justify="center">
                <v-card class="pa-3 mx- 3 boards-list" dark>
                    <v-card-title>
                        SAMPLE
                        <v-spacer/>
                        <v-text-field
                                append-icon="mdi-file-document-box-search-outline"
                                hide-details
                                label="검색"
                                single-line
                                v-model="search"
                        ></v-text-field>
                    </v-card-title>
                    <v-data-table
                            :headers="headers"
                            :items="boards"
                            :search="search"
                            :loading="false"
                            hide-default-footer
                    >
                        <template v-slot:item.title="{item}">
                            <v-btn text class="text-none px-1 my-td"  @click="moveToArticle(item)">{{titleLimit(item.title)}}</v-btn>
                        </template>
                        <template v-slot:item.viewsCount="{item}" class="text-center">
                            <span class="text-center mx-3">{{item.viewsCount}}</span>
                        </template>
                        <template v-slot:item.lastModifiedDate="{item}" class="text-center">
                            {{item.lastModifiedDate | moment('YYYY-MM-DD')}}
                        </template>
                    </v-data-table>
                    <div class="text-right">
                        <v-btn class="ma-3" to="/board-write"
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
    </div>
</template>
<script>
    import {mapActions, mapState, mapMutations} from 'vuex';
    import moment from 'vue-moment'
    export default {
        name: "BoardWrite",
        data() {
            return {
                search: '',
                headers: [
                    {text: '제목', value: 'title', align: 'left'},
                    {text: '작성자', value: 'author'},
                    {text: '조회수', value: 'viewsCount'},
                    {text: '최근 수정일', value: 'lastModifiedDate'},
                ],
                pageRequest:{
                    page: 0,
                    sort: 'id,DESC'
                },
                currentPage: Number,
            }
        },
        methods:{
            ...mapActions(['QUERY_BOARDS']),
            ...mapMutations(['MOVE_TO_ARTICLE']),
            next(page){
                this.pageRequest.page = page - 1;
                this.QUERY_BOARDS(this.pageRequest);
            },
            moveToArticle(board){
                console.log('item', board);
                const articleInfo = {
                    id:  board.id,
                    href: board._links.self.href,
                };

                this.MOVE_TO_ARTICLE(articleInfo);
            },
            titleLimit(title){
                return title.length > 40 ? title.substring(0, 40) + '...' : title;
                console.log(title);
            }

        },
        created() {
            this.QUERY_BOARDS(this.pageRequest);
        },
        computed:{
            ...mapState(['pageInfo', 'boardList']),
            boards(){
                return this.boardList;
            },
            pageItems(){
                return this.pageInfo;
            },
        }
    }
</script>

<style scoped>
    .boards-list {
        width: 100% !important;
    }
    .my-td{
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }
</style>