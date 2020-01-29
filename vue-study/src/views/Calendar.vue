<template>
    <div class="fill-height">
        <v-row align="center" class="fill-height" justify="center">
            <v-col cols="12" lg="8" md="10" sm="12" xl="7">
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
                                :event-overlap-threshold="30"
                                :start="start"
                                :type="type"
                                @click:date="open"
                                @click:event="showEvent"
                                @click:more="moreEvent"
                                dark
                                ref="calendar"

                                v-model="start"
                        ></v-calendar>
                    </v-sheet>
                    <div class="text-right mt-3 font-weight-bold ">
                        <v-btn @click="open({ date: start })" class="white--text" color="indigo"
                               large>일정 추가
                        </v-btn>
                    </div>
                </div>
            </v-col>
        </v-row>
        <EventDialog :dialog="true"/>
        <EventDetail :dialog="true"/>
    </div>
</template>

<script>
    import EventDialog from "../components/EventDialog";
    import {setSnackBarInfo} from "../apis/common_api";
    import EventDetail from "../components/EventDetail";

    export default {
        data: () => ({
            dark: true,
            startMenu: false,
            start: '',
            type: 'month',
            typeOptions: [
                {text: 'Day', value: 'day'},
                {text: 'Week', value: 'week'},
                {text: 'Month', value: 'month'},
            ],
        }),
        components: {
            EventDetail,
            EventDialog
        },
        methods: {
            open(date) {
                console.log(date);
                if (localStorage.getItem('access_token') === null) {
                    this.$store.commit('SET_SNACKBAR', setSnackBarInfo('로그인 후 이용해주세요.', 'error', 'top'));
                } else {
                    this.$store.commit('OPEN_CALENDAR_DIALOG', date)
                }
            },
            showIntervalLabel(interval) {
                return interval.minute === 0
            },
            showEvent({event}) {
                this.$store.dispatch('REQUEST_DETAIL_EVENT', event.id);
            },
            moreEvent({date}) {
                this.start = date;
                this.type = 'day';
            },
            getEventColor(event) {
                return event.color;
            }

        },
        computed: {
            events() {
                return this.$store.state.calendar.events;
            }
        },
        created() {
            this.start = this.$moment().format('YYYY-MM-DD');
        },
        watch: {
            start(newDate, oldDate) {
                let newDateMonth = this.$moment(newDate).format('MM');
                let oldDateMonth = this.$moment(oldDate).format('MM');
                if (newDateMonth !== oldDateMonth && !!localStorage.getItem('access_token')) {
                    this.$store.dispatch('REQEUST_QUERY_EVENTS_BY_DATE', newDate);
                }
            }
        }
    }
</script>

<style scoped>

    .controls {
        position: relative;
    }
</style>