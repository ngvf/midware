package cn.com.jr.HTUmidware.serverofweb.protocol;




import java.util.HashMap;
import java.util.Map;

import cn.com.jr.HTUmidware.serverofdev.protocol.send.DateSettingUART;
import cn.com.jr.HTUmidware.serverofdev.protocol.send.SendUARTEquipment;
import cn.com.jr.HTUmidware.serverofdev.protocol.send.SendUARTEquipment_XJ;

/**
 * 
 * @author yangdd
 * 根据web发送过来的类型，来决定使用哪一个系统向指定设备发送
 *
 */
public abstract class Type {

    private final static Map<String, Class> map = new HashMap<>();

    static {
        map.put("1", DateSettingUART.class);//时间同步协议
        map.put("2", SendUARTEquipment.class);//钢轨位移系统
        map.put("iscb", SendUARTEquipment_XJ.class);//限界系统
    }

    public static Class getClazz(String type) {
        return map.get(type);
    }

}
