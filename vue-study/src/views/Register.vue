<template>
    <div class="fill-height">
        <v-container class="fill-height">
            <v-row align="center" class="fill-height" justify="center">
                <div class="register elevation-15">
                    <h3 class="font-weight-bold text-center py-3 black--text">
                        회 원 가 입
                    </h3>

                    <v-form class="pa-3 text-center" ref="form">

                        <v-text-field :rules="emailRules" class="pl-3 pr-3" label="E-mail" prepend-icon="mdi-email"
                                      required type="email" v-model="member.email">
                        </v-text-field>

                        <v-text-field :rules="passwordRules" class="pl-3 pr-3" label="Password" prepend-icon="mdi-lock"
                                      required type="password" v-model="member.password">
                        </v-text-field>

                        <v-text-field :rules="validatePasswordRules" class="pl-3 pr-3" label="Re-enter password"
                                      prepend-icon="mdi-lock"
                                      required type="password" v-model="validatePassword">
                        </v-text-field>

                        <v-text-field :rules="nameRules" class="pl-3 pr-3" label="Name" prepend-icon="mdi-account"
                                      @keyup.enter="joinRequest" required type="text" v-model="member.name">
                        </v-text-field>

                        <v-btn :loading="loadingState" @click="joinRequest" class="mt-3" color="indigo" outlined>
                            REGISTER
                        </v-btn>

                    </v-form>
                </div>
            </v-row>
        </v-container>
        <Modal @pass="modalEvent"/>
    </div>
</template>

<script>
    import Modal from "../components/Modal";
    import {router} from "../routes/route";

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
            emailRules() {
                return this.$store.state.common.emailRules;
            },
            loadingState() {
                return this.$store.state.common.loadingState;
            },
            passwordRules() {
                return this.$store.state.common.passwordRules;
            },
            nameRules() {
                return this.$store.state.common.nameRules;
            },
        },
        components: {
            Modal
        },
        methods: {
            joinRequest() {
                if (this.$refs.form.validate()) {
                    this.$store.dispatch('REQUEST_JOIN', this.member);
                }
            },
            modalEvent() {
                this.$store.commit('CLOSE_MODAL_AND_MOVE', '/login');
            }
        }
    }

</script>

<style scoped>

    @media (min-width: 700px) {
        .register {
            width: 400px !important;
        }
    }

    .register {
        background-color: white;
        border-radius: 8px;
        width: 80%;
        border: white 1px solid;
        padding: 20px;
    }
</style>