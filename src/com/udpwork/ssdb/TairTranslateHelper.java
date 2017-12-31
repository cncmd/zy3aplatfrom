package com.udpwork.ssdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/***
 * @author agent.zy
 */
class TairTranslateHelper {
    /***
     * 转化Byte数组到对象
     * @param bytes
     * @return
     */
    static Object bytesToObject(byte[] bytes) throws Exception{
        Object               obj = null;
        ObjectInputStream    oIn = null;
        ByteArrayInputStream bIn = null;
        
        try {
            bIn = new ByteArrayInputStream(bytes);
            oIn = new ObjectInputStream(bIn);
            obj = oIn.readObject();
        }catch (Exception e) {
        } finally {
            oIn.close();
            bIn.close();
        }
        return obj;
    }
    
    /***
     * 转化对象到字节数组
     */
     static byte[] objectToBytes (Object obj) throws Exception{
        byte[]                bytes = null;
        ByteArrayOutputStream bOut  = null;
        ObjectOutputStream    oOut  = null;
        
        try {
            bOut = new ByteArrayOutputStream();
            oOut = new ObjectOutputStream(bOut);
            oOut.writeObject(obj);
            oOut.flush();
            bytes = bOut.toByteArray();
        }catch (Exception e) {
        } finally {
            bOut.close();
            oOut.close();
        }
        return bytes;
    }
}
