package com.udpwork.ssdb;

import java.util.List;
import java.util.LinkedHashMap;
public class SSDB{
    public Link link;

    public SSDB(String host, int port) throws Exception{
        this(host, port, 0);
    }

    public SSDB(String host, int port, int timeout_ms) throws Exception{
        link = new Link(host, port, timeout_ms);
    }
    
    public void close(){
        link.close();
    }

    /***
     * 设置object
     * @param key
     * @param obj
     */
    public void tset(String key, Object obj) throws Exception{
        byte[] data = TairTranslateHelper.objectToBytes(obj);
        set(key.getBytes(), data);
    }
    
    /***
     * 增加方式
     * bounds [low,upper]
     * @param key
     * @param by
     * 默认为0
     */
     public long tincr (String key, long value, long upperBound, int exprieTime) throws Exception{
        long  count      = 0;
        LinkedHashMap<String,Long> entry = null;
        
        try {
            entry =  (LinkedHashMap<String,Long>) this.tget(key);
        }catch (Exception e) {
            entry = new LinkedHashMap<String,Long>();
            entry.put("value", 0L);
            link.request("expire",key.getBytes(),new Long(exprieTime).toString().getBytes());
        }
        count = entry.get("value");
        count ++ ;
        if(count > upperBound) {
            throw new Exception("UpperBounts Exception");
        }
        entry.put("value", count);
        this.tset(key, entry);
        return count;
     }
    
    
    public Object tget(String key) throws Exception{
         byte[] data = this.get(key);
         Object obj  = TairTranslateHelper.bytesToObject(data);
         return obj;
    }
     

    
   public static byte[] intToByteArray(int a) {   
        return new byte[] {   
                (byte) ((a >> 24) & 0xFF),   
                (byte) ((a >> 16) & 0xFF),      
                (byte) ((a >> 8) & 0xFF),      
                (byte) (a & 0xFF)   
            };   
     } 
    
    public static int byteArrayToInt(byte[] b) {   
        return   b[3] & 0xFF |   
                    (b[2] & 0xFF) << 8 |   
                    (b[1] & 0xFF) << 16 |   
                    (b[0] & 0xFF) << 24;   
    }
    /* auth */

    public void auth(String password) throws Exception{
        Response resp = link.request("auth", password);
        if(resp.ok()){
            return;
        }
        resp.exception();
    }

    /* kv */

    
    
    public void set(byte[] key, byte[] val) throws Exception{
        Response resp = link.request("set", key, val);
        if(resp.ok()){
            return;
        }
        resp.exception();
    }
    
    
    public void set(String key, byte[] val) throws Exception{
        set(key.getBytes(), val);
    }
    
    public void set(String key, String val) throws Exception{
        set(key, val.getBytes());
    }

    
    public void del(byte[] key) throws Exception{
        Response resp = link.request("del", key);
        if(resp.ok()){
            return;
        }
        resp.exception();
    }
    
    public void del(String key) throws Exception{
        del(key.getBytes());
    }

    /***
     * 
     * @param key
     * @return null if not found
     * @throws Exception
     */
    public byte[] get(byte[] key) throws Exception{
        Response resp = link.request("get", key);
        if(resp.not_found()){
            return null;
        }
        if(resp.raw.size() != 2){
            throw new Exception("Invalid response");
        }
        if(resp.ok()){
            return resp.raw.get(1);
        }
        resp.exception();
        return null;
    }
    
    /***
     * 
     * @param key
     * @return null if not found
     * @throws Exception
     */
    public byte[] get(String key) throws Exception{
        return get(key.getBytes());
    }

    private Response _scan(String cmd, String key_start, String key_end, int limit) throws Exception{
        if(key_start == null){
            key_start = "";
        }
        if(key_end == null){
            key_end = "";
        }
        Response resp = link.request(cmd, key_start, key_end, (new Integer(limit)).toString());
        if(!resp.ok()){
            resp.exception();
        }
        resp.buildMap();
        return resp;
    }
    
