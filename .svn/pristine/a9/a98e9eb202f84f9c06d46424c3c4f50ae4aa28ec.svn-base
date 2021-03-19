package cn.com.jr.HTUmidware.controller;

import cn.com.jr.HTUmidware.configuration.ConfigContext;
import cn.com.jr.HTUmidware.serverofdev.protocol.UART;
import cn.com.jr.HTUmidware.serverofdev.supagreementserver.TCP.ServerOfDevContainer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * 协议调用web平台抽象类
 */
public abstract class AbstractController implements Controller {

	protected ChannelHandlerContext ctx;
	protected UART info;
	protected String url;
	/**
	 * 设备标识
	 */
	private String id;

	/**
	 * 当前控制器的通道
	 */
	private Channel channel;

	public AbstractController(ChannelHandlerContext ctx, UART info) {
		this.ctx = ctx;
		this.info = info;
		if (ctx == null) throw new RuntimeException("连接通道出错！");
		this.channel = ctx.channel();
		this.id = ServerOfDevContainer.sourceIds.get(ctx.channel());
		byte[] mark = info.getMark();
		this.url = ConfigContext.getInstace().getProjectConfig()
				.getProjectAddress(info.getFrameHeader());
	}

	public String getId() {
		return id;
	}

	public Channel getChannel() {
		return channel;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public UART getInfo() {
		return info;
	}

	public void setInfo(UART info) {
		this.info = info;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
