package cn.com.jr.HTUmidware.queue.TCPQueue.equipdatasendweb.transfuart;

import cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy.UartDataStrategy;

public abstract class TransfUart {

	// 返回具体协议
	public abstract UartDataStrategy getSpecificUartDatadStrategy(String tpyeAndLength);

}
