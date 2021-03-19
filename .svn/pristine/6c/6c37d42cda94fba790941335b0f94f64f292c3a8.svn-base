package cn.com.jr.HTUmidware.serverofdev.protocol.send.senddatastrategy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NowTimeToHexByteArray implements DataToHexStrategy {

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    private static NowTimeToHexByteArray nowTimeToHexByteArray=new NowTimeToHexByteArray();
	private NowTimeToHexByteArray(){
		
	}
	public static NowTimeToHexByteArray getInstance(){
		return  nowTimeToHexByteArray;
	}
	
	
	@Override
	public byte[] toHexByteArrStrategy(String content) {
		byte[] timeFormatHexByteArr = timeFormat(new Date());
		return timeFormatHexByteArr;
	}

	
	/**
	 * @author ydd
	 * 将当前时间转化为byte数组格式
	 */
	public static byte[] timeFormat(Date date) {
		if (date != null) {
			String s = formatter.format(date);
			if (s.length() == 14) {
				s = s.substring(2);
			}
			try {
				byte[] sez = new byte[s.length() / 2];
				List<Integer> e = new ArrayList<Integer>();
				for (int i = 0; i < s.length(); i += 2) {
					e.add(Integer.valueOf(s.substring(i, i + 2)));
				}
				if (e.isEmpty()) return null;

				for (int j = 0; j < e.size(); j++) {
					sez[j] = (byte) ((int) e.get(j));
				}
				return sez;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
