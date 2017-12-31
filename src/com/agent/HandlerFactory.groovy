package com.agent

class HandlerFactory {
    static def getHandler = {
         owner ->
         return new ExampleHandler();
    }
}
