<template>
    <div class="login-container">
        <div class="login text-center rounded shadow-xl">
            <h1 class="mb-4">로그인</h1>
            <form>
                <input class="d-block w-100 p-3 mb-3 my-input" placeholder="id" type="text" v-model="email">
                <input class="d-block w-100 p-3 mb-3 my-input" placeholder="password" type="password" v-model="password">
                <a @click="loginRequest" class="btn btn-warning text-white w-75 font-weight-bold p-2">LOGIN</a>
                <p class="mt-3 mb-0">아직 회원이 아니신가요? <router-link to="/resister"
                        class="font-weight-bold text-danger">회원가입</router-link></p>
            </form>
        </div>
    </div>

</template>

<script>
    import {store} from "../store/sotre";

    export default {
        name: "Login",
        data() {
            return{
                email: '',
                password: '',
            }
        },
        methods:{
            loginRequest(){
                const info = {
                    email: this.email,
                    password: this.password,
                };

                this.$store.dispatch('REQUEST_LOGIN', info)
            }
        }

    }
</script>

<style scoped>
    .login-container {
        min-height: 100vh;
        background-color: #63b47a;
    }

    .login {
        width: 330px;
        background-color: white;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        border-radius: 8px;
        border: white 1px solid;
        padding: 25px;
    }

    h1 {
        font-size: 1.5rem;
        color: #666;
        font-weight: bold;
    }

    .my-input {
        border: 1px #d6d6d6 solid;
        border-radius: 4px;
    }

</style>