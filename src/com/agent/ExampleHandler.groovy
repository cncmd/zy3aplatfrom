package com.agent


import  com.google.common.cache.CacheBuilder
import  com.google.common.cache.CacheLoader
import  java.util.concurrent.TimeUnit
import com.agent.TairManager

class ExampleHandler extends BaseHandler {
    def invoke() {
        def response = [:]
        response["version"]  = "v0.0.0.4"
        response["text"] = this.ctx;
        return Response.format(0,"succ", response)
    }
    ////tair support
//    def testTair  () {
//        def time_t = System.currentTimeMillis()
//        def tair = new TairManager();
//        tair.connect("127.0.0.1", 10086, "namespace", "daily");
//        def result = tair.getObject("A1")
//        tair.close()
//        return true
//    }
    
    ////guava support
//    def testGuava () {
//        def collectedNewsCache = CacheBuilder.newBuilder()
//        .maximumSize(1)
//        .expireAfterWrite(1, TimeUnit.MINUTES) //refresh when oldvalue valid
//        .build(new CacheLoader<String,Object>() {
//            @Override
//            public  Object load(String cacheKey) throws Exception {
//                return ["guava":"true"]
//            }
//        })
//        return collectedNewsCache.get("")
//    }
}
