package com.agent
class ExampleHandler extends BaseHandler {
    def invoke() {
        return Response.format(0,"succ", ["greeting":"hellow I am groovy"])
    }
}
