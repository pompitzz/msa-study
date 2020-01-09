<template>
    <div class="mx-auto">
        <h1>Main</h1>
        <div style="width: 500px">
            <v-file-input
                    label="images"
                    multiple
                    name="images"
                    prepend-icon="mdi-camera"
                    v-model="files"
            >
                <template v-slot:selection="{ text }">
                    <v-chip
                            class="white--text"
                            color="indigo"
                            label
                            small
                    >
                        {{ text }}
                    </v-chip>
                </template>
            </v-file-input>
            <v-btn @click="sendImage">
                전송
            </v-btn>
            <br>
            <v-img :src="endimg"
                   height="300"
                   width="300"></v-img>
            test
            <v-img :src="imgUrl"
                   height="300"
                   width="300"
            ></v-img>
            {{imgUrl}}
        </div>

    </div>
</template>

<script>
    import {mapActions} from 'vuex'

    export default {
        name: "Main",
        data() {
            return {
                files: [],
                imgUrl: '',
                endimg: '',
            }
        },
        methods: {
            ...mapActions(['UPLOAD_IMAGE']),
            sendImage() {
                // this.image = this.$refs.image.files[0];
                this.UPLOAD_IMAGE(this.files)
                    .then(img => this.imgUrl = img);

                let reader = new FileReader();
                reader.onload = (e) => {
                    this.endimg = e.target.result;
                };
                reader.readAsDataURL(this.files[0]);
            },

        }
    }
</script>

<style scoped>

</style>