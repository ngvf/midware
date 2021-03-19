package cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangdd 16进制统一转成图形码的字符串的类
 *
 */

public class HexByStringToGrap implements UartDataStrategy {
	private static Logger logger = LoggerFactory.getLogger(HexByStringToGrap.class);
	private static HexByStringToGrap hexByStringToGrap = new HexByStringToGrap();

	private HexByStringToGrap() {

	}

	public static HexByStringToGrap getInstance() {
		return hexByStringToGrap;
	}

	/*
	 * 将data统一转成图形码
	 */
	@Override
	public String conversionData(byte[] data) {
		// TODO Auto-generated method stub

		try {
			// return "["+new String(data,"US-ASCII")+"]";
			return new String(data, "US-ASCII") + ",";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("字节数组统一转成图形码失败" + e.toString());
		}
		return null;
	}

}
