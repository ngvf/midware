package cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy;


public class HexTwoSingleToGrap implements UartDataStrategy {
	private static HexTwoSingleToGrap hexTwoSingleToGrap=new HexTwoSingleToGrap();
	private HexTwoSingleToGrap(){
		
	}
	public static HexTwoSingleToGrap getInstance(){
		return hexTwoSingleToGrap;
	}

	@Override
	public String conversionData(byte[] data) {
		// TODO Auto-generated method stub
		
		try {
			return twoFixByteToString(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String twoFixByteToString(byte[] data) throws Exception {
		if (data.length != 2) {
			throw new Exception("只能转固定2个字节数组，并且是2个字节单独转，返回的字符串");
		}
		StringBuffer sb=new StringBuffer();
		byte[] oneByte={data[0]};
		byte[] twoByte={data[1]};
		
		String oneStr = OneByteToHexToDeci.uniteToDeci(oneByte);
		String twoStr = OneByteToHexToDeci.uniteToDeci(twoByte);
		
		sb.append(oneStr);
		sb.append(twoStr);
		
		return sb.toString();
	}

}

