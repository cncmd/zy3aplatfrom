package com.agent


import  com.google.common.cache.CacheBuilder
import  com.google.common.cache.CacheLoader
import  java.util.concurrent.TimeUnit


class ExampleHandler extends BaseHandler {
    def invoke() {
        
//        return Response.format(0,"succ", ["greeting":"hellow I am groovy"])
        return Response.format(0,"succ", testGuava())
    }
    
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
