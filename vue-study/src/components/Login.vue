<template>
    <div class="login-container">
        <div class="login text-center shadow-xl">
            <h3 class="mb-4 font-weight-bold">로그인</h3>

            <div>
                <input class="d-block w-100 p-3 mb-3 my-input" placeholder="E-mail" type="text" v-model="email">
                <input class="d-block w-100 p-3 mb-3 my-input" placeholder="Password" type="password"
                       v-model="password">
                <a @click="loginRequest"
                   class="btn btn-outline-default waves-effect w-75 font-weight-bold p-2">LOGIN</a>
                <p class="mt-3 mb-0">아직 회원이 아니신가요?
                    <router-link class="font-weight-bold text-danger"
                                 to="/register">회원가입
                    </router-link>
                </p>
            </div>
        </div>
        <Modal @close="CLOSE_MODAL" v-if="showModal">
            <h5 slot="title"> {{modalTitle}} </h5>
            <p slot="description"> {{modalDescription}} </p>
            <div @click="CLOSE_MODAL" slot="close">{{modalOption}}</div>
        </Modal>

    </div>

</template>

<script>
    import Modal from "./common/Modal";
    import {mapActions, mapMutations, mapState} from 'vuex'
    import {createInfo} from "../common";

    export default {
        name: "Login",
        data() {
            return {
                email: '',
                password: '',
            }
        },
        computed: {
            ...mapState(['showModal', 'modalTitle', 'modalDescription', 'modalOption'])
        },
        components: {
            Modal
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
                    this.SET_MODAL_TEXTS(createInfo(
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