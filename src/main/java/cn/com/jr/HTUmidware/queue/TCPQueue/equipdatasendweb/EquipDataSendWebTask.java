package cn.com.jr.HTUmidware.queue.TCPQueue.equipdatasendweb;

import cn.com.jr.HTUmidware.controller.Controller;
import cn.com.jr.HTUmidware.proxy.SimpleFactory;
import cn.com.jr.HTUmidware.queue.QueueElement;
import cn.com.jr.HTUmidware.queue.TCPQueue.equipdatasendweb.transfuart.TransfUart;
import cn.com.jr.HTUmidware.queue.TCPQueue.equipdatasendweb.transfuart.WyjcProjectTransfUart;
import cn.com.jr.HTUmidware.queue.TCPQueue.equipdatasendweb.transfuart.XjProjectTransfUart;
import cn.com.jr.HTUmidware.serverofdev.protocol.UART;
import cn.com.jr.HTUmidware.serverofdev.protocol.receive.EquipCodeUART;
import cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy.UartDataStrategy;
import cn.com.jr.HTUmidware.serverofdev.protocol.receive.ReplayUART;
import cn.com.jr.HTUmidware.serverofdev.supagreementserver.TCP.ServerOfDevContainer;
import cn.com.jr.HTUmidware.util.Tools;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author yangdd 设备数据发送到web平台任务
 *
 */
