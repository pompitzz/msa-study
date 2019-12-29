import {requestJoinMember, requestLogin} from "../apis/api";
import {router} from "../routes/route";

export default {
    async REQUEST_JOIN(context, member) {
        try {
            const response = await requestJoinMember(member);
            alert('회원가입을 성공하였습니다');
            console.log(member);
            await router.push('/login');
            return response;

        } catch (e) {
            alert('회원가입이 실패하였습니다.');
        }
    },

    async REQUEST_LOGIN(context, info){
        try{
            const response = await requestLogin(info);
            alert('로그인에 성공하였습니다');
            console.log(response);
            context.commit('SET_LOGIN_USER', response.data);
            return response;
        }catch (e) {
            alert('로그인 실패!');
            console.log(e);
        }
    }
}