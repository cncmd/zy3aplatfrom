package com.agent

import com.agent.handler.AuthHandler
import com.agent.handler.*

class HandlerFactory {
    static def getHandler = {
         api ->
         switch (api) {
             case "Auth.Get":
             return new AuthHandler()
             case "Example":
             return new ExampleHandler()
             default:
             return new ErrorHandler()
         }
    }
}


