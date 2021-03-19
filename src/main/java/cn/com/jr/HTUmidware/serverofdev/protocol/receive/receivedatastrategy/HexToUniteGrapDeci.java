package cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yangdd
 *16进制转成复合的10进制字符串的类
 *
 */
public class HexToUniteGrapDeci implements UartDataStrategy {
	private static Logger logger = LoggerFactory.getLogger(HexToUniteGrapDeci.class);
	
	private static HexToUniteGrapDeci hexToUniteGrapDeci=new HexToUniteGrapDeci();
	private HexToUniteGrapDeci(){
		
	}
	public static HexToUniteGrapDeci getInstance(){
		return hexToUniteGrapDeci;
	}
	/*
	 * 03 e8 03 e9 03 ea 00 19 5a 00 19 78 XYZ三个距离：1000mm 1001mm
	 * 1002mm；轨温：+25°；空气湿度：90% ；空气温度：+25°；电量12.0V.
	 */
	@Override
	public String conversionData(byte[] data) {
		// TODO Auto-generated method stub
		byte[] xyzB = new byte[6];
		byte[] railTempB = new byte[2];
		byte[] airHumB = new byte[1];
		byte[] airTempB = new byte[2];
		byte[] eleB = new byte[1];

		for (int i = 0; i < 6; i++) {
			xyzB[i] = data[i];
		}
		railTempB[0] = data[6];
		railTempB[1] = data[7];

		airHumB[0] = data[8];

		airTempB[0] = data[9];
		airTempB[1] = data[10];

		eleB[0] = data[11];

		HexByUniteToDeci hexByUniteToDeci =HexByUniteToDeci.getInstance();
		HexToGrapDeci hexToGrapDeci1 =  HexToGrapDeci.getInstance();
		OneByteToHexToDeci oneByteToHexToDeci =OneByteToHexToDeci.getInstance();

		

		String xyzStr = hexByUniteToDeci.conversionData(xyzB);
		String railTempStr = hexToGrapDeci1.conversionData(railTempB);
		String airHumStr =oneByteToHexToDeci.conversionData(airHumB);
		String airTempStr = hexToGrapDeci1.conversionData(airTempB);
		String eleStr = oneByteToHexToDeci.conversionData(eleB);
		

		return xyzStr + railTempStr + airHumStr + airTempStr + eleStr;
	}

	public String byteToStr(byte b) {
		Integer valueOf = Integer.valueOf(b);
		return valueOf.toString() + ",";

	}

	

}
