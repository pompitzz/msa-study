<template>
    <div class="login-container">
        <div class="login text-center shadow-xl">
            <h3 class="mb-4 font-weight-bold">로그인</h3>

            <div>
                <mdb-input label="E-mail" type="text" v-model="email"/>
                <mdb-input label="Password" type="password" v-model="password"/>

                <mdb-btn @click="loginRequest" class="w-75 font-weight-bold p-2" outline="default">LOGIN</mdb-btn>

                <p class="mt-3 mb-0">아직 회원이 아니신가요?
                    <router-link class="font-weight-bold text-danger"
                                 to="/register">회원가입
                    </router-link>
                </p>
            </div>
        </div>
        <Modal>
            <slot slot="title"> {{modalTitle}}</slot>
            <slot slot="description"> {{modalDescription}}</slot>
            <slot slot="option">{{modalOption}}</slot>
        </Modal>

    </div>

</template>

<script>
    import Modal from "./common/Modal";
    import {mapActions, mapMutations, mapState} from 'vuex'
    import {createModalTexts} from "../common";
    import {mdbInput, mdbBtn} from 'mdbvue'

    export default {
        name: "Login",
        data() {
            return {
                email: '',
                password: '',
            }
        },
        computed: {
            ...mapState(['modal', 'modalTitle', 'modalDescription', 'modalOption'])
        },
        components: {
            Modal,
            mdbInput,
            mdbBtn
        },
        methods: {
            ...mapMutations(['CLOSE_MODAL', 'SET_MODAL_TEXTS']),
            ...mapActions(['REQUEST_LOGIN']),
            loginRequest() {
                if (this.email !== '' && this.password !== '') {
                    this.$store.dispatch('REQUEST_LOGIN', {
                        email: this.email,
                        password: this.password,
                    })
                } else {
                    this.SET_MODAL_TEXTS(createModalTexts(
                        '로그인 불가',
                        '아이디와 비밀번호를 입력해주세요.',
                        'CLOSE'
                    ));
                }
            },
        }

    }
</script>

<style scoped>
    .login-container {
        background-color: #63b47a;
        min-height: 100vh;
    }

    .login {
        width: 360px;
        background-color: white;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        border-radius: 8px;
        border: white 1px solid;
        padding: 40px;
    }

    .my-input {
        border: 1px #d6d6d6 solid;
        border-radius: 4px;
    }

</style>