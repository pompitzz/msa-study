<template>
    <mdb-navbar class="default-color-dark py-3" dark position="top">
        <mdb-navbar-brand to="/main">
            M S A
        </mdb-navbar-brand>
        <mdb-navbar-toggler>
            <mdb-navbar-nav right>

                <mdb-nav-item :class="{active : this.$route.name === 'main'}" href="#" to="/main">메인</mdb-nav-item>
                <mdb-nav-item :class="{active : this.$route.name === 'board'}" href="#" to="/board">게시글</mdb-nav-item>
                <mdb-nav-item :class="{active : this.$route.name === 'login'}" href="#"
                              to="/login" v-if="!isAuthenticated">로그인
                </mdb-nav-item>
                <mdb-nav-item :class="{active : this.$route.name === 'register'}" href="#"
                              to="/register" v-if="!isAuthenticated">회원가입
                </mdb-nav-item>

                <mdb-nav-item :class="{active : this.$route.name === 'register'}" href="#"
                              to="/register" v-if="isAuthenticated">로그아웃
                </mdb-nav-item>
            </mdb-navbar-nav>

        </mdb-navbar-toggler>
    </mdb-navbar>
</template>

<script>
    import {
        mdbNavbar,
        mdbNavbarBrand,
        mdbNavbarToggler,
        mdbNavbarNav,
        mdbNavItem,
    } from 'mdbvue';
    import {mapGetters} from 'vuex'

    export default {
        name: 'Navbar',
        components: {
            mdbNavbar,
            mdbNavbarBrand,
            mdbNavbarToggler,
            mdbNavbarNav,
            mdbNavItem,
        },
        computed: {
            ...mapGetters(['isAuthenticated'])
        }
    }
</script>

<style scoped>
    .navbar-nav .nav-item {
        margin-left: 3px;
    }
</style>
