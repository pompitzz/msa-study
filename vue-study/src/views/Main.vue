<template>
    <div>
        <v-container>
            <editor ref="editor"
                    v-model="editorText"
            />
            <v-btn @click="log">Click</v-btn>

            <v-card class="pa-3" light>
                <viewer :value="editorText" dark="false"/>
            </v-card>
            <code>
                testtesttest
            </code>
        </v-container>
    </div>
</template>

<script>


    import Editor from '@toast-ui/vue-editor/src/Editor.vue'
    import Viewer from '@toast-ui/vue-editor/src/Viewer.vue'
    import {mapActions} from 'vuex'

    export default {
        name: "Main",
        components: {
            Editor,
            Viewer
        },
        data() {
            return {
                editorText: '',
                htmltext: '',
            }
        },
        methods: {
            ...mapActions(['SEND_BOARD']),
            log() {
                console.log(this.editorText);
                const jsonTest = {
                    content: this.editorText,
                };
                this.SEND_BOARD(jsonTest);
                let html = this.$refs.editor.invoke('getHtml');
                console.log(html);
            }
        }
    }
</script>

<style scoped>
    code {
        box-shadow: none !important;
    }
</style>