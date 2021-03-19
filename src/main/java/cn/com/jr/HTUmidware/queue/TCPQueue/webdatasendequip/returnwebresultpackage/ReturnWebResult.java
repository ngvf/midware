package cn.com.jr.HTUmidware.queue.TCPQueue.webdatasendequip.returnwebresultpackage;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.jr.HTUmidware.serverofdev.protocol.UART;


/**
 * @author ydd
 * 返回到web端结果类
 */
public class ReturnWebResult {
	private static final Logger logger = LoggerFactory.getLogger(ReturnWebResult.class);
	private volatile static ReturnWebResult returnWebResult;

	private ReturnWebResult() {

	}

	public static ReturnWebResult getInstance() {
		if (returnWebResult == null) {
			synchronized (ReturnWebResult.class) {
				if (returnWebResult == null) {
					returnWebResult = new ReturnWebResult();
				}
			}
		}
		return returnWebResult;
	}

	public void retWebSuccess(String id, Channel cannel, String data) {
		cannel.writeAndFlush("{'state':true,'msg':'SUCCESS','id':'" + id + "','type':'1'}\r\n");
		logger.info("服务端向设备：" + id + " 发送数据：" + data);
	}

	public void retIsEquipIdUnregister(String id, Channel cannel, UART uart) throws Exception {
		cannel.writeAndFlush("{'state':false,'msg':' Equipment Number :" + id
				+ " Unregistered ','id':'" + id + "','type':'2'}\r\n");
		logger.info("设备:" + id + " 未在本系统注册！,中间件接收到web平台的数据为：" + uart.toString());
		throw new Exception("设备:" + id + " 未在本系统注册！,中间件接收到web平台的数据为：" + uart.toString());
	}

	public void retIsParseError(String id, Channel cannel, UART uart) throws Exception{
		cannel.writeAndFlush("{'state':false,'msg':' Protocol error, please check again ','id':'"
				+ id + "','type':'3'}\r\n");
		logger.info("web端协议解析错误!中间件接收到web平台的数据为：" + uart.toString());
		throw new Exception("web端协议解析错误!中间件接收到web平台的数据为：" + uart.toString());
	}

	public void retIsEquipIdIsNULL(String id, Channel cannel, UART uart) throws Exception {
		cannel.writeAndFlush("{'state':false,'msg':' Equipment ID cannot be empty','id':'" + id
				+ "','type':'4'}\r\n");
		logger.info("设备id不能为空!,中间件接收到web平台的数据为：" + uart.toString());
		throw new Exception("设备id不能为空!,中间件接收到web平台的数据为：" + uart.toString());
	}

	public void retIsNoOperationSystem(String type, Channel cannel, String value) throws Exception {
		cannel.writeAndFlush("{'state':false,'msg':' No operation of this type','type':'5'}\r\n");
		logger.info("没有" + type + "系统类型！,中间件接收到web平台的数据为：" + value);
		throw new Exception("没有" + type + "系统类型！,中间件接收到web平台的数据为：" + value);
	}

}
