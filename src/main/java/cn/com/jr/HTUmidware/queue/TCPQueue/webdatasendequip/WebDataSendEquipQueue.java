package cn.com.jr.HTUmidware.queue.TCPQueue.webdatasendequip;

import java.util.concurrent.LinkedBlockingQueue;

import cn.com.jr.HTUmidware.queue.QueueElement;

/**
 * 
 * @author yangdd
 * web发到中间件的数据  的存储容器
 *
 */
public class WebDataSendEquipQueue extends LinkedBlockingQueue<Object>  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static LinkedBlockingQueue<QueueElement> linkedBlockingQueue=new LinkedBlockingQueue<QueueElement>();
    
   /*
    * 分析要不要加同步呢？
    */
	public static LinkedBlockingQueue<QueueElement> getLinkedBlockingQueue() {
		return linkedBlockingQueue;
	}

	public static void setLinkedBlockingQueue(LinkedBlockingQueue<QueueElement> linkedBlockingQueue) {
		WebDataSendEquipQueue.linkedBlockingQueue = linkedBlockingQueue;
	}
	
	  


}
