package cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yangdd 两两字节合并转成10进制的字符串的类
 *
 */
public class HexByUniteToDeci implements UartDataStrategy {
	private static Logger logger = LoggerFactory.getLogger(HexByUniteToDeci.class);
	private static HexByUniteToDeci hexByUniteToDeci = new HexByUniteToDeci();

	private HexByUniteToDeci() {

	}

	public static HexByUniteToDeci getInstance() {
		return hexByUniteToDeci;
	}

	/*
	 * 将data 每两个字节合并后再转成10进制字符串
	 */
	@Override
	public String conversionData(byte[] data) {
		// TODO Auto-generated method stub
		int len = data.length;
		if (len % 2 == 0) {
			return uniteToDeci(data).toString() + ",";
		}
		return null;

	}

	private List uniteToDeci(byte[] data) {
		int len = data.length;
		List list = new ArrayList();
		String hexStringSubCon = "";
		for (int i = 0, j = 0; i < len; i++, j++) {
			byte[] oneByte = { data[i] };
			String hexStringSub = OneByteToHexToDeci.bytesToHexString(oneByte);
			hexStringSubCon = hexStringSubCon + hexStringSub;

			if (j == 1) {
				j = -1;
				String hexStringSubConAddZero = splicingZero(hexStringSubCon, 8);
				int h = Integer.parseInt(hexStringSubConAddZero, 16);
				list.add(h);
				hexStringSubCon = "";
			}

		}

		return list;

	}

	/**
	 * 字符串前面补零操作
	 *
	 * @param str
	 *            字符串本体
	 * @param totalLenght
	 *            需要的字符串总长度
	 * @return
	 */
	public static String splicingZero(String str, int totalLenght) {
		int strLenght = str.length();
		String strReturn = str;
		for (int i = 0; i < totalLenght - strLenght; i++) {
			strReturn = "0" + strReturn;
		}
		return strReturn;
	}

}
