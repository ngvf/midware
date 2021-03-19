package cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy;


public class HexNotFixToGrap implements UartDataStrategy {
	private static HexNotFixToGrap hexNotFixToGrap=new HexNotFixToGrap();
	
	private HexNotFixToGrap(){
		
	}
	public static HexNotFixToGrap getInstance(){
		return hexNotFixToGrap;
	}

	@Override
	public String conversionData(byte[] data) {
		// TODO Auto-generated method stub

		try {
			return notFixByteToString(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String notFixByteToString(byte[] data) throws Exception {
		if (data.length == 0) {
			throw new Exception("要转的字节数组，数据为空");
		}
		StringBuffer sb = new StringBuffer();
		byte[] oneByte = { data[0] };// 站台
		byte[] twoByte = { data[1] };// 线路

		String oneStr = OneByteToHexToDeci.uniteToDeci(oneByte);
		String twoStr = OneByteToHexToDeci.uniteToDeci(twoByte);
		sb.append(oneStr);
		sb.append(twoStr);

		byte[] fourByteArray = new byte[2];
		fourByteArray[0] = data[2];
		fourByteArray[1] = data[3];
		String measurePoint = OneByteToHexToDeci.hexStrToDeciStr(OneByteToHexToDeci
				.bytesToHexString(fourByteArray));
		sb.append(measurePoint);
		sb.append(",");
		Integer measurePointNum = Integer.valueOf(measurePoint);
		int dataLen = data.length;
		int calMeasurePointNum = (dataLen - 4) / 4;
		if (calMeasurePointNum != measurePointNum ) {
			throw new Exception("测量点的数量的测量数据有误！！");
		}
		String hexStrUnit = "";
		byte[] twoArray = new byte[2];
		for (int i = 4, j = 0; i < dataLen; i++, j++) {
			twoArray[j] = data[i];
			if (j == 1) {
				j = -1;
				String hexStringSub = OneByteToHexToDeci.bytesToHexString(twoArray);
				String hexStrToDeciStr = OneByteToHexToDeci.hexStrToDeciStr(hexStringSub);
				sb.append(hexStrToDeciStr);
				sb.append(",");
			}

		}

		return sb.toString();
	}

}
