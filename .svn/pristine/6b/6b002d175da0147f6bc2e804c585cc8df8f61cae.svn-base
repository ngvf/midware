package cn.com.jr.HTUmidware.serverofweb;

import cn.com.jr.HTUmidware.queue.TCPQueue.webdatasendequip.returnwebresultpackage.ReturnWebResult;
import cn.com.jr.HTUmidware.serverofweb.protocol.Type;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.jr.HTUmidware.queue.QueueElement;
import cn.com.jr.HTUmidware.queue.TCPQueue.webdatasendequip.WebDataSendEquipQueue;
import cn.com.jr.HTUmidware.serverofdev.protocol.UART;
import cn.com.jr.HTUmidware.serverofweb.protocol.SimpleProduct;

/**
 * web端服务处理器
 */
public class ServerOfWebHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ServerOfWebHandler.class);

    /**
     * 工程出现异常的时候调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 服务端处理客户端请求的核心方法，这里接收了客户端发来的信息
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object info)
            throws Exception {
        try {
            if (info instanceof SimpleProduct) {
                SimpleProduct simpleProduct = (SimpleProduct) info;
                UART uart = null;
                /*-----------没有想到更好的方式，暂时不做提取----------------------------*/
                String value = new String(simpleProduct.getContent());
                String webSysType = simpleProduct.getWebSysType();
                Class clazz = Type.getClazz(webSysType);
                if (clazz != null && value != null) {
                    Object obj = JSONObject.parseObject(value, clazz);
                    uart = (UART) obj;
                } else {
                    Channel cannel = channelHandlerContext.channel();
                    try {
                        ReturnWebResult.getInstance().retIsNoOperationSystem(webSysType, cannel, value);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return;
                }
                /*---------------------------------------*/

                QueueElement queueElement = new QueueElement(uart, channelHandlerContext);
                WebDataSendEquipQueue.getLinkedBlockingQueue().put(queueElement);
                queueElement = null;// help GC

            }
        } catch (Exception e) {
            logger.error("协议错误");
            return;
        }

    }


}
