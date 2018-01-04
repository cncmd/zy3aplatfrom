package com.agent.handler
import com.agent.BaseHandler
import net.dongliu.requests.Requests


class AuthHandler extends BaseHandler {
    def invoke() {
        def response = [:]
        response["code"]  = this.ctx.param.code
        def data =
        [
           "appid":"15a4b139379b57",
           "token":"d8ee66e7a7d8d666186e735e6c6f7a2a",
           "type":"get_user_info",
            code:response["code"]
        ]
        
        String resp = Requests.post('http://open.51094.com/user/auth.html').forms(data).send().readToText();
        response['user'] = resp;
        return Response.format(0,"succ", response)
    }
}

