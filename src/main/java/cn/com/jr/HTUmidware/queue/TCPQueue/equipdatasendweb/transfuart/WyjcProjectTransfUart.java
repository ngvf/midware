package cn.com.jr.HTUmidware.queue.TCPQueue.equipdatasendweb.transfuart;

import cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy.*;

import java.util.HashMap;
import java.util.Map;

public class WyjcProjectTransfUart extends TransfUart {

	static Map<String, UartDataStrategy> transfUartMap=new HashMap<String, UartDataStrategy>();

	static {
		transfUartMap.put("1,6",  HexByArraysToDeci.getInstance());
		transfUartMap.put("2,12",  HexToUniteGrapDeci.getInstance());
		transfUartMap.put("3,6",  HexByUniteToDeci.getInstance());
		transfUartMap.put("4,2",  HexToGrapDeci.getInstance());
		transfUartMap.put("5,3",  HexToTempHum.getInstance());
		transfUartMap.put("6,1",  OneByteToHexToDeci.getInstance());
		transfUartMap.put("7,1",  HexByArraysToDeci.getInstance());
		transfUartMap.put("8,1",  HexByArraysToDeci.getInstance());
		transfUartMap.put("9,12",  HexToUniteGrapDeci.getInstance());
		transfUartMap.put("10,12",  HexToUniteGrapDeci.getInstance());
		transfUartMap.put("11,1",  HexByArraysToDeci.getInstance());
		transfUartMap.put("12,12",  HexToUniteGrapDeci.getInstance());
		transfUartMap.put("13,6",  HexByUniteToDeci.getInstance());

		transfUartMap.put("14,3",  HexByArraysToDeci.getInstance());// 新增获取设备水平位置xyz数据
		transfUartMap.put("15,3",  HexByArraysToDeci.getInstance());// 设备发生碰撞时上传的xyz数据
		// 16号协议解析直接就是进行设备编号添加
		transfUartMap.put("16,1",  OneByteToHexToDeci.getInstance());// 硬件设备 恢复出厂状态

	}

	public UartDataStrategy getSpecificUartDatadStrategy(String tpyeAndLength) {
		// TODO Auto-generated method stub
		return transfUartMap.get(tpyeAndLength);
	}

	public static void main(String[] args) {
		Map<String,Object> m=new HashMap<String,Object>(){{
			put("k",1);
			put("k",2);
		}};

		for(Map.Entry<String, Object> entry : m.entrySet()){
			String mapKey = entry.getKey();
			Object mapValue = entry.getValue();
			System.out.println(mapKey+":"+mapValue);
		}

	}

}
