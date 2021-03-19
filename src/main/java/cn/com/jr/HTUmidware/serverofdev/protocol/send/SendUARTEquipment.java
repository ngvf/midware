package cn.com.jr.HTUmidware.serverofdev.protocol.send;

import cn.com.jr.HTUmidware.serverofdev.protocol.AbstractUART;

/**
 * 
 * @author yangdd
 * 平台消息，下传到设备 的 协议
 *
 *
 */
 
public class SendUARTEquipment extends AbstractUART {

	/*
	 * 用于发送协议初始化
	 */
	@Override
	public void parse() {
		int UartSize = 15;
		data = new byte[UartSize];// 协议固定长度
		// 填充 data数组
		data[0] = "h".getBytes()[0];
		String equipmentCode = this.getEquipmentCode();
		String typeCode = this.getTypeCode();
		String dataLength =this.getDataLength();
		byte[] equipCode = equipmentCode.getBytes();
		insertArr(equipCode, 1);
		byte typeCodeByte = (byte) Integer.parseInt(typeCode, 16);
		byte dataLengthByte = (byte) Integer.parseInt(dataLength, 16);
		data[10] = typeCodeByte;
		data[11] = dataLengthByte;
//		byte[] dataField = { 0x01 };//可以优化成存储多个值
		String   dataField=this.getParseAfterData();
		byte dataFieldByte = (byte) Integer.parseInt(dataField, 16);
		data[12]=dataFieldByte;
//		insertArr(dataField, 12);

		this.setTypeCode(typeCode);
		this.setFrameHeader("h");
		data[UartSize - 2] = 0x0D;
		data[UartSize - 1] = 0x0A;
	}


}
