package com.agent
import java.util.Map;
import nginx.clojure.java.NginxJavaRingHandler;
import java.net.URLDecoder;
/***
 * 程序入口 
 * @author agent.zy
 */
public class MainService implements NginxJavaRingHandler {
    public Object[] invoke(Map<String, Object> request){
        try {
           def context = getContext(request)
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
    def getURLParams(str) {
        def  ret    = [:]
        def  buf    = str.tokenize("&");
        for (def i=0; i<buf.size(); i++) {
            def token = buf[i].tokenize("=")
            ret[token[0]] = token[1]
        }
        return ret
    }
    ///get Context
    def getContext (owner) {
        /***
         * 因为处理post会影响到ngx的性能所以不处理post的方式
         */
        def context = [:]
        def query   = owner['query-string']
        
         ///快速json通道
        if(query) {
            def size = query.length()
            if(size > 4000) { /**最小8K参数*/
                return null
            }
            if(query[0..3] == "json") {/**协议必须大于10*/
                def queryString = URLDecoder.decode(query[5..-1],"UTF-8");
                context.param   = Env.json_decode(queryString);
            } else {
                context.param   = getURLParams (query)
                def apiPosition = owner.uri.indexOf(".mxml") ///mxml的协议
                 
                if(apiPosition != -1) {
                    context.param.api = owner.uri[1 .. apiPosition-1].replace("/", ".");
                }
            }
        }
        if(!context.param) {
            return null
        } 
        
        if(!context.param.api) {/**协议必须要有api字段*/
            return null
        }
        context.api    = context.param.api
        context.header = owner.headers
        return context;
    }
 }
 
