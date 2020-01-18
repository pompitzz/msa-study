<template>
    <div class="fill-height">
        <v-row align="center" class="fill-height" justify="center">
            <v-col class="text-center" cols="12">
                <v-icon color="white" size="70">mdi-alert-outline</v-icon>
                <h1 class="my-3">{{errorMessage}}</h1>
            </v-col>
        </v-row>
    </div>
</template>

<script>
    export default {
        name: "Error",
        computed: {
            errorMessage() {
                return this.$store.state.common.errorMessage;
            }
        }
    }
</script>

<style scoped>

</style>