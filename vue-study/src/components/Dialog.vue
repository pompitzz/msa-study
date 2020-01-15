<template>
    <v-dialog max-width="600px" persistent v-model="dialog">
        <v-card>
            <v-card-title>
                <h3>일정 추가</h3>
            </v-card-title>
            <v-card-text>
                <v-form class="px-3" ref="form">
                    <v-text-field :rules="inputRules" label="일정" prepend-icon="mdi-folder-marker"
                                  v-model="calendar.title"></v-text-field>
                    <v-textarea :rules="inputRules" label="상세설명" prepend-icon="mdi-pencil"
                                v-model="calendar.content"></v-textarea>
                    <v-row>
                        <v-col class="pb-0" cols="6">
                            <v-menu>
                                <template v-slot:activator="{on}">

                                    <v-text-field :value="calendar.startDate" class=""
                                                  label="시작일"
                                                  prepend-icon="mdi-calendar-month"
                                                  readonly slot="activator"
                                                  v-on="on"></v-text-field>
                                </template>
                                <v-date-picker v-model="calendar.startDate"></v-date-picker>
                            </v-menu>
                        </v-col>
                        <v-col class="pb-0" cols="6">
                            <v-menu
                                    :close-on-content-click="false"
                                    offset-y
                                    v-model="startTimer"
                            >
                                <template v-slot:activator="{ on }">
                                    <v-text-field
                                            :value="calendar.startTime"
                                            label="시작 시간"
                                            prepend-icon="mdi-timer"
                                            readonly
                                            v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-time-picker
                                        v-if="startTimer"
                                        v-model="calendar.startTime"
                                >
                                    <v-btn @click="selectTime"
                                           class="mx-auto"
                                    >선택
                                    </v-btn>
                                </v-time-picker>
                            </v-menu>
                        </v-col>
                    </v-row>

                    <v-row>
                        <v-col class="pt-0" cols="6">
                            <v-menu>
                                <template v-slot:activator="{on}">

                                    <v-text-field :value="calendar.endDate" class=""
                                                  label="종료일"
                                                  prepend-icon="mdi-calendar-month"
                                                  readonly slot="activator"
                                                  v-on="on"></v-text-field>
                                </template>
                                <v-date-picker :allowed-dates="allowedDates"
                                               v-model="calendar.endDate"></v-date-picker>
                            </v-menu>
                        </v-col>
                        <v-col class="pt-0" cols="6">
                            <v-menu
                                    :close-on-content-click="false"
                                    offset-y
                                    v-model="endTimer"
                            >
                                <template v-slot:activator="{ on }">
                                    <v-text-field
                                            :value="calendar.endTime"
                                            label="종료 시간"
                                            prepend-icon="mdi-timer"
                                            readonly
                                            v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-time-picker
                                        v-if="endTimer"
                                        v-model="calendar.endTime"
                                >
                                    <v-btn @click="selectTime"
                                           class="mx-auto"
                                    >선택
                                    </v-btn>
                                </v-time-picker>
                            </v-menu>
                        </v-col>
                    </v-row>

                    <div class="text-center">
                        <v-btn @click="submit" class="primary white--text mx-2 mt-3" text>
                            추가
                        </v-btn>
                        <v-btn @click="close" class="primary white--text mx-2 mt-3" text>
                            닫기
                        </v-btn>
                    </div>
                </v-form>
            </v-card-text>
        </v-card>
    </v-dialog>
</template>

<script>
    export default {
        data() {
            return {
                startTimer: false,
                endTimer: false,
            }
        },
        computed: {
            dialog() {
                return this.$store.state.calendar.dialog;
            },
            calendar() {
                return this.$store.state.calendar.calendar;
            },
        },
        methods: {
            submit() {
                if (this.$refs.form.validate()) {
                    this.$store.dispatch('REQUEST_ADD_EVENT', this.calendar);
                }
            },
            close() {
                this.$store.commit('CLOSE_CALENDAR_DIALOG');
            },
            selectTime() {
                this.endTimer = false;
                this.startTimer = false;
            },
            allowedDates(val) {
                let endDate = val.split('-').reduce((a, b) => a + b);
                let startDate = this.calendar.startDate.split('-').reduce((a, b) => a + b);
                return endDate >= startDate;
            }
        },

    }
</script>

<style scoped>

</style>