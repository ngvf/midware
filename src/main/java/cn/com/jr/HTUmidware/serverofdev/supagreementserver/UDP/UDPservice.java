package cn.com.jr.HTUmidware.serverofdev.supagreementserver.UDP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UDPservice {

	private static final Logger logger = LoggerFactory.getLogger(UDPservice.class);
	private Integer port;
	private SocketChannel socketChannel;

	public UDPservice(Integer port) throws Exception {
		this.port = port;
		bind(port);
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public SocketChannel getSocketChannel() {
		return socketChannel;
	}

	public void setSocketChannel(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}

	private void bind(int serverPort) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		try {
			// 通过NioDatagramChannel创建Channel，并设置Socket参数支持广播
			// UDP相对于TCP不需要在客户端和服务端建立实际的连接，因此不需要为连接（ChannelPipeline）设置handler
			Bootstrap b = new Bootstrap();
			b.group(bossGroup).channel(NioDatagramChannel.class)// 设置UDP通道
					.option(ChannelOption.SO_BROADCAST, true)// 支持广播
					.option(ChannelOption.SO_RCVBUF, 1024 * 1024)// 设置UDP读缓冲区为1M
					.option(ChannelOption.SO_SNDBUF, 1024 * 1024)// 设置UDP写缓冲区为1M
					.handler(new ChineseProverbServerHandler());// 初始化处理器
			ChannelFuture f = b.bind(port).sync();
			// b.bind(port).sync().channel().closeFuture().await();
			  if (f.isSuccess()) {
		            logger.info("UDPservice long connection started success port:"+ serverPort);
		        } else {
		            logger.error("UDPservice long connection started fail");
		        }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
