<template>
    <div class="fill-height">
        <v-container class="text-center fill-height">
            <v-alert class="white--text ma-auto pa-5" color="error" max-width="500">
                <h2>존재하지 않는 페이지 입니다.</h2>
                <br>
                <v-btn color="white" outlined to="/boards">홈으로 가기</v-btn>
            </v-alert>
        </v-container>
    </div>
</template>

<script>
    export default {
        name: "NoPage"
    }
</script>

<style scoped>

</style>