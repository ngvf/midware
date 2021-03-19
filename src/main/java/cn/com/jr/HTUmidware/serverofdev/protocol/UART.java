package cn.com.jr.HTUmidware.serverofdev.protocol;

import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author yangdd
 * 将协议接口化（提取协议中重要方法）
 *
 *
 */
public interface UART {

	/**
	 * 得到标识位，下标为0 @return, ascii原型
	 */
	byte[] getMark();

	/**
	 * 得到标识位，下标为0
	 * 
	 * @return ,ascii值
	 */
	String getFrameHeader();

	/**
	 * 得到标识位，下标为1-9
	 * 
	 * @return ,ascii值
	 */
	String getEquipmentCode();
	
	String getTypeCode();
	
//	String getAllDataLength();
	
	String getDataLength();

	byte[] getContentData();
	
	void setParseAfterData(String parseAfterData);
	/**
	 * 得到所有数据
	 * 
	 * @return byte数组
	 */
	byte[] getData();

	/**
	 * 设置数据
	 * 
	 * @param data
	 *            byte数组
	 */
	void setData(byte[] data);

	/**
	 * 启动解析
	 */
	void parse();

}
