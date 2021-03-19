package cn.com.jr.HTUmidware.serverofdev.supagreementserver.TCP;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.jr.HTUmidware.configuration.ConfigContext;
import cn.com.jr.HTUmidware.serverofdev.codec.UARTDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class TCPServiceOfDev {
    private static final Logger logger = LoggerFactory.getLogger(TCPServiceOfDev.class);
    private Integer port;
    private SocketChannel socketChannel;

    public TCPServiceOfDev(Integer port) throws Exception {
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
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        // 保持连接数    服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024 * 1024);
        // 有数据立即发送
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        // 保持连接
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        // 处理新连接
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel sc) throws Exception {
                // 增加任务处理
                ChannelPipeline p = sc.pipeline();
                p.addLast(
//                        new IdleStateHandler(62, 0, 0, TimeUnit.SECONDS),//心跳处理
                        new UARTDecoder(),
                        new ByteArrayEncoder(),
                        new TCPServerHandler());
            }}
        );

        ChannelFuture f = bootstrap.bind(serverPort).sync();
        if (f.isSuccess()) {
            logger.info("TCPServiceOfDev long connection started success port:"+ ConfigContext.getInstace().getServerPort());
        } else {
            logger.error("TCPServiceOfDev long connection started fail");
        }
    }
	
}
