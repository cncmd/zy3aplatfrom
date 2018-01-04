package com.agent.handler
import  com.google.common.cache.CacheBuilder
import  com.google.common.cache.CacheLoader
import  java.util.concurrent.TimeUnit

import com.agent.BaseHandler
import com.agent.Response
import com.agent.TairManager

class ExampleHandler extends BaseHandler {
    def invoke() {
        def response = [:]
        response["version"]  = Env.VERSION
        return Response.format(0,"Example", response)
    }
}