    public Response scan(String key_start, String key_end, int limit) throws Exception{
        return _scan("scan", key_start, key_end, limit);
    }
    
    public Response rscan(String key_start, String key_end, int limit) throws Exception{
        return _scan("rscan", key_start, key_end, limit);
    }
    

    
    public long incr(String key, long by) throws Exception{
        Response resp = link.request("incr", key, (new Long(by)).toString());
        if(!resp.ok()){
            resp.exception();
        }
        if(resp.raw.size() != 2){
            throw new Exception("Invalid response");
        }
        long ret = 0;
        ret = Long.parseLong(new String(resp.raw.get(1)));
        return ret;
    }
    
    /* hashmap */

    public void hset(String name, byte[] key, byte[] val) throws Exception{
        Response resp = link.request("hset", name.getBytes(), key, val);
        if(resp.ok()){
            return;
        }
        resp.exception();
    }
    
    public void hset(String name, String key, byte[] val) throws Exception{
        this.hset(name, key.getBytes(), val);
    }
    
    public void hset(String name, String key, String val) throws Exception{
        this.hset(name, key, val.getBytes());
    }
    
    public void hdel(String name, byte[] key) throws Exception{
        Response resp = link.request("hdel", name.getBytes(), key);
        if(resp.ok()){
            return;
        }
        resp.exception();
    }
    
    public void hdel(String name, String key) throws Exception{
        this.hdel(name, key.getBytes());
    }

    /**
     * 
     * @param name
     * @param key
     * @return null if not found
     * @throws Exception
     */
    public byte[] hget(String name, byte[] key) throws Exception{
        Response resp = link.request("hget", name.getBytes(), key);
        if(resp.not_found()){
            return null;
        }
        if(resp.raw.size() != 2){
            throw new Exception("Invalid response");
        }
        if(resp.ok()){
            return resp.raw.get(1);
        }
        resp.exception();
        return null;
    }
    
    /**
     * 
     * @param name
     * @param key
     * @return null if not found
     * @throws Exception
     */
    public byte[] hget(String name, String key) throws Exception{
        return hget(name, key.getBytes());
    }

    private Response _hscan(String cmd, String name, String key_start, String key_end, int limit) throws Exception{
        if(key_start == null){
            key_start = "";
        }
        if(key_end == null){
            key_end = "";
        }
        Response resp = link.request(cmd, name, key_start, key_end, (new Integer(limit)).toString());
        if(!resp.ok()){
            resp.exception();
        }
        for(int i=1; i<resp.raw.size(); i+=2){
            byte[] k = resp.raw.get(i);
            byte[] v = resp.raw.get(i+1);
            resp.keys.add(k);
            resp.items.put(k, v);
        }
        return resp;
    }
    
    public Response hscan(String name, String key_start, String key_end, int limit) throws Exception{
        return this._hscan("hscan", name, key_start, key_end, limit);
    }
    
    public Response hrscan(String name, String key_start, String key_end, int limit) throws Exception{
        return this._hscan("hrscan", name, key_start, key_end, limit);
    }
    
    public long hincr(String name, String key, long by) throws Exception{
        Response resp = link.request("hincr", name, key, (new Long(by)).toString());
        if(!resp.ok()){
            resp.exception();
        }
        if(resp.raw.size() != 2){
            throw new Exception("Invalid response");
        }
        long ret = 0;
        ret = Long.parseLong(new String(resp.raw.get(1)));
        return ret;
    }
    
    /* zset */

    public void zset(String name, byte[] key, long score) throws Exception{
        Response resp = link.request("zset", name.getBytes(), key, (new Long(score)).toString().getBytes());
        if(resp.ok()){
            return;
        }
        resp.exception();
    }
    
    public void zset(String name, String key, long score) throws Exception{
        zset(name, key.getBytes(), score);
    }
    
    public void zdel(String name, byte[] key) throws Exception{
        Response resp = link.request("zdel", name.getBytes(), key);
        if(resp.ok()){
            return;
        }
        resp.exception();
    }
    
