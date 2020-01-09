<template>
    <div>
        <v-app-bar
                flat
                color="#404242"
                class="white--text">

            <v-toolbar-title>
                <router-link to="/" class="white--text">
                    M S A
                </router-link>
            </v-toolbar-title>

            <v-spacer></v-spacer>

            <div v-for="link in links" :key="link.name" class="d-inline">
                <v-btn outlined class="mr-3 d-none d-sm-inline-flex" color="white" :to="link.route"
                       v-if="selectMenuList(link.name)">
                    <v-icon left>{{link.icon}}</v-icon>
                    <span>{{link.name}}</span>
                </v-btn>
            </div>

            <v-btn outlined class="mr-3 d-none d-sm-inline-flex"
                   color="white" @click="LOGOUT" v-if="isAuthenticated">
                <v-icon left>mdi-logout-variant</v-icon>
                <span>로그아웃</span>
            </v-btn>

            <v-menu
                    left
                    bottom
            >
                <template v-slot:activator="{ on }">
                    <v-btn icon v-on="on" class="d-inline d-sm-none white--text">
                        <v-icon>mdi-menu</v-icon>
                    </v-btn>
                </template>

                <v-list>
                    <div v-for="link in links"
                         :key="link.name">
                        <v-list-item v-if="selectMenuList(link.name)"
                                     router :to="link.route">
                            <v-list-item-title>{{link.name}}</v-list-item-title>
                        </v-list-item>
                    </div>

                    <v-list-item v-if="isAuthenticated"
                                 @click="LOGOUT">
                        <v-list-item-title>로그아웃</v-list-item-title>
                    </v-list-item>
                </v-list>

            </v-menu>
        </v-app-bar>
    </div>
</template>

<script>
    import {mapGetters, mapMutations} from 'vuex'

    export default {
        name: "AppBar",
        data() {
            return {
                links: [
                    {name: '메인', icon: 'mdi-home', route: '/'},
                    {name: '메모장', icon: 'mdi-note-text', route: '/memo'},
                    {name: '로그인', icon: 'mdi-login-variant', route: '/login'},
                    {name: '회원가입', icon: 'mdi-account-plus', route: '/register'},
                    {name: '게시글', icon: 'mdi-clipboard-text-multiple-outline', route: '/boards'},
                ]
            }
        },
        computed: {
            ...mapGetters(['isAuthenticated'])
        },
        methods: {
            ...mapMutations(['LOGOUT']),
            selectMenuList(name) {
                if (name === '로그인' || name === '회원가입') {
                    if (this.isAuthenticated) {
                        return false;
                    }
                }

                return true;
            }
        }


    }
</script>

<style scoped>
    a {
        text-decoration: none;
    }
</style>