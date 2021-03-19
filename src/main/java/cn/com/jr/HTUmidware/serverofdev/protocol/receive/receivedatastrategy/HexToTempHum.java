package cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yangdd
 *16进制转成温度和湿度的类
 *
 */

public class HexToTempHum implements UartDataStrategy {

	private static Logger logger=LoggerFactory.getLogger(HexToTempHum.class);
	private static HexToTempHum hexToTempHum=new HexToTempHum();
	private HexToTempHum(){
		
	}
	public static HexToTempHum getInstance(){
		return hexToTempHum;
	}
	
	@Override
	public String conversionData(byte[] data) {
		// TODO Auto-generated method stub

		
		byte[] airTempB = new byte[2];
		byte[] airHumB = new byte[1];
		airHumB[0]=data[0];
		airTempB[0]=data[1];
		airTempB[1]=data[2];
		
		HexToUniteGrapDeci hexToUniteGrapDeci = HexToUniteGrapDeci.getInstance();
		String airHum = hexToUniteGrapDeci.byteToStr(airHumB[0]);
		HexToGrapDeci hexToGrapDeci =  HexToGrapDeci.getInstance();
		String airData = hexToGrapDeci.conversionData(airTempB);
		
		return airHum+airData;
	}


	



}
