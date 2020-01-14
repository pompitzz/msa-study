import {queryMembers} from "../apis/admin_api";

const state = {
};

const getters = {
};

const mutations = {
};

const actions = {
    async QUERY_MEMBERS_FOR_ADMIN(context) {
        try {
            const response = await queryMembers();
            return response.data;
        } catch (e) {
            context.commit('OPEN_MODAL', {title: '사용자 조회 실패.', content: `사용자 조회에 실패했습니다.` + e, option: '재시도',})
        }
    },
};

export default {mutations, state, actions, getters};