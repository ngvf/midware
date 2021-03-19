package cn.com.jr.HTUmidware.serverofweb.codec;

import cn.com.jr.HTUmidware.serverofweb.protocol.ConstantValue;
import cn.com.jr.HTUmidware.serverofweb.protocol.SimpleProduct;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 简单协议的解码器
 */
public class SimpleDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out)
			throws Exception {
		if (buffer.readableBytes() >= ConstantValue.BASE_LENGTH) {

			// 防止消息过大,客户端攻击
			if (buffer.readableBytes() > ConstantValue.MAX_LENGTH) {
				buffer.skipBytes(buffer.readableBytes());
				return;
			}

			int startReader;

			while (true) {
				startReader = buffer.readerIndex();
				buffer.markReaderIndex();

				if (buffer.readInt() == ConstantValue.HEAD_DATA) {
					break;
				}

				buffer.resetReaderIndex();
				buffer.readByte();

				if (buffer.readableBytes() < ConstantValue.BASE_LENGTH) {
					return;
				}
			}

			int webSysLength=buffer.readInt();
//			int webSysLength = buffer.readerIndex();
			StringBuffer webSysType=new StringBuffer();
			for (int i = 0; i < webSysLength; i++) {
				char typeC = buffer.readChar();
				webSysType.append(String.valueOf(typeC));
			}

			int contentLength = buffer.readInt();

			if (buffer.readableBytes() < contentLength) {
				// 还原读指针,等待数据到齐
				buffer.readerIndex(startReader);
				return;
			}

			byte[] content = new byte[contentLength];
			buffer.readBytes(content);
			SimpleProduct simpleProduct = new SimpleProduct(webSysLength, webSysType.toString(),contentLength,content);
			out.add(simpleProduct);

		}
		else {
			String s = "协议错误!";
			SimpleProduct simpleProduct = new SimpleProduct(0, "",
					s.length(), s.getBytes());
			ctx.writeAndFlush(simpleProduct);
		}
	}
}
