package cn.com.jr.HTUmidware.proxy;

import cn.com.jr.HTUmidware.controller.Controller;
import cn.com.jr.HTUmidware.controller.TCPController.SettingController;
import cn.com.jr.HTUmidware.controller.TCPController.SettingControllerXJ;
import cn.com.jr.HTUmidware.controller.UDPController.XgjController;
import cn.com.jr.HTUmidware.serverofdev.protocol.UART;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 协议调用web平台工厂
 */
public class SimpleFactory {

	// 不同帧头协议集合
	private static Map<Byte, Class> controllerMap = new HashMap<Byte, Class>();

	// 不同帧头协议初始化
	static {
		controllerMap.put((byte) 104, SettingController.class);
		controllerMap.put((byte) -9, SettingControllerXJ.class);
		//UDP限高架
		controllerMap.put((byte)90, XgjController.class);
	}

	public static Controller createController(ChannelHandlerContext ctx, UART uart) {

		byte mark = uart.getMark()[0];
		Class clazz = controllerMap.get(mark);
		Controller controller = null;
		if (clazz != null) {
			try {
				Constructor constructor = clazz.getConstructor(ChannelHandlerContext.class,
						UART.class);
				controller = (Controller) constructor.newInstance(ctx, uart);

				//切面
				ProxyController proxy = new ProxyController(controller);
				Controller controllerProxy = proxy.getInstace();
				return controllerProxy;
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
