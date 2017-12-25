package com.agent

@Singleton
class ExampleHandler implements IServiceHandler {
    def invoke() {
        return Response.format(0,"succ", ["greeting":"hellow I am groovy"])
    }
}
