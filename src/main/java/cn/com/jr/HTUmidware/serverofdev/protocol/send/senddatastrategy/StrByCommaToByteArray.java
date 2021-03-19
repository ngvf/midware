package cn.com.jr.HTUmidware.serverofdev.protocol.send.senddatastrategy;

import java.nio.ByteBuffer;

/**
 * @author ydd
 *
 */
public class StrByCommaToByteArray implements DataToHexStrategy {
	
	private static StrByCommaToByteArray strByCommaToByteArray=new StrByCommaToByteArray();
	private StrByCommaToByteArray(){
		
	}
	public static StrByCommaToByteArray getInstance(){
		return strByCommaToByteArray;
	}

	@Override
	public byte[] toHexByteArrStrategy(String content) {
		// TODO Auto-generated method stub
		byte[] strByCommaToByteArr = strByCommaToByteArr(content);
		return strByCommaToByteArr;
	}

	/**
	 * @author ydd 字符串先根据逗号拆分，然后才转成字节数组
	 */
	private static byte[] strByCommaToByteArr(String strContent) {
		String[] split = strContent.split(",");
		int len = split.length;
		byte[] retByte = new byte[len];
		for (int i = 0; i < len; i++) {
			retByte[i] = hexStrToByte(split[i])[0];
		}
		return retByte;
	}

	/**
	 * @author ydd 将16进制字符串转成byte数组
	 */
	public static byte[] hexStrToByte(String hex) {
		ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
		for (int i = 0; i < hex.length(); i++) {
			String hexStr = hex.charAt(i) + "";
			i++;
			hexStr += hex.charAt(i);
			byte b = (byte) Integer.parseInt(hexStr, 16);
			bf.put(b);
		}
		return bf.array();
	}

}
