<template>
    <div class="pa-5">
        <v-row>
            <v-col class="mb-4" cols="12" md="6">
                <v-row>
                    <v-col cols="6">
                        <v-menu
                                :close-on-content-click="false"
                                :return-value.sync="start"
                                offset-y
                                ref="dateOpen"
                                v-model="dateOpen"
                        >
                            <template v-slot:activator="{ on }">
                                <v-text-field
                                        dark
                                        dense
                                        hide-details
                                        label="Start Date"
                                        outlined
                                        prepend-icon="mdi-calendar"
                                        readonly
                                        v-model="start"
                                        v-on="on"
                                ></v-text-field>
                            </template>
                            <v-date-picker dark
                                           no-title
                                           v-model="start"
                            >
                                <v-spacer/>
                                <v-btn @click="dateOpen = false" color="primary" dark text>Cancel</v-btn>
                                <v-btn @click="$refs.dateOpen.save(start)" color="primary" dark text>OK</v-btn>
                            </v-date-picker>
                        </v-menu>
                    </v-col>
                    <v-col cols="6">
                        <v-select
                                :items="typeOptions"
                                class="my-auto"
                                dark
                                dense
                                hide-details
                                label="Type"
                                outlined
                                v-model="type"
                        ></v-select>
                    </v-col>
                </v-row>
                <div class="text-center mb-3 display-1">
                    {{start | moment('YYYY MMMM')}}
                </div>
                <v-sheet height="500">
                    <v-calendar
                            :start="start"
                            :type="type"
                            @click:date="open"
                            dark
                            ref="calendar"
                    ></v-calendar>
                </v-sheet>
            </v-col>
            <Dialog/>
        </v-row>
    </div>
</template>

<script>
    import Dialog from "../components/EventDialog";

    export default {
        data() {
            return {
                dateOpen: false,
                start: '',
                type: 'month',
                typeOptions: [
                    {text: 'Day', value: 'day'},
                    {text: 'Week', value: 'week'},
                    {text: 'Month', value: 'month'},
                ],
            }
        },
        components: {
            Dialog
        },
        methods: {
            open(date) {
                this.$store.commit('OPEN_CALENDAR_DIALOG', date)
            },
            getEventColor(event) {
                return event.color
            },
            showIntervalLabel(interval) {
                // console.log(interval);
                return interval.minute === 0
            },
            showEvent({event, day}) {
                console.log(event);
                console.log(day);
            },
            moreEvent({date}) {
                this.start = date;
                this.type = 'day';
            }
        },
        computed: {
            events() {
                return this.$store.state.calendar.events;
            }
        },
        created() {
            this.start = this.$moment().format('YYYY-MM-DD');
        }
    }
</script>

<style scoped>

</style>