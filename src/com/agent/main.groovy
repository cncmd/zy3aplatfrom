package com.agent
import java.util.Map
import nginx.clojure.java.NginxJavaRingHandler;
import com.agent.handler.Response
/***
 * 程序入口 
 * @author agent.zy
 */
public class MainService implements NginxJavaRingHandler {
    public Object[] invoke(Map<String, Object> request){
        try {
           def context  = getContext(request)
           def handler  = HandlerFactory.getHandler(context.api)
           
           if(!handler) {
               return Response.format(-1, "not find service")
           }
           
           handler.ctx  = context
           return handler.invoke()
           
        } catch (e) {
           return Response.format(-1,"error",e)
        }
        return response
    }
    
    ///get Context
    def getContext (owner) {
        def context = Env.getJson_decode(owner["query-string"]);
        if(context['api']) {
            context.api   =  context.api
        }
        if(context['param']) {
            context.param =  context.param
        }
        return context;
    }
 }