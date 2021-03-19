package cn.com.jr.HTUmidware.util;

import cn.com.jr.HTUmidware.configuration.ProtocolConfig;
import cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy.OneByteToHexToDeci;
import cn.com.jr.HTUmidware.serverofdev.protocol.send.DateSettingUART;

import java.text.SimpleDateFormat;
import java.util.*;

public class Tools {

    private static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");


    public static boolean isNullEmpty(String id) {
		if(id == null || id.length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
     * 判断是否为空
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }

        if (o instanceof String) {
            o = ((String) o).trim();
            return o.equals("") || o.equals("null");
        }
        return false;
    }

    /**
     * 根据时间获取 byte组
     * @param date
     * @return
     */
    public static  byte[] timeFormat(Date date) {
        if (date != null) {
            String s = yyyyMMddHHmmss.format(date);
            if (s.length() == 14) {
                s = s.substring(2);
            }
            try {

                byte[] sez = new byte[s.length() / 2];
                List<Integer> e = new ArrayList<Integer>();
                for (int i = 0; i < s.length(); i += 2) {
                    e.add(Integer.valueOf(s.substring(i, i + 2)));
                }
                if (e.isEmpty()) return null;

                for (int j = 0; j < e.size(); j++) {
                    sez[j] = (byte) ((int) e.get(j));
                }
                return sez;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 组装时间协议数据组
     * @author yuhy
     */
    public static byte[] assembleTimeUART(String id){

        DateSettingUART date=new DateSettingUART();

        byte[] TimeUART=new byte[20];
        for(byte b:TimeUART){
            b=0x20;
        }
        insertArr(TimeUART,"h".getBytes(),0);
        insertArr(TimeUART,id.getBytes(), 1);
        TimeUART[10] = (byte)1;
        TimeUART[11]=(byte)6;
        insertArr(TimeUART,Tools.timeFormat(new Date()),12);
        TimeUART[18] = 0x0D;
        TimeUART[19] = 0x0A;
        return TimeUART;
    }

    /**
     * 向指定数组插入指定数据
     * @author yuhy
     */
    public static int insertArr(byte[] byt, byte[] bytes, int idx) {
        if (bytes != null) {
            for (int i = 0; i < bytes.length; i++) {
                byt[i + idx] = bytes[i];
            }
            return idx + bytes.length;
        }
        return idx;
    }

    /**
     * 根据配置文件获取指定长度 来验证数据帧头是否正确
     * @param data 所有数据
     * @return
     */
    public static boolean isUARTInfo(byte[] data) {

        Map<String, Integer> websystemMap = ProtocolConfig.getInstace().getWebsystemMap();

        for(String key:websystemMap.keySet()){
            if(key.equals(OneByteToHexToDeci.bytesToHexString(Arrays.copyOf(data,websystemMap.get(key)))))
                return false;
        }

        return true;
    }

    /**
     * 判断是否不为空
     * @param o
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

}
