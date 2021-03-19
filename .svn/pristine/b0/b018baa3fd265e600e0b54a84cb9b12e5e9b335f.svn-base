package cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author yangdd
 * 16进制转成10进制温度的类
 *
 */
public class HexToGrapDeci implements UartDataStrategy {
	private static Logger logger=LoggerFactory.getLogger(HexToGrapDeci.class);
	private static HexToGrapDeci hexToGrapDeci=new HexToGrapDeci();
	private HexToGrapDeci(){
		
	}
	public static HexToGrapDeci getInstance(){
		return hexToGrapDeci;
	}

	/**
	 *  00 19 表示：+25
	 *  00表示温度为正，01表示温度为负）
	 */
	@Override
	public String conversionData(byte[] data) {
		// TODO Auto-generated method stub
		if (data.length != 2) return null;
		byte plusMinus = data[0];
		byte[] temp = {data[1]};

		Integer plusMinusNum = Integer.valueOf(plusMinus);
		String pm = plusMinusNum.toHexString(plusMinusNum);
		String pmFalg = "";
		if ("0".equals(pm)) {
			pmFalg = "+";
		}
		else if ("1".equals(pm)) {
			pmFalg = "-";
		}

		OneByteToHexToDeci oneByteToHexToDeci = OneByteToHexToDeci.getInstance();
		String conversionData = oneByteToHexToDeci.conversionData(temp);
		List list = new ArrayList();
		list.add(pmFalg+conversionData);
		return list.toString();
	}



	
}
