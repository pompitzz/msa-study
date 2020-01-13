<template>
    <div>
        <v-row align="center" class="theme--dark" justify="space-around">
            <v-col cols="12">
                <v-select
                        :items="items"
                        v-model="model"
                >
                </v-select>
            </v-col>
        </v-row>
        <v-container>
            <h1>This is Main Page</h1>
            <div>
                <v-row>
                    <v-col class="py-0" cols="12" lg="6">
                        <v-card class="my-2 pa-3" dark>
                            <div class="ma-3">
                                <div>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 px-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0 px-0 " text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3 mb-0">좋은 정보 감사합니다. 좋은 정보 감사합니다.좋은 정보 감사합니다.좋은 정보 감사합니다.다음에 또 봬여</p>
                                <div class="mt-2 text-right grey--text "><span class="elevation-2 pa-2"
                                                                               style="font-size: 13px">더보기</span></div>
                            </div>
                            <div :style="`max-width: ${two}%`" class="ma-3 ml-auto">
                                <div>
                                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0" text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                                <div class="mt-2 text-right grey--text "><span class="elevation-2 pa-2"
                                                                               style="font-size: 13px">더보기</span></div>
                            </div>
                            <div :style="`max-width: ${three}%`" class="ma-3 ml-auto">
                                <div>
                                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0" text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                            </div>
                            <div :style="`max-width: ${three}%`" class="ma-3 ml-auto">
                                <div>
                                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0" text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                            </div>
                            <div :style="`max-width: ${four}%`" class="ma-3 ml-auto">
                                <div>
                                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0" text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                            </div>

                            <div :style="`max-width: ${two}%`" class="ma-3 ml-auto">
                                <div>
                                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0" text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                            </div>
                            <div :style="`max-width: ${three}%`" class="ma-3 ml-auto">
                                <div>
                                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0" text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                            </div>
                        </v-card>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col class="py-0" cols="12" lg="6">
                        <v-card class="my-2 pa-3" dark>
                            <div class="ma-3">
                                <div>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 px-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0 px-0 " text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3">좋은 정보 감사합니다. 좋은 정보 감사합니다.좋은 정보 감사합니다.좋은 정보 감사합니다.다음에 또 봬여</p>
                            </div>
                            <div :style="`max-width: ${two}%`" class="ma-3 ml-auto">
                                <div>
                                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0" text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                            </div>
                            <div :style="`max-width: ${three}%`" class="ma-3 ml-auto">
                                <div>
                                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0" text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                            </div>
                            <div :style="`max-width: ${three}%`" class="ma-3 ml-auto">
                                <div>
                                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0" text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                            </div>
                            <div :style="`max-width: ${four}%`" class="ma-3 ml-auto">
                                <div>
                                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0" text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                            </div>

                            <div :style="`max-width: ${two}%`" class="ma-3 ml-auto">
                                <div>
                                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0" text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                            </div>
                            <div :style="`max-width: ${three}%`" class="ma-3 ml-auto">
                                <div>
                                    <v-icon class="white--text mb-2">mdi-subdirectory-arrow-right</v-icon>
                                    <v-icon class="mb-2 mr-3">mdi-account</v-icon>
                                    <h4 class="d-inline-block">이 동 명 <span class="mx-1"/> 2018-09-91</h4>
                                    <v-btn class="float-right ma-0 ml-0" text>삭제</v-btn>
                                    <v-btn class="float-right ma-0" text>수정</v-btn>
                                </div>
                                <hr/>
                                <p class="mt-3">좋은 정보 감사합니다. 다음에 또 봬여</p>
                            </div>
                        </v-card>
                    </v-col>
                </v-row>

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
        data() {
            return {
                dialog: false,
                two: 90,
                three: 85,
                four: 80,
                items: ['Foo', 'Bar', 'Fizz', 'Buzz'],
                model: 'Foo',
            }
        },
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