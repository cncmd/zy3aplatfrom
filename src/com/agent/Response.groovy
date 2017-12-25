package com.agent
/***
 * @author agent.zy
 */
class Response {
    public static final HTTP_STATUS_OK=200
    public static final HTTP_CONTEN_TYPE   =["Content-Type":"text/json"]
    
    static format(code) {
        def obj = [code:code];
        return [HTTP_STATUS_OK, 
                HTTP_CONTEN_TYPE, 
                Env.json_encode(obj)]
    }
    
    static format(code,desc) {
        def obj = [code:code, desc:desc];
        return [HTTP_STATUS_OK, 
                HTTP_CONTEN_TYPE, 
                Env.json_encode(obj)]
    }
    
    static format(code, desc, data) {
        def obj = [code:code, desc:desc, data:data];
        return [HTTP_STATUS_OK, 
                HTTP_CONTEN_TYPE, 
                Env.json_encode(obj)]
    }
}
