<template>
    <!--Main Navigation-->
    <header>
        <nav class="navbar fixed-top navbar-expand-lg navbar-dark default-color-dark scrolling-navbar">
            <router-link class="navbar-brand" href="#" to="/main"><strong>M S A</strong></router-link>
            <button aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"
                    class="navbar-toggler" data-target="#navbarSupportedContent"
                    data-toggle="collapse" type="button">
                <span class="navbar-toggler-icon"/>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto">
                    <li :class="{active : this.$route.name === 'main'}" class="nav-item">
                        <router-link class="nav-link" href="#" to="/main">메인</router-link>
                    </li>
                    <li :class="{active : this.$route.name === 'board'}" class="nav-item">
                        <router-link class="nav-link" href="#" to="/board">게시글</router-link>
                    </li>
                    <li :class="{active : this.$route.name === 'login'}" class="nav-item"
                        v-if="!isAuthenticated">
                        <router-link class="nav-link" href="#" to="/login">로그인</router-link>
                    </li>
                    <li :class="{active : this.$route.name === 'register'}" class="nav-item"
                        v-if="!isAuthenticated">
                        <router-link class="nav-link" href="#" to="/register">회원가입</router-link>
                    </li>
                    <li class="nav-item" v-if="isAuthenticated">
                        <a @click.prevent="LOGOUT" class="nav-link" href="#">로그아웃</a>
                    </li>
                </ul>
            </div>
        </nav>

    </header>
</template>

<script>
    import {mapGetters, mapMutations} from 'vuex';

    export default {
        name: "Test",
        computed: {
            ...mapGetters(['isAuthenticated']),
        },
        methods: {
            ...mapMutations(['LOGOUT']),
        }
    }
</script>

<style scoped>
    nav li .router-link-active {
    }

    ul.navbar-nav li {
        margin: 0 5px;
    }
</style>