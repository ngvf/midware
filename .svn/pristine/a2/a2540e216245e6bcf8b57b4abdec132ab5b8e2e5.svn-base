package cn.com.jr.HTUmidware;

import cn.com.jr.HTUmidware.configuration.ConfigContext;
import cn.com.jr.HTUmidware.serverofdev.supagreementserver.TCP.TCPServiceOfDev;
import cn.com.jr.HTUmidware.serverofdev.supagreementserver.UDP.UDPservice;
import cn.com.jr.HTUmidware.serverofweb.ServerOfWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yangdd 服务启动类
 *
 *
 *
 */
public class ServerStart {

	private static final Logger logger = LoggerFactory.getLogger(ServerStart.class);

	public static void main(String[] args) {
		try {
			ConfigContext instace = ConfigContext.getInstace();
			// TCPServiceOfDev 设备和中间件用的是tcp协议
			TCPServiceOfDev tcp = new TCPServiceOfDev(instace.getServerPort());
			// UDP 暂时未用到
			UDPservice udp = new UDPservice(instace.getServerPort());
			/*
			 * http 暂时未用到 （目前http协议端口和TCP端口不能使用同一个端口），
			 * 不然会报java.net.BindException: Address already in use: bind
			 */
//			HttpService http = new HttpService(1112);

			//供web请求的服务    web平台使用tcp/ip协议（socket）    
			ServerOfWeb socketServer = new ServerOfWeb(instace.getSocketPort());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("server start error.. ");

		}
	}
}
