package cn.com.jr.HTUmidware.queue.TCPQueue.equipdatasendweb.transfuart;

import cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy.*;

import java.util.HashMap;
import java.util.Map;

public class XjProjectTransfUart extends TransfUart {

	static Map<String, UartDataStrategy> transfUartMap=new HashMap<String, UartDataStrategy>();
	
	static {
		transfUartMap.put("A1",  HexByStringToGrap.getInstance());//硬件回复心跳
		transfUartMap.put("A2",  HexByArraysToDeci.getInstance());//
		transfUartMap.put("A3",  HexByArraysToDeci.getInstance());
		transfUartMap.put("B3",  HexNotFixToGrap.getInstance());
		transfUartMap.put("A4",  HexTwoSingleToGrap.getInstance());
		transfUartMap.put("A5",  HexThreeSingleToGrap.getInstance());
		transfUartMap.put("B5",  HexThreeSingleTwoDoubleToGrap.getInstance());
		transfUartMap.put("A9",  HexByArraysToDeci.getInstance());
		transfUartMap.put("B0",  HexByStringToGrap.getInstance());
	}

	@Override
	public UartDataStrategy getSpecificUartDatadStrategy(String tpyeAndLength) {
		// TODO Auto-generated method stub
		return transfUartMap.get(tpyeAndLength);
	}

}
