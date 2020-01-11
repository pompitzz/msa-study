<template>
    <div class="fill-height">
        <v-row align="center" class="fill-height" justify="center">
            <v-card class="text-center" dark style="padding: 30px">
                <h3 class="mb-5">현재 페이지로 접속하기 위해선 로그인이 필요합니다.</h3>
                <v-btn color="red" outlined to="/login">로그인 페이지로 이동</v-btn>
            </v-card>

            <!--            <v-card>-->
            <!--                <v-alert type="error" class="ma-0">-->
            <!--                    <h3>현재 페이지로 접속하기 위해선 로그인이 필요합니다.</h3>-->
            <!--                    <h4 class="text-center">로그인 페이지로 이동</h4>-->
            <!--                </v-alert>-->
            <!--            </v-card>-->
        </v-row>
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