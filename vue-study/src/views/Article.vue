<template>
    <div>
        <v-container>
            <h1 class="my-5 text-center">{{article.title}}</h1>
            <p>작성자 : <span class="ml-2">{{article.author}}</span>
                <span class="mx-5">|</span> 최근 작성일 : <span class="ml-2">{{article.lastModifiedDate |
                    moment('YYYY-MM-DD')}}</span>
            </p>
            <div class="mb-3">
                <v-btn @click="modifyBoard" class="mx-2" color="grey" dark outlined small v-if="isAuthor">수정</v-btn>
                <v-btn @click="deleteBoard" class="mx-2" color="grey" dark outlined small v-if="isAuthor">삭제</v-btn>
            </div>
            <v-card class="pa-5" light>
                <viewer :value="article.content" dark="false"/>
            </v-card>
            <!--            <v-card class="my-5 pa-1 mx-auto" dark width="600">-->
            <!--                <div class="ma-3">-->
            <!--                    <div>-->
            <!--                        <v-icon class="mr-3 mb-2">mdi-account</v-icon>-->
            <!--                        <h3 class="d-inline-block ma-0">이 동 명 <span class="mx-1"/></h3> 2018-09-91-->
            <!--                        <v-btn class="float-right mb-2 ml-0" text>삭제</v-btn>-->
            <!--                        <v-btn class="float-right mb-2" text>수정</v-btn>-->
            <!--                    </div>-->
            <!--                    <hr/>-->
            <!--                    <p style="font-size: 15px" class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>-->
            <!--                </div>-->
            <!--            </v-card>-->
            <!--            <v-card class="my-5 pa-1 mx-auto" dark width="600">-->
            <!--                <div class="ma-3">-->
            <!--                    <div>-->
            <!--                        <v-icon class="mr-3 mb-2">mdi-account</v-icon>-->
            <!--                        <h3 class="d-inline-block ma-0">이 동 명 <span class="mx-1"/></h3> 2018-09-91-->
            <!--                        <v-btn class="float-right mb-2 ml-0" text>삭제</v-btn>-->
            <!--                        <v-btn class="float-right mb-2" text>수정</v-btn>-->
            <!--                    </div>-->
            <!--                    <hr/>-->
            <!--                    <p style="font-size: 15px" class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>-->
            <!--                </div>-->
            <!--            </v-card>-->
            <!--            <v-card class="my-5 pa-1 mx-auto" dark width="600">-->
            <!--                <div class="ma-3">-->
            <!--                    <div>-->
            <!--                        <v-icon class="mr-3 mb-2">mdi-account</v-icon>-->
            <!--                        <h3 class="d-inline-block ma-0">이 동 명 <span class="mx-1"/></h3> 2018-09-91-->
            <!--                        <v-btn class="float-right mb-2 ml-0" text>삭제</v-btn>-->
            <!--                        <v-btn class="float-right mb-2" text>수정</v-btn>-->
            <!--                    </div>-->
            <!--                    <hr/>-->
            <!--                    <p style="font-size: 15px" class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>-->
            <!--                </div>-->
            <!--            </v-card>-->

        </v-container>
        <Modal/>
    </div>
</template>

<script>
    import Viewer from '@toast-ui/vue-editor/src/Viewer.vue'
    import Modal from "../components/Modal";
    import {mapState, mapActions, mapMutations} from 'vuex'

    export default {
        name: "Article",
        data() {
            return {
                article: {
                    title: '',
                    author: '',
                    content: '',
                    email: '',
                    lastModifiedDate: '',
                },
                isAuthor: false,
            }
        },
        components: {
            Viewer,
            Modal
        },
        computed: {
            ...mapState(['articleUrl'])
        },
        methods: {
            ...mapActions(['QUERY_ARTICLE', 'VALIDATE_MODIFY_MEMBER', 'VALIDATE_DELETE_MEMBER']),
            ...mapMutations(['MODIFY_ARTICLE', 'CLOSE_MODAL', 'OPEN_MODAL']),
            modifyBoard() {
                let email = localStorage.getItem("email");
                if (email !== null) {
                    this.VALIDATE_MODIFY_MEMBER({id: this.$route.params.id, email: email})

                } else {
                    this.openModal();
                }
            },
            deleteBoard() {
                let email = localStorage.getItem("email");
                if (email !== null) {
                    this.VALIDATE_DELETE_MEMBER({id: this.$route.params.id, email: email})

                } else {
                    this.openModal();
                }
            },
            openModal() {
                this.OPEN_MODAL({
                    title: '인증되지 않은 사용자',
                    content: '로그인 후 게시자인지 확인이 필요합니다.',
                    option: '닫기'
                })
            }
        },
        created() {
            this.QUERY_ARTICLE(this.$route.params.id).then(res => {
                this.article = res;
                this.isAuthor = this.article.email === localStorage.getItem('email');
            });
        }
    }
</script>

<style scoped>

</style>