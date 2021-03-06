package cn.com.jr.HTUmidware.serverofdev.supagreementserver.UDP;

import cn.com.jr.HTUmidware.queue.QueueElement;
import cn.com.jr.HTUmidware.queue.TCPQueue.equipdatasendweb.EquipDataSendWebQueue;
import cn.com.jr.HTUmidware.serverofdev.protocol.ProtocolFactory;
import cn.com.jr.HTUmidware.serverofdev.protocol.UART;
import cn.com.jr.HTUmidware.util.Tools;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
	private static Logger logger = LoggerFactory.getLogger(ChineseProverbServerHandler.class);

	private String nextQuote() {
		// 线程安全随机类，避免多线程环境发生错误
//		int quote = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
//		return DICTIONARY[quote];
		return null;
	}

	// 接收Netty封装的DatagramPacket对象，然后构造响应消息
	@Override
	public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {

		String req = packet.content().toString(CharsetUtil.UTF_8);
		byte[] bytes = req.getBytes();

		if(Tools.isUARTInfo(bytes))
			return;

		Object uart = ProtocolFactory.createUART(bytes[0], bytes, ctx);

		UART abstrUart;
		try{
			abstrUart=(UART) uart;
		} catch (Exception e) {
			logger.error("上传的UDP数据有误,"+e.getMessage()+"数据为:"+bytes);
			throw new Exception("UDP--未找到对应协议"+e.getMessage());
		}
	
		/*
		 *这里有可能会造成栈溢出，即申请不到足够的空间，当然也是后面数据量超级大的时候，目前公司业务还不会出现
		 *解决方案目前只想到2种：1.扩充内存 
		 *                 2.如果上传上来的数据会导致栈溢出，可以先才用其他方式存储，等有足够的内存后再去做响应操作
		 */
		
		QueueElement queueElement = new QueueElement(abstrUart,ctx);
		EquipDataSendWebQueue.getUDPLinkedQueue().put(queueElement);
		queueElement=null;//help GC

		ctx.writeAndFlush(new DatagramPacket(Unpooled.wrappedBuffer(sendToDev()),packet.sender()));

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		cause.printStackTrace();
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
