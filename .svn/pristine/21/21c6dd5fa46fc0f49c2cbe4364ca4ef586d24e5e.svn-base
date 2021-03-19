package cn.com.jr.HTUmidware.queue;

import cn.com.jr.HTUmidware.serverofdev.protocol.UART;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author yangdd
 * 设备数据发送到web平台的元素
 *
 */
public class QueueElement {
	private UART uart;
	private ChannelHandlerContext ctx;

	public QueueElement(UART uart, ChannelHandlerContext ctx) {
		super();
		this.uart = uart;
		this.ctx = ctx;
	}

	public UART getUart() {
		return uart;
	}

	public void setUart(UART uart) {
		this.uart = uart;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

}
