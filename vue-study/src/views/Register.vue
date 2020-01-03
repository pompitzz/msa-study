<template>
    <div class="register-container">

        <div class="register elevation-15">
            <h3 class="font-weight-bold text-center py-3">
                회 원 가 입
            </h3>

            <v-form class="pa-3 text-center" ref="form">

                <v-text-field class="pl-3 pr-3" :rules="emailRules" required v-model="member.email"
                              label="E-mail" type="email" prepend-icon="mdi-email">
                </v-text-field>

                <v-text-field class="pl-3 pr-3" :rules="passwordRules" required v-model="member.password"
                              label="Password" type="password" prepend-icon="mdi-lock">
                </v-text-field>

                <v-text-field class="pl-3 pr-3" :rules="validatePasswordRules" required v-model="validatePassword"
                              label="Re-enter password" type="password" prepend-icon="mdi-lock">
                </v-text-field>

                <v-text-field class="pl-3 pr-3" :rules="nameRules" required v-model="member.name"
                              label="Name" type="text" prepend-icon="mdi-account">
                </v-text-field>

                <v-btn class="mt-3" color="" outlined @click="joinRequest" :loading="loadingState">REGISTER</v-btn>

            </v-form>
        </div>
        <Modal/>
    </div>
</template>

<script>
    import {mapState, mapActions, mapMutations} from 'vuex'
    import Modal from "../components/Modal";


    export default {
        name: "Resister",
        data() {
            return {
                dialog: false,
                member: {
                    email: '',
                    password: '',
                    name: '',
                    role: 'USER',
                },
                validatePassword: '',
                validatePasswordRules: [
                    v => v === this.member.password || '비밀번호가 맞지 않습니다..'
                ]
            }
        },
        computed: {
            ...mapState([
                'emailRules',
                'passwordRules',
                'nameRules',
                'loadingState',
            ])
        },
        components: {
            Modal
        },
        methods: {
            ...mapActions(['REQUEST_JOIN']),
            ...mapMutations(['OPEN_MODAL', 'START_LOADING']),
            joinRequest() {
                if (this.$refs.form.validate()) {
                    this.START_LOADING();
                    this.REQUEST_JOIN(this.member);
                }
            },
        }
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