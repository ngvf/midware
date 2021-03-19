package cn.com.jr.HTUmidware.queue.UDPQueue;

import cn.com.jr.HTUmidware.controller.Controller;
import cn.com.jr.HTUmidware.proxy.SimpleFactory;
import cn.com.jr.HTUmidware.queue.QueueElement;
import cn.com.jr.HTUmidware.queue.TCPQueue.equipdatasendweb.EquipDataSendWebQueue;
import cn.com.jr.HTUmidware.serverofdev.protocol.UART;
import cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy.UartDataStrategy;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UDPSendToWebQueue  implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(UDPSendToWebQueue.class);

    final Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();
            QueueElement take = null;
            try {
                // 获取队列中的第一个任务元素
                // QueueElement peek = EquipDataSendWebQueue.peek();
                take = EquipDataSendWebQueue.getUDPLinkedQueue().take();

                // 向web平台发送请求（这里是将本来在ServerHandler中处理的，放在这里处理）
                sendToWeb(take.getCtx(),take.getUart());

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("UDPSendToWebQueue向web平台发送请求出错:" + e);
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
     * @author yuhy
     */
    private void sendToWeb(ChannelHandlerContext channelHandlerContext, UART uart) {
        String equipmentCode = uart.getEquipmentCode();
        String typeCode = uart.getTypeCode();
        String dataLength = uart.getDataLength();
        byte[] mark = uart.getMark();

        UartDataStrategy uartDatadStrategy = null;
        String frameHeader = uart.getFrameHeader();
        String para = null;

        Controller controller = SimpleFactory.createController(channelHandlerContext, uart);
        if (controller != null)  controller.executor();

        else logger.warn("未找到对应的控制器!");


    }
}
