import {router} from "../routes/route";

export default {
    REDIRECT_LOGIN(){
        router.push('/login').catch(e => console.log(e));
    }
}