public class EquipDataSendWebTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(EquipDataSendWebTask.class);

	final Lock lock = new ReentrantLock();

	private static Map<Byte, TransfUart> transfUart = new HashMap<Byte, TransfUart>();
	static {
		transfUart.put((byte) 104, new WyjcProjectTransfUart());
		transfUart.put((byte) -9, new XjProjectTransfUart());
	}

	public volatile int queueEleNum = 0;
	public volatile int count = 0;

	@Override
	public void run() {
		while (true) {
			lock.lock();
			QueueElement take = null;
			try {
				// 获取队列中的第一个任务元素
				// QueueElement peek = EquipDataSendWebQueue.peek();
				take = EquipDataSendWebQueue.getLinkedBlockingQueue().take();
				System.out.println("设备消息送到消息队列,take后队列有多少元素："
						+ EquipDataSendWebQueue.getLinkedBlockingQueue().size());
				// 向web平台发送请求（这里是将本来在ServerHandler中处理的，放在这里处理）
				ChannelHandlerContext ctx = take.getCtx();
				UART uart = take.getUart();
				String equipmentCode = uart.getEquipmentCode();
				if (uart instanceof ReplayUART) {
					logger.info("心跳,回复设备" + Arrays.toString(uart.getData()));
					ctx.write(uart.getData());// 不回复心跳数据会造成硬件单方面认为服务端死机
					if (Tools.isEmpty(ServerOfDevContainer.sourceIds.get(ctx))) {// 如果是未注册的心跳则发送请求注册数据
						Thread.sleep(2000);
						byte[] data = new byte[] { 0x2A, 0x49, 0x44, 0x4E, 0x3F, 0x0D, 0x0A };
						ctx.write(data);
					}
				}
				else if (uart instanceof EquipCodeUART && Tools.isNotEmpty(equipmentCode)) {
					addRequipAndChannlConnect(equipmentCode, ctx);
				}
				else {
					sendToWeb(ctx, uart);
				}
				// 从队列中移除已经处理的请求
				// EquipDataSendWebQueue.pop();//因为使用是take，不用去删除
				System.out.println("设备消息送到的消息队列中的元素已有几个被做了任务：" + (++queueEleNum));
				ctx.flush();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("EquipDataSendWebTask中向web平台发送请求出错:" + e);
			} finally {
				lock.unlock();
			}

		}
	}

	/**
	 * 
	 * @author yangdd
	 * @param channelHandlerContext
	 * @param uart
	 *            向web平台发送请求
	 */
	/**
	 * @author ydd
	 */
	private void sendToWeb(ChannelHandlerContext channelHandlerContext, UART uart) {
		String equipmentCode = uart.getEquipmentCode();
		String typeCode = uart.getTypeCode();
		String dataLength = uart.getDataLength();
		byte[] mark = uart.getMark();
		// 判断设备和通道是否在容器中
		boolean result = judgeRequipAndChannlConn(channelHandlerContext, equipmentCode, typeCode,
				dataLength);
		// 设备与通道不在容器中直接返回
		if (!result) {
			return;
		}
		// 设备和通道是在容器中才对内容数据（ContentData）进行解析
		// UartDatadStrategy uartDatadStrategy = transfUartMap.get(typeCode +
		// "," + dataLength);
		UartDataStrategy uartDatadStrategy = null;
		String frameHeader = uart.getFrameHeader();
		String para = null;
		if ("h".equals(frameHeader)) {
			para = typeCode + "," + dataLength;
		}else{
			para = typeCode;
		}
		uartDatadStrategy = transfUart.get(uart.getMark()[0]).getSpecificUartDatadStrategy(para);
		if (uartDatadStrategy == null) return;
        //真正对内容解析
		String parseAfterDataStr = uartDatadStrategy.conversionData(uart.getContentData());
		uart.setParseAfterData(changeDataFormat(parseAfterDataStr));

		Channel channel = channelHandlerContext.channel();
		String id = ServerOfDevContainer.sourceIds.get(channel);
		logger.info("服务端接收到设备：" + id + " 的数据：" + uart.toString());
		if (Tools.isNullEmpty(id)) {
			logger.info("设备编号:" + uart.getEquipmentCode() + "没有和通道" + channel + "建立连接，不能向平台发送数据！");
		}
		else {
			Controller controller = SimpleFactory.createController(channelHandlerContext, uart);
			if (controller != null) {
				controller.executor();
			}
			else {
				logger.warn("未找到对应的控制器!");
			}
		}

	}

	/**
	 * 
	 * @author yangdd
	 * @param ctx
	 * @param equipmentCode
	 * @param typeCode
	 * @param dataLength
	 * @return 判断设备和通道是否在容器中
	 */
	private boolean judgeRequipAndChannlConn(ChannelHandlerContext ctx, String equipmentCode,
			String typeCode, String dataLength) {
		boolean connectflag = true;
		if ("9,12".equals(typeCode + "," + dataLength) || "B0".equals(typeCode)) {
			connectflag = addRequipAndChannlConnect(equipmentCode, ctx);
		}
		else {
			connectflag = isRequipAndChannlConnect(equipmentCode, ctx);
		}
		;

		return connectflag;
	}

	/**
	 * 
	 * @author yangdd
	 * @param equCode
	 * @param channelHandlerContext
	 * @return 增加设备和通道注册
	 */
	private boolean addRequipAndChannlConnect(String equCode,
			ChannelHandlerContext channelHandlerContext) {
		// TODO Auto-generated method stub
		Channel channel = channelHandlerContext.channel();
		String idString = equCode;
		Map<String, Channel> sourceChannels = ServerOfDevContainer.sourceChannels;
		Map<Channel, String> sourceIds = ServerOfDevContainer.sourceIds;
		if (idString != null && channel != null) {
			Channel cha = sourceChannels.get(idString);
			if (cha != null && !cha.equals(channel)) {
				logger.warn("设备：" + idString + " 已与通道关联，已将此前设备通道关闭，移除旧通道对应id，或请检查设备id是否重复！");

				cha.close();
				sourceIds.remove(cha);// 移除旧通道对应id
				sourceChannels.remove(idString);// 移除id对应旧通道
				// return false;
			}
			sourceChannels.put(idString, channel);
			sourceIds.put(channel, idString);

			logger.info("设备：" + idString + " 与通道关联成功.");
			// for (String key : sourceChannels.keySet()) {// 查看所有通道与设备编号的对应关系
			// Channel allchannel = sourceChannels.get(key);
			// System.out.print("key:" + key + ",value:"
			// +allchannel.toString());
			// }
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @author yangdd
	 * @param equCode
	 * @param channelHandlerContext
	 * @return
	 * 
	 *         此方法的目的是判断设备与通道是否注册
	 *
	 */
	private boolean isRequipAndChannlConnect(String equCode,
			ChannelHandlerContext channelHandlerContext) {
		// TODO Auto-generated method stub
		Channel channel = channelHandlerContext.channel();
		String idString = equCode;
		Map<String, Channel> sourceChannels = ServerOfDevContainer.sourceChannels;
		if (idString != null && channel != null) {
			Channel cha = sourceChannels.get(idString);
			if (cha != null && !cha.equals(channel)) {
				logger.warn("设备：" + idString + " 已与通道关联，已将此前设备通道关闭，请检查设备id是否重复！");
				cha.close();
				return false;
			}
			else if (cha != null && cha.equals(channel)) {
				// 返回成功数据
				logger.info("设备：" + idString + " 已与通道" + cha + "关联，存在sourceChannels容器中");
				return true;
			}

		}
		// 返回设备ID或通道为空的数据提示
		logger.warn("设备：" + idString + " ,没有与通道" + channel + "关联，请先设备开始上传操作！");
		return false;
	}

	/**
	 * 
	 * @author yangdd
	 * @param data
	 * @return //将data数据统一转成[x,x,x]的格式,方便使用数据
	 */

	private static String changeDataFormat(String data) {
		String dataRep = data.replaceAll("\\[", "");
		String reDataRep = dataRep.replaceAll("\\]", "");
		reDataRep = reDataRep.substring(0, reDataRep.length() - 1);
		StringBuilder retStr = new StringBuilder();
		retStr.append("[");
		retStr.append(reDataRep);
		retStr.append("]");
		return retStr.toString();
	}

}
