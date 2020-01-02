<template>
    <div class="login-container">

        <div class="login text-center elevation-15">
            <h3 class="mb-4 font-weight-bold">로그인</h3>

            <div>
                <v-form class="pa-3 text-center" ref="form">

                    <v-text-field :rules="emailRules"
                                  required v-model="member.email" label="E-mail"
                                  type="email" prepend-icon="mdi-email"></v-text-field>

                    <v-text-field :rules="passwordRules"
                                  required v-model="member.password"
                                  label="Password" type="password" prepend-icon="mdi-lock"></v-text-field>


                    <v-btn class="mt-3" color="cyan" outlined @click="loginRequest" :loading="loadingState">LOGIN</v-btn>
                </v-form>
                <p class="mt-3 mb-0">아직 회원이 아니신가요?
                    <router-link class="font-weight-bold red--text"
                                 to="/register">회원가입
                    </router-link>
                </p>
            </div>
        </div>
        <Modal/>
    </div>
</template>

<script>
    import {mapActions, mapState, mapMutations} from 'vuex'
    import Modal from "../components/Modal";

    export default {
        name: "Login",
        data() {
            return {
                member: {
                    email: '',
                    password: '',
                },
            }
        },
        computed: {
            ...mapState(['emailRules', 'passwordRules', 'loadingState'])
        },
        components: {
            Modal
        },
        methods: {
            ...mapActions(['REQUEST_LOGIN']),
            ...mapMutations(['OPEN_MODAL', 'START_LOADING']),
            loginRequest() {
                if (this.$refs.form.validate()) {
                    this.START_LOADING();
                    this.REQUEST_LOGIN(this.member);
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

</style>