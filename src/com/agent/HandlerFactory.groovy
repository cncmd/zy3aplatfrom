package com.agent

class HandlerFactory {
    static IServiceHandler getHandler (owner) {
         return new ExampleHandler();
    }
}
