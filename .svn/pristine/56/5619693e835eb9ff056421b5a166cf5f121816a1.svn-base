package cn.com.jr.HTUmidware.clientofdev;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class ClientHandler extends SimpleChannelInboundHandler {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道关闭");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println("从服务端接收:"+o);
    }
}