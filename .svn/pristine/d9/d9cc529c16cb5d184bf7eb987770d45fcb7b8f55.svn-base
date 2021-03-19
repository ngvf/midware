package cn.com.jr.HTUmidware.controller.UDPController;

import cn.com.jr.HTUmidware.controller.AbstractController;
import cn.com.jr.HTUmidware.controller.TCPController.SettingController;
import cn.com.jr.HTUmidware.serverofdev.protocol.UART;
import cn.com.jr.HTUmidware.serverofdev.protocol.receive.AlarmUartXgj;
import cn.com.jr.HTUmidware.util.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 限高架处理
 */
public class XgjController extends AbstractController {

    private final static String method = "/web/httpclient.do";
    private static final Logger logger = LoggerFactory.getLogger(SettingController.class);
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

    private String type;
    private String deviceId;
    private byte[] devIds;

    public XgjController(ChannelHandlerContext ctx, UART info){
        super(ctx, info);
    }

    @Override
    public String executor() {
        if(info instanceof AlarmUartXgj) {
            AlarmUartXgj alarmUART = (AlarmUartXgj) this.info;
            this.type = alarmUART.getTypeCode();
            this.deviceId = alarmUART.getEquipmentCode();
            this.devIds= alarmUART.subBytes(alarmUART.getData(),1,9);

            Map<String, Object> map = new HashMap<String, Object>();

            map.put("deviceId", this.deviceId);
            map.put("type", this.type);
            map.put("content", alarmUART.getParseAfterData());
            map.put("head",String.valueOf((int)alarmUART.getMark()[0]));

            String param = "data=[" + JSONObject.toJSONString(map) + "]";
            String result = HttpUtil.httpRequest(getUrl()+method, "POST", param);

            logger.info("向web端发送数据:" + JSONObject.toJSONString(map));

            return result;
        }
        return "";
    }

    public byte[] send() {
        if("2".equals(this.type)){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return buildData(deviceId);
        }
        return null;
    }

    /**
     * 拼接1号协议进行回复
     * @param deviceId
     * @return
     */
    private byte[] buildData(String deviceId) {
        byte[] timeByte = getTimeByte(new Date());
        byte[] bytes = new byte[20];
        for(byte b:bytes){
            b=0x20;
        }
        try{
            bytes[0]=0x5A;
            upArray(this.devIds,1,bytes);
            bytes[10]=0x01;
            bytes[11]=0x06;
            upArray(timeByte,12,bytes);
            bytes[18]=0x0D;
            bytes[19]=0x0A;
        }catch (Exception e){
            return null;
        }
        return bytes;
    }

    /**
     * 获取当前时间String
     * @param date
     * @return
     */
    private byte [] getTimeByte(Date date){
        if(date!=null){
            String s = this.formatter.format(date);
            if(s.length()==14){
                s=s.substring(2);
            }
            try {

                byte[] sez=new byte[s.length()/2];
                List<Integer> e=new ArrayList<Integer>();
                for(int i=0;i<s.length();i+=2){
                    e.add(Integer.valueOf(s.substring(i, i + 2)));
                }
                if(e.isEmpty()) return null;

                for(int j=0;j<e.size();j++){
                    sez[j]=(byte)((int)e.get(j));
                }
                return sez;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将数组插入到指定数组的指定位置
     * @param bytes 插入数据组
     * @param idx   下标位置
     * @param datas 被插入数据组
     * @return
     */
    private void upArray(byte[] bytes,int idx,byte[] datas){
        if(bytes.length>(datas.length-idx))
            return;
        for (int i=0;i<bytes.length;i++) {
            datas[i+idx]=bytes[i];
        }
    }

}
