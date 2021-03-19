package cn.com.jr.HTUmidware.serverofdev.codec;

import cn.com.jr.HTUmidware.configuration.ConfigContext;
import cn.com.jr.HTUmidware.configuration.ProtocolConfig;
import cn.com.jr.HTUmidware.serverofdev.protocol.AbstractUART;
import cn.com.jr.HTUmidware.serverofdev.protocol.ProtocolFactory;
import cn.com.jr.HTUmidware.util.Tools;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Arrays;
import java.util.List;

/**
 *
 * 简单协议的解码器
 */
public class UARTDecoder extends ByteToMessageDecoder {

	private static ProtocolConfig protocolConfig =ProtocolConfig.getInstace();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out)
			throws Exception {
		if (buffer.readableBytes() >= AbstractUART.MIN_LENGTH) {

			// 防止消息过大,客户端攻击
			/*
			 * if (buffer.readableBytes() > AbstractUART.MAX_LENGTH) {
			 * buffer.skipBytes(buffer.readableBytes()); return; }
			 */

			int startReader;

			ConfigContext instace = ConfigContext.getInstace();

			byte mark;
			startReader = buffer.readerIndex();
			mark = buffer.readByte();
//			while (true) {
//				startReader = buffer.readerIndex();
//				ByteBuf markReaderIndex = buffer.markReaderIndex();
//
//				mark = buffer.readByte();
//				if (isSupport(instace, mark)) {
//					break;
//				}
//
//				buffer.resetReaderIndex();
//				buffer.readByte();
//
//				if (buffer.readableBytes() < AbstractUART.MIN_LENGTH) { return; }
//			}

			// int length = instace.getProtocolConfig().getLength(mark);

			
			buffer.readerIndex(startReader);
			int writerIndex = buffer.writerIndex();//应该用这种方式：ByteBuf可读容量 = writerIndex - readerIndex
//			int readableBytes = buffer.readableBytes();
			if (buffer.readableBytes() < writerIndex) {
				// 等待数据到齐
				return;
			}

			byte[] data = new byte[writerIndex];
			buffer.readBytes(data);

			if(Tools.isUARTInfo(data))
				return;

			System.out.println("通道信息："+ctx+"得到数据"+Arrays.toString(data));
			Object uart = ProtocolFactory.createUART(mark, data, ctx);

			if(Tools.isEmpty(uart))
				return;

			out.add(uart);

		}
	}

	/**
	 * 判断是否支持协议
	 *
	 * @param instace
	 * @param mark
	 * @return
	 */
	private boolean isSupport(ConfigContext instace, byte mark) {
		return instace.getProtocolConfig().getReceiveContainer().contains(mark);
	}
}
