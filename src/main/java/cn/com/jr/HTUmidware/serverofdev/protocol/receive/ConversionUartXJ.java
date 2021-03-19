package cn.com.jr.HTUmidware.serverofdev.protocol.receive;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.jr.HTUmidware.serverofdev.protocol.AbstractUART;
import cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy.OneByteToHexToDeci;

public class ConversionUartXJ extends AbstractUART {
	private static Logger logger = LoggerFactory.getLogger(ConversionUartXJ.class);

	@Override
	public void parse() {
		// TODO Auto-generated method stub

		byte[] allData = getData();
		byte[] frameHeaderByte = { allData[0], allData[1] };
		try {
			String bytesToHexString = OneByteToHexToDeci.bytesToHexString(frameHeaderByte);
			setFrameHeader(bytesToHexString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.toString());
		}
		// 设置设备编码
		byte[] equipmentCodeByte = new byte[9];
		for (int i = 2, j = 0; i < 11; i++, j++) {
			equipmentCodeByte[j] = allData[i];
		}
		String equipmentCode = null;
		try {
			equipmentCode = new String(equipmentCodeByte, "US-ASCII");
			setEquipmentCode(equipmentCode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("设备编号EquipmentCode解析失败：" + e.toString());
		}

		byte[] typeCodeByte = { allData[11] };
		// String typeCodeStr = Byte.toString(typeCodeByte);
		String typeCodeStr = null;
		try {
			typeCodeStr = OneByteToHexToDeci.bytesToHexString(typeCodeByte);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		setTypeCode(typeCodeStr);

		byte[] allDataLenByte = new byte[2];
		allDataLenByte[0] = allData[12];
		allDataLenByte[1] = allData[13];

		String allDataLenuniteToDeci = null;
		try {
			allDataLenuniteToDeci = OneByteToHexToDeci.hexStrToDeciStr(OneByteToHexToDeci
					.bytesToHexString(allDataLenByte));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.info(e1.toString());
		}
		setAllDataLength(allDataLenuniteToDeci);

		byte[] dataLenByte = new byte[2];
		dataLenByte[0] = allData[14];
		dataLenByte[1] = allData[15];
		String dataLenuniteToDeci = null;
		try {
			dataLenuniteToDeci = OneByteToHexToDeci.hexStrToDeciStr(OneByteToHexToDeci
					.bytesToHexString(dataLenByte));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.toString());
		}
		Integer dataLen = Integer.valueOf(dataLenuniteToDeci);
		setDataLength(dataLenuniteToDeci);

		// 将协议中的内容数据不做转换，留在handler中处理(而handler中放入队列处理)
		byte[] contentData = new byte[dataLen];
		int len = 16 + dataLen;
		for (int i = 16, j = 0; i < len; i++, j++) {
			contentData[j] = allData[i];
		}
		setContentData(contentData);

	}

}
