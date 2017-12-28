package com.agent

class TairTranslateHelper {
    /***
     * 转化Byte数组到对象
     * @param bytes
     * @return
     */
    static def bytesToObject(byte[] bytes) {
        def obj = null
        def bIn = null
        def oIn = null
        try {
            bIn = new ByteArrayInputStream(bytes)
            oIn = new ObjectInputStream(bIn)
            obj = oIn.readObject()
        }catch (all) {
            println(all)
        } finally {
            oIn.close()
            bIn.close()
        }
        return obj
    }
    
    /***
     * 转化对象到字节数组
     */
     static def objectToBytes (Object obj) {
        def bOut = null
        def oOut = null
        def bytes= null
        try {
            bOut = new ByteArrayOutputStream()
            oOut = new ObjectOutputStream(bOut)
            oOut.writeObject(obj)
            oOut.flush()
            bytes = bOut.toByteArray()
        }catch (all) {
            println(all)
        } finally {
            bOut.close()
            oOut.close()
        }
        return bytes
    }
}
