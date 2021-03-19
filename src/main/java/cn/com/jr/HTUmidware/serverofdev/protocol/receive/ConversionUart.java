package cn.com.jr.HTUmidware.serverofdev.protocol.receive;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.jr.HTUmidware.serverofdev.protocol.AbstractUART;

/**
 * 
 * @author yangdd
 * 
 *         转换协议类 将16进制的字节数组转成bean对象并设值
 */
public class ConversionUart extends AbstractUART {
	private static Logger logger = LoggerFactory.getLogger(ConversionUart.class);

	@Override
	public void parse() {
		// TODO Auto-generated method stub
		byte[] allData = getData();
		byte[] frameHeaderByte = { allData[0] };
		try {
			String frameHeaderStr = new String(frameHeaderByte, "US-ASCII");
			setFrameHeader(frameHeaderStr);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.info("帧头FrameHeaderStr解析失败：" + e1.toString());
		}
		// 设置设备编码
		byte[] equipmentCodeByte = new byte[9];
		for (int i = 1, j = 0; i < 10; i++, j++) {
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

		byte typeCodeByte = allData[10];
		String typeCodeStr = Byte.toString(typeCodeByte);
		setTypeCode(typeCodeStr);

		byte dataLenByte = allData[11];
		String dataLenStr = Byte.toString(dataLenByte);
		Integer len = Integer.valueOf(dataLenStr);
		setDataLength(dataLenStr);

		// 将协议中的内容数据不做转换，留在handler中处理(而handler中放入队列处理)
		byte[] contentData = new byte[len];
		for (int i = 12, j = 0; i < 12 + len; i++, j++) {
			contentData[j] = allData[i];
		}
		setContentData(contentData);

	}

}
