package com.agent

import com.udpwork.ssdb.SSDB

class TairManager {
    
    SSDB ssdb = null
    def  ns   =""
    def  env  ="daily"
    
    def buildKey (pkey) {
       return "${env}/${ns}/${pkey}"
    }
    
    def connect (ip , port, namespace, env) {
       ssdb = new SSDB(ip , port)
       ns   = namespace;
    }
    
    def setObject(key , obj) {
        def pkey = buildKey(key)
        try {
            ssdb.tset(pkey, obj)
        } catch (Exception all) {
            println(all)
            return false
        } 
        return true
    }
    
    def getObject(pkey) {
        def obj  = null
        try {
            obj = ssdb.tget(buildKey(pkey))
        } catch (Exception all) {
            println(all)
        }
        return obj
    }
    def close () {
        ssdb.close()
    }
    
}
