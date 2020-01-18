<template>
    <div class="fill-height">
        <v-row align="center" class="fill-height" justify="center">
            <v-col cols="12" md="6" sm="12">
                <div class="mx-5">
                    <div class="text-center text-uppercase">
                        <h1>Calendar</h1>
                    </div>
                    <v-row>
                        <v-col cols="6">
                            <v-menu
                                    :close-on-content-click="false"
                                    :nudge-right="40"
                                    :return-value.sync="start"
                                    offset-y
                                    ref="startMenu"
                                    transition="scale-transition"
                                    v-model="startMenu"
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
                                               scrollable
                                               v-model="start"
                                >
                                    <v-spacer/>
                                    <v-btn @click="startMenu = false" color="primary" dark text>Cancel</v-btn>
                                    <v-btn @click="$refs.startMenu.save(start)" color="primary" dark text>OK</v-btn>

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
                    <div class="text-center mb-3">
                        <v-btn
                                @click="$refs.calendar.prev()"
                                class="mr-auto"
                                color="white"
                                fab
                                outlined
                                x-small
                        >
                            <v-icon dark>mdi-chevron-left</v-icon>
                        </v-btn>
                        <h2 class="mx-5 d-inline-block">{{start | moment('YYYY MMMM')}}</h2>
                        <v-btn
                                @click="$refs.calendar.next()"
                                class="ml-auto"
                                color="white"
                                fab
                                outlined
                                x-small
                        >
                            <v-icon dark>mdi-chevron-right</v-icon>
                        </v-btn>
                    </div>
                    <v-sheet height="500">
                        <v-calendar
                                :event-color="getEventColor"
                                :events="events"
                                :show-interval-label="showIntervalLabel"
                                :start="start"
                                :type="type"
                                @click:date="open"
                                @click:event="showEvent"
                                @click:more="moreEvent"
                                @click:time="open"
                                dark
                                ref="calendar"

                                v-model="start"
                        ></v-calendar>
                    </v-sheet>
                </div>
            </v-col>
            <v-col class="mb-4 controls" cols="12" md="6">
                <div class="text-center">
                    <v-btn color="green" outlined to="/boards">게시글로 가기</v-btn>
                </div>
            </v-col>
        </v-row>
        <Dialog :dialog="true"/>
    </div>
</template>

<script>
    import Dialog from "../components/Dialog";

    export default {
        data: () => ({
            dark: true,
            startMenu: false,
            start: '',
            now: null,
            names: ['Meeting', 'Holiday', 'PTO', 'Travel', 'Event', 'Birthday', 'Conference', 'Party'],
            type: 'month',
            typeOptions: [
                {text: 'Day', value: 'day'},
                {text: 'Week', value: 'week'},
                {text: 'Month', value: 'month'},
            ],
            maxDays: 7,
            maxDaysOptions: [
                {text: '7 days', value: 7},
                {text: '5 days', value: 5},
                {text: '4 days', value: 4},
                {text: '3 days', value: 3},
            ],
        }),
        components: {
            Dialog
        },
        methods: {
            open(date) {
                console.log(date);
                this.$store.commit('OPEN_CALENDAR_DIALOG', date)
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
            },

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

    .controls {
        position: relative;
    }
</style>