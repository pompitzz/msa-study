<template>
    <div>
        <v-container>
            <h1 class="my-5 text-center">{{article.title}}</h1>
            <p>작성자 : {{article.author}} <span class="mx-5">|</span> 최근 작성일 : {{article.lastModifiedDate |
                moment('YYYY-MM-DD')}}
            </p>
            <div class="mb-3">
                <v-btn outlined dark color="grey" small class="mx-2">수정</v-btn>
                <v-btn outlined dark color="grey" small class="mx-2">삭제</v-btn>
            </div>
            <v-card class="pa-3" light>
                <viewer :value="article.content" dark="false"/>
            </v-card>
        </v-container>
    </div>
</template>

<script>
    import Viewer from '@toast-ui/vue-editor/src/Viewer.vue'
    import {mapState, mapActions} from 'vuex'

    export default {
        name: "Article",
        data() {
            return {
                article: {},
            }
        },
        components: {
            Viewer
        },
        computed: {
            ...mapState(['articleUrl'])
        },
        methods: {
            ...mapActions(['QUERY_ARTICLE'])
        },
        created() {
            console.log('article', this.articleUrl);
            this.QUERY_ARTICLE(this.articleUrl).then(res => this.article = res);
            console.log('arararar', this.article)
        }
    }
</script>

<style scoped>

</style>