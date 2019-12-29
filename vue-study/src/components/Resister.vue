<template>
    <div class="login-container">
        <div class="login rounded col-10 col-xl-3 col-md-6 shadow-xl">
            <h1 class="mb-4 text-center">회원가입</h1>
            <form>
                <form>
                    <div class="form-group">
                        <label for="Email">아이디</label>
                        <input type="email" class="form-control" id="Email" aria-describedby="emailHelp"
                               v-model="email">
                    </div>
                    <div class="form-group">
                        <label for="password">비밀번호</label>
                        <input type="password" class="form-control" id="password" v-model="password">
                    </div>
                    <div class="form-group">
                        <label for="name">이름</label>
                        <input type="password" class="form-control" id="name" v-model="name">
                    </div>
                    <div class="text-center">
                        <a @click="join" class="btn btn-info btn-lg text-white font-weight-bold w-50 mt-2">회원 가입
                        </a>
                    </div>
                </form>
            </form>
        </div>
    </div>

</template>

<script>
    import {router} from "../routes/route";

    export default {
        name: "Resister",
        data() {
            return {
                email: '',
                password: '',
                name: '',
                showModal: false,
            }
        },
        methods: {
            join() {
                if (this.email !== '' && this.password !== '' && this.name !== '') {
                    let member = {
                        email: this.email,
                        password: this.password,
                        name: this.name,
                        role: 'USER',
                    };
                    this.$store.dispatch('REQUEST_JOIN', member);

                } else {
                }
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