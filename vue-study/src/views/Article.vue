<template>
    <div>
        <v-container>
            <h1 class="my-5 text-center">{{article.title}}</h1>
            <p>작성자 : <span class="ml-2">{{article.author}}</span>
                <span class="mx-5">|</span> 최근 작성일 : <span class="ml-2">{{article.lastModifiedDate |
                    moment('YYYY-MM-DD')}}</span>
            </p>
            <div class="mb-3">
                <v-btn @click="modifyBoard" class="mx-2" color="grey" dark outlined small>수정</v-btn>
                <v-btn @click="deleteBoard" class="mx-2" color="grey" dark outlined small>삭제</v-btn>
            </div>
            <v-card class="pa-3" light>
                <viewer :value="article.content" dark="false"/>
            </v-card>
        </v-container>
        <Modal @pass="modalEvent"/>
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
                article: {},
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
            modalEvent() {
                this.CLOSE_MODAL();
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
            this.QUERY_ARTICLE(this.$route.params.id).then(res => this.article = res);
        }
    }
</script>

<style scoped>

</style>