package cn.com.jr.HTUmidware.queue.TCPQueue.equipdatasendweb;

import cn.com.jr.HTUmidware.queue.QueueElement;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * @author yangdd
 *1.第一次修改，使用自己的写的队列，通过查阅资料发现，应该使用阻塞队列
 *设备数据发送到web平台队列
 *此队列为先进先出队列
 *2.第二次修改，改为使用Java中的阻塞队列，根据业务选取什么阻塞队列 LinkedBlockingQueue
 *两个队列尚未 指定队列大小,如果出现内存溢出 请在此处考虑.
 */
public class EquipDataSendWebQueue extends LinkedBlockingQueue<Object>  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * TCP协议队列
	 */
	private static LinkedBlockingQueue<QueueElement> linkedBlockingQueue=new LinkedBlockingQueue<QueueElement>();
	/**
	 * UDP协议队列
	 */
	private static LinkedBlockingQueue<QueueElement> UDPLinkedQueue=new LinkedBlockingQueue<QueueElement>();

	/*
    * 分析要不要加同步呢？
    */
	public static LinkedBlockingQueue<QueueElement> getLinkedBlockingQueue() {
		return linkedBlockingQueue;
	}

	public static void setLinkedBlockingQueue(LinkedBlockingQueue<QueueElement> linkedBlockingQueue) {
		EquipDataSendWebQueue.linkedBlockingQueue = linkedBlockingQueue;
	}


	public static LinkedBlockingQueue<QueueElement> getUDPLinkedQueue() {
		return UDPLinkedQueue;
	}
	public static void setUDPLinkedQueue(LinkedBlockingQueue<QueueElement> UDPLinkedQueue) {
		EquipDataSendWebQueue.UDPLinkedQueue = UDPLinkedQueue;
	}
}
