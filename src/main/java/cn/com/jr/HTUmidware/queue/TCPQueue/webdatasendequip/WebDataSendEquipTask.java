package cn.com.jr.HTUmidware.queue.TCPQueue.webdatasendequip;

import cn.com.jr.HTUmidware.queue.QueueElement;
import cn.com.jr.HTUmidware.queue.TCPQueue.webdatasendequip.returnwebresultpackage.ReturnWebResult;
import cn.com.jr.HTUmidware.serverofdev.protocol.UART;
import cn.com.jr.HTUmidware.serverofdev.supagreementserver.TCP.ServerOfDevContainer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author yangdd web平台任务数据发送到设备
 *
 */
public class WebDataSendEquipTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(WebDataSendEquipTask.class);

	final Lock lock = new ReentrantLock();

	public volatile int queueEleNum = 0;
	public volatile int count = 0;

	@Override
	public void run() {
		while (true) {
			lock.lock();
			QueueElement take = null;
			try {
				// 获取队列中的第一个任务元素
				take = WebDataSendEquipQueue.getLinkedBlockingQueue().take();
				System.out.println("web平台发送到消息队列,take后队列有多少元素："
						+ WebDataSendEquipQueue.getLinkedBlockingQueue().size());
				// 向设备发送请求（这里是将本来在ServerHandler中处理的，放在这里处理）
				ChannelHandlerContext ctx = take.getCtx();
				UART uart = take.getUart();
				String equipmentCode = uart.getEquipmentCode();
				sendToEquip(ctx, uart);
				// 从队列中移除已经处理的请求
				// 因为使用是take，不用去删除
				System.out.println("web平台发送到消息队列中的元素已有几个被做了任务：" + (++queueEleNum));
				ctx.flush();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("WebDataSendEquipQueue中向设备发送请求出错:" + e);
			} finally {
				lock.unlock();
			}

		}
	}

	/**
	 * @author ydd 向设备发送数据
	 * @throws Exception 
	 */
	private void sendToEquip(ChannelHandlerContext channelHandlerContext, UART uart) throws Exception {

		Channel cannel = channelHandlerContext.channel();
//		try {
			if (uart instanceof UART) {

				logger.info("收到web端请求：" + "type:" + uart.getTypeCode() + "value:" + uart.toString());

				byte[] data = null;
				String id = null;
				try {
					uart.parse();
					data = uart.getData();
					id = uart.getEquipmentCode();
				} catch (Exception e) {
					ReturnWebResult.getInstance().retIsParseError(id, cannel, uart);
					return;
				}
				if (id == null || "".equals(id)) {
					ReturnWebResult.getInstance().retIsEquipIdIsNULL(id, cannel, uart);
					return;
				}
				Channel channel = ServerOfDevContainer.sourceChannels.get(id);
				if (channel != null && channel.isActive()) {
					channel.writeAndFlush(data);
					String s = "";
					for (byte datum : data) {
						s = s + datum + " ";
					}
					ReturnWebResult.getInstance().retWebSuccess(id, cannel, s);
				}
				else {
					ReturnWebResult.getInstance().retIsEquipIdUnregister(id, cannel, uart);
					return;
				}

			}
//		} catch (Exception e) {
//			cannel.writeAndFlush(e + "\r\n");
//			logger.error("web接口调用错误!", e);
//		}
	}

}
