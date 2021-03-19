package cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy;


public class HexThreeSingleToGrap implements UartDataStrategy {
	
	private static HexThreeSingleToGrap hexThreeSingleToGrap=new HexThreeSingleToGrap();
	private HexThreeSingleToGrap(){
		
	}
	public static HexThreeSingleToGrap getInstance(){
		return hexThreeSingleToGrap;
	}

	@Override
	public String conversionData(byte[] data) {
		// TODO Auto-generated method stub
		
		try {
			return threeFixByteToString(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String threeFixByteToString(byte[] data) throws Exception {
		if (data.length != 3) {
			throw new Exception("只能转固定三个字节数组，并且是三个字节单独转，返回的字符串");
		}
		StringBuffer sb=new StringBuffer();
		byte[] oneByte={data[0]};
		byte[] twoByte={data[1]};
		byte[] threeByte={data[2]};
		
		String oneStr = OneByteToHexToDeci.uniteToDeci(oneByte);
		String twoStr = OneByteToHexToDeci.uniteToDeci(twoByte);
		String threeStr = OneByteToHexToDeci.uniteToDeci(threeByte);
		sb.append(oneStr);
		sb.append(twoStr);
		sb.append(threeStr);
		
		return sb.toString();
	}

}
