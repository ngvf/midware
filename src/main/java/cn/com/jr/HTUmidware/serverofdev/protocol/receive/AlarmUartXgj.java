package cn.com.jr.HTUmidware.serverofdev.protocol.receive;

import cn.com.jr.HTUmidware.serverofdev.protocol.AbstractUART;
import cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy.OneByteToHexToDeci;

import java.util.Arrays;

/**
 * 限高架2.0协议处理
 * 协议:
 * 0x5A	帧头	1	帧的起始编码
 * 0x30...0x39	设备编码	9	设备唯一性编码：
 * 字节1：标识码
 * 字节2：年份高
 * 字节3：年份低
 * 字节4：批号高
 * 字节5：批号低
 * 字节6~9：设备序列（0001~9999）
 * 0xHH	类型码	1	0x01—0xFF
 * 0xHH	数据长度	1	0x00—0xFF
 * 	0xHH…	0xHH	数据	N	实现功能的数据
 * 	    实现功能的数据 主要是
 * 0x86	帧尾	1	结束码
 * @author yuhy
 */
public class AlarmUartXgj  extends AbstractUART {

    //碰撞后立即上传4号协议
    public static final String collisionWarning="4";
    //碰撞后 30秒,检测当前硬件位置进行数据上传
    public static final String testingWarning="6";

    @Override
    public void parse() {
        setEquipmentCode(asciiString(subBytes(this.data,1,9)));//设置装置ID
        setTypeCode(String.valueOf((int)this.data[10]));
        setDataLength(String.valueOf((int)this.data[11]));
        setFrameHeader(OneByteToHexToDeci.bytesToHexString(getMark()));

        if(collisionWarning.equals(this.getTypeCode()) || testingWarning.equals(this.getTypeCode())){
            String content = getContent();
            setContentData(content.getBytes());
            setParseAfterData(content.substring(0, content.length() - 1));
        }else{
            byte[] bytes = subBytes(this.data, 12, this.data.length - (12 + 3));
            setContentData(bytes);//减去开始下标位加上1等于去掉止位符数据
            setParseAfterData(Arrays.toString(bytes).trim().replace(" ","").
                    replace("[","").replace("]",""));
        }

    }

    /**
     * 截取部分数组值
     * @param src 数组
     * @param begin 开始下标
     * @param count 截取个数
     * @return
     */
    public byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i=begin; i<begin+count; i++)
            bs[i-begin] = src[i];
        return bs;
    }

    /**
     * 拼接4,6号协议的上传数据
     * @return
     */
    private String getContent(){
        StringBuilder sb=new StringBuilder();
        sb.append(asciiString(subBytes(this.data, 12, 4))+",");
        sb.append(asciiString(subBytes(this.data, 16, 4))+",");
        sb.append(asciiString(subBytes(this.data, 20, 4))+",");
        sb.append(Arrays.toString(subBytes(this.data, 24, 3))+",");
        sb.append(Arrays.toString(subBytes(this.data,27,6))+",");
        return sb.toString().trim().replace(" ","").
                replace("[","").replace("]","");
    }
}
