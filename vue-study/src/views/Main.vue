<template>
    <div>
        <v-container>
            <h1>This is Main Page</h1>
            <div style="width: 600px">
                <v-card class="my-5 pa-5" dark width="600">
                    <div class="ma-3">
                        <div>
                            <v-icon class="mb-2 mr-3" large>mdi-account</v-icon>
                            <h3 class="d-inline-block">이 동 명 <span class="mx-1"/></h3> 2018-09-91
                            <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                            <v-btn class="float-right ma-0" text>수정</v-btn>
                        </div>
                        <hr/>
                        <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                    </div>
                </v-card>
                <div>

                    <v-card class="my-5 pa-5" dark style="margin-left: 50px" width="550">
                        <v-icon class="white--text">mdi-subdirectory-arrow-right</v-icon>
                        <div class="ma-3">
                            <div>
                                <v-icon class="mb-2 mr-3" large>mdi-account</v-icon>
                                <h3 class="d-inline-block">이 동 명 <span class="mx-1"/></h3> 2018-09-91
                                <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                <v-btn class="float-right ma-0" text>수정</v-btn>
                            </div>
                            <hr/>
                            <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                        </div>
                    </v-card>
                </div>
                <div>
                    <v-card class="my-5 pa-5" dark style="margin-left: 100px" width="500">
                        <v-icon class="white--text">mdi-subdirectory-arrow-right</v-icon>

                        <div class="ma-3">
                            <div>
                                <v-icon class="mb-2 mr-3" large>mdi-account</v-icon>
                                <h3 class="d-inline-block">이 동 명 <span class="mx-1"/></h3> 2018-09-91
                                <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                <v-btn class="float-right ma-0" text>수정</v-btn>
                            </div>
                            <hr/>
                            <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                        </div>
                    </v-card>
                </div>
            </div>
        </v-container>
        <Modal @pass="modalEvnet"/>
    </div>
</template>

<script>
    import {mapActions, mapMutations} from 'vuex'
    import Modal from "../components/Modal";

    export default {
        name: "Main",
        components: {Modal},
        methods: {
            ...mapActions(['QUERY_MEMBER']),
            ...mapMutations(['CLOSE_MODAL']),
            queryMember() {
                let email = localStorage.getItem('email');
                if (localStorage.getItem('name') === null && email !== null) {
                    this.QUERY_MEMBER(email);
                }
            },
            modalEvnet() {
                this.queryMember();
            }
        },
        created() {
            this.queryMember();
        }
    }
</script>

<style scoped>
    code {
        box-shadow: none !important;
    }
</style>