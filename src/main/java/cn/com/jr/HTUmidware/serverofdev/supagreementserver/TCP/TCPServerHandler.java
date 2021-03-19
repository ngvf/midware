package cn.com.jr.HTUmidware.serverofdev.supagreementserver.TCP;

import cn.com.jr.HTUmidware.queue.QueueElement;
import cn.com.jr.HTUmidware.queue.TCPQueue.equipdatasendweb.EquipDataSendWebQueue;
import cn.com.jr.HTUmidware.serverofdev.protocol.UART;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author yangdd 设备服务端处理器
 *
 */

public class TCPServerHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(TCPServerHandler.class);

	

	private static final ExecutorService webExecutors = Executors.newCachedThreadPool();
	// 客户端超时次数
	// private Map<ChannelHandlerContext,Integer> clientOvertimeMap = new
	// ConcurrentHashMap<>();
	// private int counter; //超时次数超过该值则注销连接

	/**
	 * 客户端与服务端创建连接的时候调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		SocketAddress address = channel.remoteAddress();
		ServerOfDevContainer.group.add(channel);
		logger.info(address + ":客户端与服务端连接开始...");
		Thread.sleep(10000);
		//2A 49 44 4E 3F 
		byte [] uart=new byte[]{0x2A,0x49,0x44,0x4E,0x3F,0x0D,0x0A};
        if (channel.isActive()) {//连接后请求设备发送自己的设备编号...用于server服务宕机后重新连接所有设备
            channel.writeAndFlush(uart);
        }

	}

	/**
	 * 客户端与服务端断开连接时调用
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		// SocketAddress address = channel.remoteAddress();
		ServerOfDevContainer.group.remove(channel);
		String id = ServerOfDevContainer.sourceIds.get(channel);
		if (id != null || "".equals(id)) {
			ServerOfDevContainer.sourceIds.remove(channel);
			ServerOfDevContainer.sourceChannels.remove(id);

			ServerOfDevContainer.warnSet.add(id);

			// MonitorController monitorController=new MonitorController();
			// monitorController.uploadDevStatus(id,ConstantPool.NETWORK_FAULT);//上传通信故障

		}
		logger.warn("断开："+ctx+"设备：" + id + " 与服务端连接关闭...");
	}

	/**
	 * 服务端接收客户端发送过来的数据结束之后调用
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
		System.out.println("信息接收完毕...");
	}

	/**
	 * 心跳监测1分25秒内未上传定义为通信中断 DTU本身断开重连会极短的时间内重连
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);

		if (evt instanceof IdleStateEvent) {

			IdleStateEvent event = (IdleStateEvent) evt;

			if (event.state().equals(IdleState.READER_IDLE)) {
				Channel channel = ctx.channel();

				String id = ServerOfDevContainer.sourceIds.get(channel);
				if ("".equals(id) || "null".equals(id) || null == id) return;
				ServerOfDevContainer.sourceIds.remove(channel);
				ServerOfDevContainer.sourceChannels.remove(id);
				ServerOfDevContainer.warnSet.add(id);
				// MonitorController monitorController=new MonitorController();
				// monitorController.uploadDevStatus(id,ConstantPool.NETWORK_FAULT);//上传通信故障
				System.out.println("通信故障上传" + id);

			}

		}
	}

	/**
	 * 工程出现异常的时候调用
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		// ctx.close();
	}

	/**
	 * 服务端(中间件)处理客户端(硬件)请求的核心方法，这里接收了客户端(硬件)发来的信息
	 */
	@Override
	public void channelRead(ChannelHandlerContext channelHandlerContext, Object info)
			throws Exception {

		UART uart;
		try {
			uart = (UART) info;
		} catch (Exception e) {
			logger.error("协议错误");
			return;
		}
		QueueElement queueElement = new QueueElement(uart,channelHandlerContext);
		EquipDataSendWebQueue.getLinkedBlockingQueue().put(queueElement);
		queueElement=null;//help GC
		channelHandlerContext.writeAndFlush(sendToDev());
	}

	/**
	 * 每次得到数据时进行回复,统一回复 01 0d 0a
	 * @return
	 */
	public byte[] sendToDev(){
		byte[] bytes={0x01,0x0D,0x0A};
		return bytes;
	}
}
