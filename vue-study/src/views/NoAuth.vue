<template>
        <div class="no-auth text-center">
            <h1 class="mb-5">현재 페이지로 접속하기 위해선 로그인이 필요합니다.</h1>
            <v-btn color="red" outlined to="/login">로그인 페이지로 이동</v-btn>
        </div>
</template>

<script>
    export default {
        name: "NoAuth"
    }
</script>

<style scoped>
    .no-auth {
        background-color: white;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        border-radius: 8px;
        border: white 1px solid;
    }
</style>