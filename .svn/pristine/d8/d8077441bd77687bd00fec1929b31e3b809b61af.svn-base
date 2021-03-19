package cn.com.jr.HTUmidware.serverofdev.protocol.receive;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.jr.HTUmidware.serverofdev.protocol.AbstractUART;

/**
 * 设备编号协议
 * @author ydd
 *
 */
public class EquipCodeUART extends AbstractUART{
	
	private static Logger logger = LoggerFactory.getLogger(EquipCodeUART.class);

	
	@Override
	public void parse() {
		byte[] allData = getData();
		
		String equipmentCode = null;
		try {
			equipmentCode = new String(allData, "US-ASCII");
			setEquipmentCode(equipmentCode.replaceAll("\r|\n", ""));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			 logger.error("设备重连异常"+e.getMessage());
		}		
	}
}
