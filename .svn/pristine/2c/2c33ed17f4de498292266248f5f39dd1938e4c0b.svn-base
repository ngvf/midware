package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import cn.com.jr.HTUmidware.serverofdev.protocol.receive.receivedatastrategy.HexByArraysToDeci;

public class thread {

	// private static final Exchanger<String> exgr = new Exchanger<String>();
	// private static ExecutorService threadPool =
	// Executors.newFixedThreadPool(2);
	// public static void main(String[] args) {
	// threadPool.execute(new Runnable() {
	// @Override public void run() {
	// try {
	// String A = "银行流水A"; // A录入银行流水数据
	// exgr.exchange(A);
	// } catch (InterruptedException e) {
	//
	// } } });
	// threadPool.execute(new Runnable() {
	// @Override public void run() {
	// try {
	// String B = "银行流水b"; // B录入银行流水数据
	// String A = exgr.exchange("B");
	// System.out.println("A和B数据是否一致：" + A.equals(B) + "，A录入的是："
	// + A + "，B录入是：" + B);
	// }catch (InterruptedException e) {
	//
	// }
	// }
	// });
	// threadPool.shutdown();
	// }
	private static ExecutorService threadPoolds= Executors.newScheduledThreadPool(5);
	private static final int THREAD_COUNT = 30;
	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
	private static Semaphore s = new Semaphore(10);

	public static void main(String[] args) {
		for (int i = 0; i < THREAD_COUNT; i++) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						s.acquire();
						System.out.println("save data");
						Thread.sleep(5000);
						s.release();
					} catch (InterruptedException e) {
					}
				}
			});
		}
		threadPool.shutdown();
	}
	
	
	
	
}
