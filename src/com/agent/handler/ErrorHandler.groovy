package com.agent.handler
import  com.google.common.cache.CacheBuilder
import  com.google.common.cache.CacheLoader
import  java.util.concurrent.TimeUnit

import com.agent.BaseHandler
import com.agent.Response
import com.agent.TairManager
/***
 * 默认没有找到该资源
 * @author agent.zy
 */
class ErrorHandler extends BaseHandler {
    def invoke() {
        return Response.format(-1,"not find service")
    }
}
