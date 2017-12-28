package com.agent


import  com.google.common.cache.CacheBuilder
import  com.google.common.cache.CacheLoader
import  java.util.concurrent.TimeUnit


class ExampleHandler extends BaseHandler {
    def invoke() {
        def response = [:]
        response["guava"] = testGuava()
        return Response.format(0,"succ", response)
    }
    ////guava support
    def testGuava () {
        def collectedNewsCache = CacheBuilder.newBuilder()
        .maximumSize(1)
        .expireAfterWrite(1, TimeUnit.MINUTES) //refresh when oldvalue valid
        .build(new CacheLoader<String,Object>() {
            @Override
            public  Object load(String cacheKey) throws Exception {
                return ["guava":"true"]
            }
        })
        return collectedNewsCache.get("")
    }
}
