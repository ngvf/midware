package cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangdd 16进制统一转成10进制字符串的类
 *
 */

public class HexByArraysToDeci implements UartDataStrategy {
	private static Logger logger = LoggerFactory.getLogger(HexByArraysToDeci.class);

	private static HexByArraysToDeci hexByArraysToDeci = new HexByArraysToDeci();

	private HexByArraysToDeci() {

	}

	public static HexByArraysToDeci getInstance() {
		return hexByArraysToDeci;
	}

	/*
	 * 将data统一转成10进制的字符串
	 */
	@Override
	public String conversionData(byte[] data) {
		// TODO Auto-generated method stub
		return Arrays.toString(data) + ",";
	}

}
