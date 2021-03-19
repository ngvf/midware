package cn.com.jr.HTUmidware.serverofdev.protocol.send;

import cn.com.jr.HTUmidware.serverofdev.protocol.AbstractUART;
import cn.com.jr.HTUmidware.util.Tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author yangdd
 * 平台消息，下传到设备 的  日期时间设定协议类
 * 将web发送过来的请求转成16进制的字节数组
 */
public class DateSettingUART extends AbstractUART {

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

	/*
	 * * 用于发送协议初始化
	 */
	@Override
	public void parse() {
		int UartSize = 20;
		data = new byte[UartSize];// 协议固定长度
		// 填充 data数组
		data[0] = "h".getBytes()[0];
		String equipmentCode = this.getEquipmentCode();
		String typeCode = this.getTypeCode();
		String dataLength = this.getDataLength();
		
		byte typeCodeByte = (byte) Integer.parseInt(typeCode, 16);
		byte dataLengthByte = (byte) Integer.parseInt(dataLength, 16);
		data[10] = typeCodeByte;
		data[11] = dataLengthByte;

		byte[] dataField = Tools.timeFormat(new Date());
		insertArr(dataField, 12);

		this.setTypeCode(typeCode);
		this.setFrameHeader("h");
		data[UartSize - 2] = 0x0D;
		data[UartSize - 1] = 0x0A;
	}

}
