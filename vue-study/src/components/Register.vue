<template>
    <div class="register-container">

        <!-- Material form register -->
        <div class="register shadow-xl">

            <h3 class="font-weight-bold text-center py-3 mb-3">
                회 원 가 입
            </h3>

            <div class="card-body pt-0">
                <!-- E-mail -->
                <mdb-input id="res-email" label="E-mail" type="email" v-model="member.email"/>

                <!-- Password -->
                <mdb-input id="res-password" label="Password" type="password" v-model="member.password"/>
                <!-- Name -->
                <mdb-input id="res-name" label="Name" v-model="member.name"/>

                <div class="text-center">
                    <!-- Sign up button -->
                    <mdb-btn @click.native.prevent="join" class="px-5 font-weight-bold my-4" outline="info"
                             rounded>
                        Sign in
                    </mdb-btn>
                </div>
            </div>
        </div>
        <Modal>
            <slot slot="title"> {{modalTitle}}</slot>
            <slot slot="description"> {{modalDescription}}</slot>
            <div @click="CLOSE_MODAL" slot="option">
                <router-link class="text-white" to="/login" v-if="modalOption === '이동'">
                    {{modalOption}}
                </router-link>
                <slot v-else>
                    {{modalOption}}
                </slot>
            </div>
        </Modal>
    </div>
</template>

<script>
    import Modal from "./common/Modal";
    import {mapState, mapMutations, mapActions} from 'vuex'
    import {createModalTexts} from "../common";
    import {mdbInput, mdbBtn} from 'mdbvue'

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
                'modal',
                'modalTitle',
                'modalDescription',
                'modalOption'
            ])
        },
        components: {
            Modal,
            mdbInput,
            mdbBtn
        },
        methods: {
            ...mapMutations(['CLOSE_MODAL', 'SET_MODAL_TEXTS']),
            ...mapActions(['REQUEST_JOIN']),
            join() {
                if (isNotEmpty(this.member)) {
                    this.REQUEST_JOIN(this.member);

                } else {
                    this.SET_MODAL_TEXTS(createModalTexts(
                        '회원가입 불가',
                        '빈칸을 모두 작성해주세요',
                        '닫기'
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