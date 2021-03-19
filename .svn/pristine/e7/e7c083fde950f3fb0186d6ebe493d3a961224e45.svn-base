package cn.com.jr.HTUmidware.serverofdev.supagreementserver.HTTP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.jr.HTUmidware.configuration.ConfigContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HttpService {
    private static final Logger logger = LoggerFactory.getLogger(HttpService.class);
    private Integer port;
    private SocketChannel socketChannel;

    public HttpService(Integer port) throws Exception {
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
    	  ServerBootstrap bootstrap = new ServerBootstrap();
          EventLoopGroup boss = new NioEventLoopGroup();
          EventLoopGroup work = new NioEventLoopGroup();
          bootstrap.group(boss,work)
                  .handler(new LoggingHandler(LogLevel.DEBUG))
                  .channel(NioServerSocketChannel.class)
                  .childHandler(new ChannelInitializer<SocketChannel>() {
                      @Override
                      protected void initChannel(SocketChannel sc) throws Exception {
                    	  ChannelPipeline pipeline = sc.pipeline();
                          pipeline.addLast(new HttpServerCodec());// http 编解码
                          pipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024)); // http 消息聚合器                                                                     512*1024为接收的最大contentlength
                          pipeline.addLast(new HttpRequestHandler());// 请求处理器
                      }});

        ChannelFuture f = bootstrap.bind(serverPort).sync();
        if (f.isSuccess()) {
            logger.info("HttpService long connection started success port:"+serverPort);
        } else {
            logger.error("HttpService long connection started fail");
        }
    }
	
}
