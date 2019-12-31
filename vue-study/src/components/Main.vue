<template>
    <div>
        <div class="text-center">
            <h1 style="margin-top: 300px;">This is Main Component</h1>
            <mdb-btn color="primary">
                <mdb-icon class="mr-1" icon="magic"/>
                Primary
            </mdb-btn>

            <mdb-btn color="default">Default</mdb-btn>

            <mdb-btn color="secondary">Secondary</mdb-btn>
        </div>


    </div>
</template>

<script>
    import {mdbBtn, mdbIcon} from 'mdbvue'

    export default {
        name: "Main",
        components: {
            mdbBtn,
            mdbIcon
        }
    }
</script>

<style scoped>

</style>