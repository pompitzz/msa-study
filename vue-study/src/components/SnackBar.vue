<template>
    <v-snackbar
            :bottom="snackbar.location === 'bottom'"
            :color="snackbar.color"
            :timeout="timeout"
            :top="snackbar.location === 'top'"
            v-model="snackbar.open">
        <h3 class="white--text">{{snackbar.text}}</h3>

        <v-btn @click="snackbar.open = false" color="white--text" text>
            CLOSE
        </v-btn>
    </v-snackbar>
</template>

<script>
    export default {
        name: "SnackBar",
        data() {
            return {
                timeout: 5000,
            }
        },
        computed: {
            snackbar() {
                return this.$store.state.common.snackbar;
            }
        },
    }
</script>

<style scoped>

</style>