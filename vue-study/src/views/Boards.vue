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
                    ></v-data-table>
                    <div class="text-right">
                        <v-btn class="ma-3" to="/article"
                        >뷰어 테스트
                        </v-btn>
                        <v-btn class="ma-3" to="/board-write"
                        >글쓰기
                        </v-btn>
                    </div>
                </v-card>
            </v-row>
        </v-container>
    </div>
</template>
<script>

    export default {
        name: "BoardWrite",
        data() {
            return {
                search: '',
                headers: [
                    {
                        text: '제목',
                        align: 'left',
                        sortable: false,
                        value: 'title',
                    },
                    {text: '작성자', value: 'author'},
                    {text: '조회수', value: 'count'},
                    {text: '날짜', value: 'date'},
                ],
                boards: [
                    {title: '알고르짐이란? ', author: '이동명', count: '33', date: '2019-01-01 11:11'},
                    {title: '알고르짐이란? ', author: '이동명', count: '33', date: '2019-01-01 11:11'},
                    {title: '알고르짐이란? ', author: '이동명', count: '33', date: '2019-01-01 11:11'},
                    {title: '알고르짐이란? ', author: '이동명', count: '33', date: '2019-01-01 11:11'},
                    {title: '알고르짐이란? ', author: '이동명', count: '33', date: '2019-01-01 11:11'},
                ],
            }
        },
    }
</script>

<style scoped>
    .boards-list {
        width: 100% !important;
    }
</style>