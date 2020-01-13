<template>
    <div class="fill-height">
        <v-row align="center" class="fill-height" justify="center">
            <h1 class="display-2">Main Page</h1>
        </v-row>
        <Modal @pass="modalEvnet"/>
    </div>
</template>

<script>
    import {mapActions, mapMutations} from 'vuex'
    import Modal from "../components/Modal";

    export default {
        name: "Main",
        components: {Modal},
        data() {
            return {
                dialog: false,
                two: 90,
                three: 85,
                four: 80,
                items: ['Foo', 'Bar', 'Fizz', 'Buzz'],
                model: 'Foo',
            }
        },
        methods: {
            ...mapActions(['QUERY_MEMBER']),
            ...mapMutations(['CLOSE_MODAL']),
            queryMember() {
                let email = localStorage.getItem('email');
                if (localStorage.getItem('name') === null && email !== null) {
                    this.QUERY_MEMBER(email);
                }
            },
            modalEvnet() {
                this.queryMember();
            }
        },
        created() {
            this.queryMember();
        }
    }
</script>

<style scoped>
    code {
        box-shadow: none !important;
    }
</style>