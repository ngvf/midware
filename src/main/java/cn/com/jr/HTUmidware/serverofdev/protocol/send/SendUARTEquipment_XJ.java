package cn.com.jr.HTUmidware.serverofdev.protocol.send;

import java.util.HashMap;
import java.util.Map;

import cn.com.jr.HTUmidware.serverofdev.protocol.AbstractUART;
import cn.com.jr.HTUmidware.serverofdev.protocol.send.senddatastrategy.DataToHexStrategy;
import cn.com.jr.HTUmidware.serverofdev.protocol.send.senddatastrategy.NowTimeToHexByteArray;
import cn.com.jr.HTUmidware.serverofdev.protocol.send.senddatastrategy.StrByCommaToByteArray;
import cn.com.jr.HTUmidware.serverofdev.protocol.send.senddatastrategy.StrToHexByteArrary;

public class SendUARTEquipment_XJ extends AbstractUART {

	private static Map<String, DataToHexStrategy> transfToHexUart = new HashMap<String, DataToHexStrategy>();
	static {
		transfToHexUart.put("1", StrToHexByteArrary.getInstance());
		transfToHexUart.put("2", NowTimeToHexByteArray.getInstance());
		transfToHexUart.put("3", StrToHexByteArrary.getInstance());
		transfToHexUart.put("4", StrByCommaToByteArray.getInstance());
		transfToHexUart.put("5", StrByCommaToByteArray.getInstance());
		transfToHexUart.put("9", StrToHexByteArrary.getInstance());
		transfToHexUart.put("176", StrToHexByteArrary.getInstance());

	}

	@Override
	public void parse() {
		// TODO Auto-generated method stub
		String allDataLenStr = this.getAllDataLength();
		Integer allDataLen = Integer.valueOf(allDataLenStr);
		data = new byte[allDataLen];// 协议固定长度

		// 头固定
		data[0] = StrByCommaToByteArray.hexStrToByte("7F")[0];
		data[1] = StrByCommaToByteArray.hexStrToByte("F7")[0];

		// 设备编码
		String equipmentCode = this.getEquipmentCode();
		byte[] equipCode = equipmentCode.getBytes();
		insertArr(equipCode, 2);

		// 类型
		String typeCode = this.getTypeCode();
		byte typeCodeByte = (byte) Integer.parseInt(typeCode, 10);
		data[11] = typeCodeByte;
		// 数据全长
		data[12] = 00;
		data[13] = (byte) Integer.parseInt(allDataLenStr, 10);
		// 实际数据长度
		String dataLength = this.getDataLength();
		data[14] = 00;
		data[15] = (byte) Integer.parseInt(dataLength, 10);

		String parseData = this.getParseAfterData();
		byte[] parseAfterData = transfToHexUart.get(typeCode).toHexByteArrStrategy(parseData);
		insertArr(parseAfterData, 16);

		// 00 00 7F F7
		data[allDataLen - 4] = 00;
		data[allDataLen - 3] = 00;
		data[allDataLen - 2] = StrByCommaToByteArray.hexStrToByte("7F")[0];
		data[allDataLen - 1] = StrByCommaToByteArray.hexStrToByte("F7")[0];
	}

//	public static void main(String[] args) {
//		int parseInt = Integer.parseInt("247", 10);
//		byte parseIt = (byte) Integer.parseInt("127", 10);
//		byte parseIt7F = "7F".getBytes()[0];
//		byte parseItF7 = "F7".getBytes()[0];
//		byte[] hexStr2Byte = StrByCommaToByteArray.hexStrToByte("7F");
//		byte[] hexStr2Byte1 = StrByCommaToByteArray.hexStrToByte("F7");
//		System.out.print("151");
//	}

}
