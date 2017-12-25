//verion=1.0.0.1 

 // agent
import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;

class Env {
    static def json_encode = { obj -> new JsonOutput().toJson(obj) }
    static def json_decode = { text-> new JsonSlurper().parseText(text)}
    static def log = {text-> println(text)}
} // agent
class ExampleHandler implements IServiceHandler {
    def invoke() {
        return Response.format(0,"succ", ["greeting":"hellow I am groovy"])
    }
}
 // agent

class HandlerFactory {
    static IServiceHandler getHandler (owner) {
         return new ExampleHandler();
    }
}
 // agent

interface IServiceHandler {
      def invoke();
}



 // agent

interface IState{
    def enter (owner) ;
    def execute(owner);
    def exit(owner);
}
 // agent
import java.util.Map
import nginx.clojure.java.NginxJavaRingHandler;
 // agent.handler.Response
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
 } // agent
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
/**
 * @author agent.zy
 */

 // agent

class StateMechine {
    def currState;
    def prevState;
    def owner;
    
    StateMechine (ow) {
        owner = ow;
    }
    
    def revertPreState() {
        this.changeState(this.prevState);
    }
    
    def setPrevState(state) {
        this.prevState = state;
    }
    
    def getCurrentState() {
        return this.currState;
    }
    
    def changeState(state) {
        this.prevState = this.currState;
        if(this.prevState) {
            this.prevState.exit(this.owner);
        }
        this.currState = state;
        this.currState.enter(this.owner);
    }
    
    def update() {
        if(this.currState) {
            this.currState.execute(this.owner);
        }
    }
}

 // agent
import java.util.Map
import nginx.clojure.java.NginxJavaRingHandler;
 // agent.handler.Response
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