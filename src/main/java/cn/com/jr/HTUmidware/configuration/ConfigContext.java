package cn.com.jr.HTUmidware.configuration;

import cn.com.jr.HTUmidware.queue.TCPQueue.equipdatasendweb.EquipDataSendWebTask;
import cn.com.jr.HTUmidware.queue.TCPQueue.webdatasendequip.WebDataSendEquipTask;
import cn.com.jr.HTUmidware.queue.UDPQueue.UDPSendToWebQueue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件类
 * 
 */
public class ConfigContext {

	private static Properties properties;

	private static ProtocolConfig protocolConfig =ProtocolConfig.getInstace();
	private static ProjectConfig projectConfig = new ProjectConfig();
	static {

		try {
			InputStream inputStream = ConfigContext.class.getClassLoader().getResourceAsStream(
					"configuration.properties");
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static ConfigContext configContext = new ConfigContext();

	public static ConfigContext getInstace() {
			return configContext;
	}

	/**
	 * 服务端口
	 */
	private int serverPort;
	private int socketPort;

	// // web服务器项目标记
	// private String webserverMark;
	// // web服务器接口地址
	// private String webserverUrl;

	private ConfigContext() {
		load();
		// 设备数据发送到web平台使用多线程来做 这里是启动发送任务进入监听状态，队列中有任务就发送
		EquipDataSendWebTask edswt = new EquipDataSendWebTask();
		Thread edswtThread1 = new Thread(edswt, "edswtThread1");
		edswtThread1.start();

		UDPSendToWebQueue UDP=new UDPSendToWebQueue();
		Thread UdpThread1 = new Thread(UDP, "UdpThread1");
		UdpThread1.start();

		//web平台发送数据设备     使用多线程来做 这里是启动发送任务进入监听状态，队列中有任务就发送
		WebDataSendEquipTask wset=new WebDataSendEquipTask();
		Thread wsetThread1 = new Thread(wset, "wsetThread1");
		wsetThread1.start();
		
//		new AutoCalibrationTime().executer();
	}

	/**
	 * 读取配置文件
	 */
	private void load() {
		// 端口
		serverPort = Integer.valueOf(properties.getProperty("server.port", "8080"));
		socketPort = Integer.valueOf(properties.getProperty("socket.port", "8089"));

		// web服务器项目标记
		String webserverMark = properties.getProperty("webserver.mark");
		// web服务器接口地址
		String webserverUrl = properties.getProperty("webserver.url");
		String[] webserverMarkSpl = webserverMark.split(",");
		String[] webserverUrlSpl = webserverUrl.split(",");
		int webMarkNum = webserverMarkSpl.length;
		int webUrlNum = webserverUrlSpl.length;
		for (int i = 0; i < webMarkNum; i++) {
			projectConfig.addProjectAddressMap(webserverMarkSpl[i], webserverUrlSpl[i]);
		}

		// 协议标识符
		String property = properties.getProperty("receive.container");
		for (String item : property.split(",")) {
			protocolConfig.addReceiveType(Byte.valueOf(item));
		}

		// 协议长度
		for (Byte item : protocolConfig.getReceiveContainer()) {
			String s = properties.getProperty("receive.num." + item);
			protocolConfig.getReceiveMap().put(item, Integer.valueOf(s));
		}

		//存储每个系统的协议头节取长度 和对应的协议开头是什么
		String[] webserverMarks =properties.getProperty("websystem.mark").split(",");
		String[] websystemNum = properties.getProperty("websystem.len").split(",");
		for(int i=0;i<websystemNum.length;i++){
			protocolConfig.addWebsystemMap(webserverMarks[i],Integer.valueOf(websystemNum[i]));
		}


	}




	public int getServerPort() {
		return this.serverPort;
	}

	public int getSocketPort() {
		return socketPort;
	}

	public ProtocolConfig getProtocolConfig() {
		return protocolConfig;
	}

	public static ProjectConfig getProjectConfig() {
		return projectConfig;
	}

	
}
