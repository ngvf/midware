package cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy;

/**
 * 
 * @author ydd 一个字节先转成16进制，然后再将16进制转成10进制 得到10进制的String
 */
public class OneByteToHexToDeci implements UartDataStrategy {
	private volatile static OneByteToHexToDeci oneByteToHexToDeci;

	private OneByteToHexToDeci() {
	}

	public static OneByteToHexToDeci getInstance() {
		if (oneByteToHexToDeci == null) {
			synchronized (OneByteToHexToDeci.class) {
				if (oneByteToHexToDeci == null) {
					oneByteToHexToDeci = new OneByteToHexToDeci();
				}
			}
		}
		return oneByteToHexToDeci;
	}

	@Override
	public String conversionData(byte[] data) {
		// TODO Auto-generated method stub
		try {
			return uniteToDeci(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String uniteToDeci(byte[] data) throws Exception {

		String hexStringSubCon = "";
		int num = data.length;
		if (num > 1) {
			throw new Exception("只能转一个字节数组");
		}
		hexStringSubCon = bytesToHexString(data);
		String hexStringSubConAddZero = splicingZero(hexStringSubCon, 8);
		int Deci = Integer.parseInt(hexStringSubConAddZero, 16);
		return String.valueOf(Deci) + ",";
	}

	public static String uniteToDeci(byte[] data, boolean bool) throws Exception {

		String hexStringSubCon = "";
		int num = data.length;
		if (num > 1) {
			throw new Exception("只能转一个字节数组");
		}
		hexStringSubCon = bytesToHexString(data);
		String hexStringSubConAddZero = splicingZero(hexStringSubCon, 8);
		int Deci = Integer.parseInt(hexStringSubConAddZero, 16);
		if (bool) {
			return String.valueOf(Deci) + ",";
		}
		return String.valueOf(Deci);
	}

	/**
	 * 字符串前面补零操作
	 *
	 * @param str
	 *            字符串本体
	 * @param totalLenght
	 *            需要的字符串总长度
	 * @return
	 */
	public static String splicingZero(String str, int totalLenght) {
		int strLenght = str.length();
		String strReturn = str;
		for (int i = 0; i < totalLenght - strLenght; i++) {
			strReturn = "0" + strReturn;
		}
		return strReturn;
	}

	/**
	 * @author ydd 16进制字符串转成10进制数的字符串
	 */
	public static String hexStrToDeciStr(String hexStr) throws Exception {
		String hexStringSubConAddZero = splicingZero(hexStr, 8);
		int Deci = Integer.parseInt(hexStringSubConAddZero, 16);
		return String.valueOf(Deci);
	}

	/**
	 * @author ydd 将字节数组转成16进制的字符串 {-127，-3} -->81FD {-127} -->81
	 */
	public static String bytesToHexString(byte[] bArr) {
		StringBuffer sb = new StringBuffer(bArr.length);
		String sTmp;

		for (int i = 0; i < bArr.length; i++) {
			sTmp = Integer.toHexString(0xFF & bArr[i]);
			if (sTmp.length() < 2) sb.append(0);
			sb.append(sTmp.toUpperCase());
		}

		return sb.toString();
	}

	// public static void main(String args[]) throws Exception{
	// String hexStrToDeciStr = hexStrToDeciStr("F77f");
	// System.out.print(hexStrToDeciStr);
	// }

}
