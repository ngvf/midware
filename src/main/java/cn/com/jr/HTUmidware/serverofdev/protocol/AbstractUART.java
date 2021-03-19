package cn.com.jr.HTUmidware.serverofdev.protocol;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author yangdd
 * 将协议抽象化（规定协议的属性和部分方法）
 * 
 *
 */
 
public abstract class AbstractUART implements UART {

	protected SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 协议最小长度
	 */
	public final static int MIN_LENGTH = 2;
	/**
	 * 协议最大长度
	 */
	public final static int MAX_LENGTH = 176;

	// 帧头
	private String frameHeader;
	// 设备编码
	private String equipmentCode;
	// 类型码
	private String typeCode;
	// 数据长度
	private String dataLength;
	// 协议总数据长度
    private String allDataLength;
	// 转换后数据
	private String parseAfterData;
	// 帧尾
	private String endOfFrame;
	// 转换前内容数据
    protected byte[] contentData;
	// 转换前所有数据
	protected byte[] data;
	

	public AbstractUART() {

	}

	protected AbstractUART(byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte[] getMark() {
		if (data.length > MIN_LENGTH) {
			byte[] bytes = { data[0] };
			return bytes;
		}
		return null;
	}

	public String getMarkString() {
		if (data.length > MIN_LENGTH) {
			try {
				return new String(new byte[] { data[0] }, "US-ASCII");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String asciiString(byte[] bytes) {
		if (bytes == null || bytes.length < 1) return null;
		try {
			return new String(bytes, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return "AbstractUART [yyyyMMddHHmmss=" + yyyyMMddHHmmss + ", frameHeader=" + frameHeader
				+ ", equipmentCode=" + equipmentCode + ", typeCode=" + typeCode + ", dataLength="
				+ dataLength + ", allDataLength=" + allDataLength + ", parseAfterData="
				+ parseAfterData + ", endOfFrame=" + endOfFrame + ", contentData="
				+ Arrays.toString(contentData) + ", data=" + Arrays.toString(data) + "]";
	}

	/**
	 * @param dateTime
	 *            日期时间 类型为20180526152108 即为2018年5月26日15:21:08
	 * @return
	 */
	protected Date buildDateTime(String dateTime) {
		if (dateTime != null) {
			try {
				return this.yyyyMMddHHmmss.parse(dateTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将日期格式化为 byte数组
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	protected byte[] getTimeByte(Date date) {
		if (date != null) {
			String s = this.yyyyMMddHHmmss.format(date);
			if (s.length() == 14) {
				s = s.substring(2);
			}
			try {
				return s.getBytes("US-ASCII");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将byte数组插入到指定下标
	 * 
	 * @param bytes
	 *            插入数组
	 * @param idx
	 *            插下标
	 * @return 插入结束下标
	 */
	protected int insertArr(byte[] bytes, int idx) {
		if (bytes != null) {
			for (int i = 0; i < bytes.length; i++) {
				data[i + idx] = bytes[i];
			}
			return idx + bytes.length;
		}
		return idx;
	}

	public String getFrameHeader() {
		return frameHeader;
	}

	public void setFrameHeader(String frameHeader) {
		this.frameHeader = frameHeader;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getDataLength() {
		return dataLength;
	}

	public void setDataLength(String dataLength) {
		this.dataLength = dataLength;
	}
	
	public String getAllDataLength() {
		return allDataLength;
	}

	public void setAllDataLength(String allDataLength) {
		this.allDataLength = allDataLength;
	}

	public String getParseAfterData() {
		return parseAfterData;
	}

	public void setParseAfterData(String parseAfterData) {
		this.parseAfterData = parseAfterData;
	}

	public String getEndOfFrame() {
		return endOfFrame;
	}

	public void setEndOfFrame(String endOfFrame) {
		this.endOfFrame = endOfFrame;
	}

	public byte[] getContentData() {
		return contentData;
	}

	public void setContentData(byte[] contentData) {
		this.contentData = contentData;
	}
	


	
	

}
