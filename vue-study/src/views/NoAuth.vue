<template>
        <div class="ml-auto no-auth text-center mt-5 mr-5">
            <h3 class="mb-5">현재 페이지로 접속하기 위해선 로그인이 필요합니다.</h3>
            <v-btn color="red" outlined to="/login">로그인 페이지로 이동</v-btn>
        </div>
</template>

<script>
    export default {
        name: "NoAuth"
    }
</script>

<style scoped>
    /*.no-auth {*/
    /*    background-color: white;*/
    /*    position: absolute;*/
    /*    top: 50%;*/
    /*    left: 50%;*/
    /*    transform: translate(-50%, -50%);*/
    /*    border-radius: 8px;*/
    /*    border: white 1px solid;*/
    /*}*/
</style>