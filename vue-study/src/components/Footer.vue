<template>
    <v-footer app class="theme--dark" fixed><span class="mx-auto">Copyright © {{new Date() | moment('YYYY')}} All Rights Reserved.</span>
    </v-footer>
</template>

<script>
    export default {
        name: "Footer"
    }
</script>

<style scoped>

</style>