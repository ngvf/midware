package cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy;


public class HexThreeSingleTwoDoubleToGrap implements UartDataStrategy {
	private static HexThreeSingleTwoDoubleToGrap hexThreeSingleOneDoubleToGrap=new HexThreeSingleTwoDoubleToGrap();
	private HexThreeSingleTwoDoubleToGrap(){
		
	}
	public static HexThreeSingleTwoDoubleToGrap getInstance(){
		return hexThreeSingleOneDoubleToGrap;
	}

	@Override
	public String conversionData(byte[] data) {
		// TODO Auto-generated method stub
		
		try {
			return fiveFixByteToString(data)+",";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String fiveFixByteToString(byte[] data) throws Exception {
		if (data.length != 7) {
			throw new Exception("只能转固定七个字节数组，并且是前三个字节单独转，后面四个字节，每两个字节合并后再转，所返回的字符串");
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
		
		byte[] fourByteArray = new byte[2];
		fourByteArray[0]=data[3];
		fourByteArray[1]=data[4];
		String strAngle =OneByteToHexToDeci.hexStrToDeciStr( OneByteToHexToDeci.bytesToHexString(fourByteArray));
		sb.append(strAngle);
		sb.append(",");
		byte[] fiveByteArray = new byte[2];
		fiveByteArray[0]=data[5];
		fiveByteArray[1]=data[6];
		String strDistance =OneByteToHexToDeci.hexStrToDeciStr( OneByteToHexToDeci.bytesToHexString(fiveByteArray));
		sb.append(strDistance);
		
		
		return sb.toString();
	}

}
