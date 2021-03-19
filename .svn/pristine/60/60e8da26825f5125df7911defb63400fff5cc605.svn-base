package cn.com.jr.HTUmidware.controller.TCPController;

import cn.com.jr.HTUmidware.controller.AbstractController;
import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.jr.HTUmidware.serverofdev.protocol.UART;
import cn.com.jr.HTUmidware.serverofdev.protocol.receive.ConversionUart;
import cn.com.jr.HTUmidware.util.HttpUtil;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * 中间件调用平台接口 
 * restful风格
 */
public class SettingController extends AbstractController {

	
	private final static String method = "/wyjc/httpclient.do";
	private static final Logger logger = LoggerFactory.getLogger(SettingController.class);

	public SettingController(ChannelHandlerContext ctx, UART info) {
		super(ctx, info);
	}

	@Override
	public String executor() {
		
		if (info instanceof ConversionUart) {
			JSONObject o = (JSONObject) JSONObject.toJSON(this.info);
			String msg = o.toJSONString();
			msg = "data=" + msg;
			String result = HttpUtil.httpRequest(getUrl() + method, "POST", msg);
			String req="中间件向web平台发送的请求："+getUrl() + method+";POST;"+ msg;
			logger.info(req);
			return result;
		}

		return null;
	}
}
