package cn.com.jr.HTUmidware.util;

import cn.com.jr.HTUmidware.serverofdev.supagreementserver.TCP.ServerOfDevContainer;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoCalibrationTime implements Runnable{

    private final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
//    	Calendar cal = Calendar.getInstance();//判断只有周一的时候开始进行
//		if(cal.get(Calendar.DAY_OF_WEEK)!=1)
//			return;
		for (Channel channel : ServerOfDevContainer.group) {
            String id = ServerOfDevContainer.sourceIds.get(channel);
            if (Tools.isNotEmpty(id)) {
                if (channel.isActive()) {
                    //调用时间下发
                	byte[] assembleTimeUART = Tools.assembleTimeUART(id);
					channel.writeAndFlush(assembleTimeUART);
                	System.out.println("进行时间校准"+Arrays.toString(assembleTimeUART));
                }

            }
        }
	}
//	/**
//	 * 向指定数组插入指定数据
//	 * @author yuhy
//	 */
//	private int insertArr(byte[] byt, byte[] bytes, int idx) {
//		if (bytes != null) {
//			for (int i = 0; i < bytes.length; i++) {
//				byt[i + idx] = bytes[i];
//			}
//			return idx + bytes.length;
//		}
//		return idx;
//	}
//
//	/**
//	 * 组装时间协议数据组
//	 * @author yuhy
//	 */
//	private byte[] assembleTimeUART(String id){
//
//		DateSettingUART date=new DateSettingUART();
//
//		byte[] TimeUART=new byte[20];
//		for(byte b:TimeUART){
//			b=0x20;
//		}
//		insertArr(TimeUART,"h".getBytes(),0);
//		insertArr(TimeUART,id.getBytes(), 1);
//		TimeUART[10] = (byte)1;
//		TimeUART[11]=(byte)6;
//		insertArr(TimeUART,Tools.timeFormat(new Date()),12);
//		TimeUART[18] = 0x0D;
//		TimeUART[19] = 0x0A;
//		return TimeUART;
//	}
	
	public void executer(){
        scheduledThreadPool.scheduleAtFixedRate(this, 0, 1, TimeUnit.DAYS);//首次延迟1分钟 每隔30秒执行一次
	}
	
}
