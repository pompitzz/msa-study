<template>
    <div class="register-container">

        <!-- Material form register -->
        <div class="register shadow-xl">

            <h3 class="font-weight-bold text-center py-3 mb-3">
                회 원 가 입
            </h3>

            <div class="card-body pt-0">

                <div class="text-center">
                    <!-- E-mail -->
                    <div class="md-form ">
                        <input class="form-control" id="res-email" placeholder="E-mail" type="email"
                               v-model="member.email">
                    </div>

                    <!-- Password -->
                    <div class="md-form">
                        <input class="form-control" id="password" placeholder="Password" type="password"
                               v-model="member.password">
                    </div>

                    <!-- Name -->
                    <div class="md-form">
                        <input class="form-control" id="name" placeholder="Name" type="text" v-model="member.name">
                    </div>

                    <!-- Sign up button -->
                    <button @click="join"
                            class="btn btn-outline-info btn-rounded my-4 waves-effect px-5 mx-auto font-weight-bold">
                        Sign in
                    </button>
                </div>

            </div>
        </div>

        <Modal @close="CLOSE_MODAL" v-if="showModal">
            <h5 slot="title"> {{modalTitle}} </h5>
            <p slot="description"> {{modalDescription}} </p>
            <div @click="CLOSE_MODAL" slot="close">
                <router-link class="text-white" to="/login" v-if="modalOption === '이동'">
                    {{modalOption}}
                </router-link>
                <div v-else>
                    {{modalOption}}
                </div>
            </div>
        </Modal>
    </div>
</template>

<script>
    import Modal from "./common/Modal";
    import {mapState, mapMutations, mapActions} from 'vuex'
    import {createInfo} from "../common";

    export default {
        name: "Resister",
        data() {
            return {
                member: {
                    email: '',
                    password: '',
                    name: '',
                    role: 'USER',
                },
            }
        },
        computed: {
            ...mapState([
                'showModal',
                'modalTitle',
                'modalDescription',
                'modalOption'
            ])
        },
        components: {
            Modal
        },
        methods: {
            ...mapMutations(['CLOSE_MODAL', 'SET_MODAL_TEXTS']),
            ...mapActions(['REQUEST_JOIN']),

            join() {
                if (isNotEmpty(this.member)) {
                    this.REQUEST_JOIN(this.member);

                } else {
                    this.SET_MODAL_TEXTS(createInfo(
                        '회원가입 불가',
                        '빈칸을 모두 작성해주세요',
                        'CLOSE'
                    ))
                }
            }
        }
    }

    function isNotEmpty(member) {
        return member.title !== '' && member.name !== '' && member.password !== '';
    }

</script>

<style scoped>
    .register-container {
        min-height: 100vh;
        background-color: #63b47a;
    }

    .register {
        background-color: white;
        width: 400px;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        border-radius: 8px;
        border: white 1px solid;
        padding: 20px;
    }


</style>