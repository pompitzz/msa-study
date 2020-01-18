<template>
    <div>
        <v-row class="mx-5">
            <v-col :key="member.email" cols="10" lg="3" md="4" offset="1" offset-sm="0"
                   sm="6" v-for="member in members">
                <v-btn @click="findMembers">사용자 조회</v-btn>
                <v-card class="text-center" dark>
                    <v-card-title><span class="mx-auto">이름: {{member.name}}</span></v-card-title>
                    <hr>
                    <v-card-subtitle>
                        <h3>Email: {{member.email}}</h3>
                        <br>
                        <h3>Role: {{member.role}}</h3>
                    </v-card-subtitle>
                    <v-card-actions>
                        <v-spacer/>
                        <div class="mb-3">
                            <v-btn class="mx-3" color="white" outlined>
                                수정
                            </v-btn>
                            <v-btn class="mx-3" color="white" outlined>
                                삭제
                            </v-btn>
                        </div>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
        <Modal/>
    </div>
</template>

<script>
    import Modal from "../components/Modal";

    export default {
        name: "Admin",
        components: {Modal},
        data() {
            return {
                members: [],
            }
        },
        methods: {
            findMembers() {
                this.$store.dispatch('QUERY_MEMBERS_FOR_ADMIN').then(res => this.members = res)
            }
        },
        created() {
            this.$store.dispatch('QUERY_MEMBERS_FOR_ADMIN').then(res => this.members = res)
        }
    }
</script>

<style scoped>

</style>