    public void zdel(String name, String key) throws Exception{
        this.zdel(name, key.getBytes());
    }
    
    /**
     * 
     * @param name
     * @param key
     * @return null if not found.
     * @throws Exception
     */
    public Long zget(String name, byte[] key) throws Exception{
        Response resp = link.request("zget", name.getBytes(), key);
        if(resp.not_found()){
            return null;
        }
        if(resp.raw.size() != 2){
            throw new Exception("Invalid response");
        }
        if(resp.ok()){
            return Long.parseLong(new String(resp.raw.get(1)));
        }
        resp.exception();
        return new Long(0);
    }

    /**
     * 
     * @param name
     * @param key
     * @return null if not found.
     * @throws Exception
     */
    public Long zget(String name, String key) throws Exception{
        return zget(name, key.getBytes());
    }

    private Response _zscan(String cmd, String name, String key, Long score_start, Long score_end, int limit) throws Exception{
        if(key == null){
            key = "";
        }
        String ss ="";
        if(score_start != null){
            ss = score_start.toString();
        }
        String se ="";
        if(score_end != null){
            se = score_end.toString();
        }
        Response resp = link.request(cmd, name, key, ss, se, (new Integer(limit)).toString());
        if(!resp.ok()){
            resp.exception();
        }
        for(int i=1; i<resp.raw.size(); i+=2){
            byte[] k = resp.raw.get(i);
            byte[] v = resp.raw.get(i+1);
            resp.keys.add(k);
            resp.items.put(k, v);
        }
        return resp;
    }
    
    public Response zscan(String name, String key, Long score_start, Long score_end, int limit) throws Exception{
        return this._zscan("zscan", name, key, score_start, score_end, limit);
    }
    
    public Response zrscan(String name, String key, Long score_start, Long score_end, int limit) throws Exception{
        return this._zscan("zrscan", name, key, score_start, score_end, limit);
    }
    
    public long zincr(String name, String key, long by) throws Exception{
        Response resp = link.request("zincr", name, key, (new Long(by)).toString());
        if(!resp.ok()){
            resp.exception();
        }
        if(resp.raw.size() != 2){
            throw new Exception("Invalid response");
        }
        long ret = 0;
        ret = Long.parseLong(new String(resp.raw.get(1)));
        return ret;
    }

    /****************/
    
    public Response multi_get(String...keys) throws Exception{
        Response resp = link.request("multi_get", keys);
        if(!resp.ok()){
            resp.exception();
        }
        resp.buildMap();
        return resp;
    }
    
    public Response multi_get(byte[]...keys) throws Exception{
        Response resp = link.request("multi_get", keys);
        if(!resp.ok()){
            resp.exception();
        }
        resp.buildMap();
        return resp;
    }
    
    public void multi_set(String...kvs) throws Exception{
        if(kvs.length % 2 != 0){
            throw new Exception("Invalid arguments count");
        }
        Response resp = link.request("multi_set", kvs);
        if(!resp.ok()){
            resp.exception();
        }
    }
    
    public void multi_set(byte[]...kvs) throws Exception{
        if(kvs.length % 2 != 0){
            throw new Exception("Invalid arguments count");
        }
        Response resp = link.request("multi_set", kvs);
        if(!resp.ok()){
            resp.exception();
        }
    }
    
    public Response multi_del(String...keys) throws Exception{
        Response resp = link.request("multi_del", keys);
        if(!resp.ok()){
            resp.exception();
        }
        resp.buildMap();
        return resp;
    }
    
    public Response multi_del(byte[]...keys) throws Exception{
        Response resp = link.request("multi_del", keys);
        if(!resp.ok()){
            resp.exception();
        }
        resp.buildMap();
        return resp;
    }
    
    public Response request(String cmd, byte[]...params) throws Exception{
        return link.request(cmd, params);
    }

    public Response request(String cmd, String...params) throws Exception{
        return link.request(cmd, params);
    }
    
    public Response request(String cmd, List<byte[]> params) throws Exception{
        return link.request(cmd, params);
    }
}
