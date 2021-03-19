package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPoolExecutor1 {

	/*
	 * 
	 * newSingleThreadExecutor newCachedThreadPool newScheduledThreadPool
	 * newWorkStealingPool
	 */

	// java.util.concurrent.ThreadPoolExecutor.newFixedThreadPool(3);

	// ExecutorService ee= Executors.newFixedThreadPool(4);
	// ExecutorService ee0= Executors.newCachedThreadPool(1);
	// // ExecutorService ee1= Executors.newSingleThreadExecutor(1);
	//
	// ExecutorService ee2= Executors.newFixedThreadPool(1);
	//
	// ExecutorService ee3= Executors.newFixedThreadPool(1);
	//
	// ExecutorService ee4= Executors.newFixedThreadPool(1);

	// 同步和异步针对的是io结果
	// 阻塞和非阻塞 是针对的线程的状态 还是否挂起
	// ConcurrentHashMap<String, String> chm=new ConcurrentHashMap<String,
	// String>();
	// ConcurrentLinkedQueue<String> clq=new ConcurrentLinkedQueue();
	// AtomicBoolean ab=new AtomicBoolean();

//	static CyclicBarrier c = new CyclicBarrier(2, new A());
//
//	public static void main(String[] args) {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					c.await();
//				} catch (Exception e) {
//				}
//				System.out.println(1);
//			}
//		}).start();
//		try {
//			c.await();
//		} catch (Exception e) {
//		}
//		System.out.println(2);
//	}
//
//	static class A implements Runnable {
//		@Override
//		public void run() {
//			System.out.println(3);
//		}
//	}
	
	
	
}
