package com.agent
import java.util.Map;
import nginx.clojure.java.NginxJavaRingHandler;
import com.agent.handler.Response;
import java.net.URLDecoder;
/***
 * 程序入口 
 * @author agent.zy
 */

public class MainService implements NginxJavaRingHandler {
    public Object[] invoke(Map<String, Object> request){
        def context = null
        try {
           context = getContext(request)
           if(!context) {
               return Response.format(-1, "not find context")
           }
           def handler  = HandlerFactory.getHandler.call(context.api)
           if(!handler) {
               return Response.format(-1, "not find service")
           }
           
           handler.ctx  = context
           return handler.invoke()
           
        } catch (def e) {
           return Response.format(-1, "service error", ["desc":":${e.toString()}"])
        }
        return response
    }
    
    def listKey(list) {
        return list
    }
    
    ///get Context
    def getContext (owner) {
        def query = owner['query-string']
        
        if(!query) {
            return null
        }
        def context     = [:]
        
        def queryString = URLDecoder.decode(query[5..-1],"UTF-8");
        context.param   = Env.json_decode(queryString);
        
        if(!context) {
            context['a'] = queryString
            return context
        }
        
        if(context['api']) {
            context.api   =  context.api
        }
        
        if(context['param']) {
            context.param =  context.param
        }
        context.header = owner.headers
        return context;
    }
 }
 
