package cn.com.jr.HTUmidware.serverofdev.protocol;

import cn.com.jr.HTUmidware.serverofdev.protocol.receive.*;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author yangdd
 *接受设备的协议转换工厂类
 *
 *
 */
public class ProtocolFactory {
	//不同帧头协议集合
	private static Map<Byte, Class> map = new HashMap<Byte, Class>();
  
	//添加不同帧头协议
	static {
		//wyjc 可用配置文件方式
		map.put((byte) 104, ConversionUart.class);
		map.put((byte) 1, ReplayUART.class);
		map.put((byte) 87, EquipCodeUART.class);
		//xj
		map.put((byte) -9, ConversionUartXJ.class);
		map.put((byte)90, AlarmUartXgj.class);
	}

	public static UART createUART(byte mark, byte[] data, ChannelHandlerContext ctx) {
		Class clazz = map.get(mark);
		if (clazz != null) {
			UART uart;
			try {
				uart = (UART) clazz.newInstance();
				uart.setData(data);
				uart.parse();
				return uart;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
