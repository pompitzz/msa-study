<template>
    <nav>
        <v-app-bar app class="white--text" color="#404242" flat>

            <v-app-bar-nav-icon @click.stop="drawer = !drawer" color="white"/>
            <v-toolbar-title>
                <span v-if="!drawer">BLOG</span>
            </v-toolbar-title>

            <v-spacer/>
            <v-menu offset-y>
                <template v-slot:activator="{ on }">
                    <v-icon color="white" v-on="on">mdi-apps</v-icon>
                </template>

                <v-list>
                    <div :key="link.name" v-for="link in links">
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

        <!-- SECTION: 옆쪽 Drawer
        ====================================================================== -->
        <v-navigation-drawer app dark v-model="drawer">
            <v-col class="mt-5 text-center">
                <h2 class="white--text my-3">
                    Stranger BLOG
                </h2>
            </v-col>
            <hr class="ma-3 white my-hr"/>
            <v-list>
                <div :key="link.text" v-for="link in links">
                    <v-list-item :to="link.route" router v-if="selectMenuList(link.name)">
                        <v-list-item-action>
                            <v-icon color="white">{{link.icon}}</v-icon>
                        </v-list-item-action>
                        <v-list-item-content class="white--text">
                            {{link.name}}
                        </v-list-item-content>
                    </v-list-item>
                </div>

                <v-list-item @click="LOGOUT" router v-if="isAuthenticated">
                    <v-list-item-action>
                        <v-icon color="white">mdi-logout-variant</v-icon>
                    </v-list-item-action>
                    <v-list-item-content class="white--text">
                        로그아웃
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>
        <!-- SECTION: 옆쪽 Drawer -->
    </nav>
</template>

<script>
    import {mapMutations} from 'vuex'

    export default {
        name: "AppBar",
        data() {
            return {
                drawer: true,
                links: [
                    {name: '메인', icon: 'mdi-home', route: '/main'},
                    {name: '메모장', icon: 'mdi-note-text', route: '/memo'},
                    {name: '게시글', icon: 'mdi-clipboard-text-multiple-outline', route: '/boards'},
                    {name: '관리자 페이지', icon: 'mdi-account', route: '/admin'},
                    {name: '로그인', icon: 'mdi-login-variant', route: '/login'},
                    {name: '회원가입', icon: 'mdi-account-plus', route: '/register'},
                ]
            }
        },
        computed: {
            isAuthenticated(){
                return this.$store.getters.isAuthenticated;
            },
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