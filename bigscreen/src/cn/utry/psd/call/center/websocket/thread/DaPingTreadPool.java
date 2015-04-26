package cn.utry.psd.call.center.websocket.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * deprecated: 线程池，做数据推送
 * 
 * date 2015-03-09
 * 
 * @author sharkTang
 */
public class DaPingTreadPool extends ThreadPoolExecutor {
	static final int COREPOOLSIZE = 300;
	static final int MAXIMUMPOOLSIZE = 300;
	static final int KEEPALIVETIME = 200;
	static final int ARRAYBLOCKINGQUEUESIZE = 5;
	//private static ExecutorService privatePoolInstance = null;
	private static DaPingTreadPool privatePoolInstance = null;
	/**
	 * deprecated: UtryTreadPool构造方法
	 * 
	 * 初始化线程池
	 * 
	 * date 2015-03-08
	 * 
	 * @author sharkTang
	 * @return UtryTreadPool
	 */
	public DaPingTreadPool(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		// TODO Auto-generated constructor stub

	}

	/**
	 * deprecated: 获得线程池
	 * 
	 * date 2015-03-08
	 * 
	 * @author sharkTang
	 * @return UtryTreadPool
	 */
	public static synchronized  DaPingTreadPool getUtryTreadPool() {
		if (null == privatePoolInstance) {
			//privatePoolInstance = Executors.newCachedThreadPool();   
			/*	privatePoolInstance = Executors.newFixedThreadPool(100);  */
			privatePoolInstance = new DaPingTreadPool(COREPOOLSIZE,
					MAXIMUMPOOLSIZE, KEEPALIVETIME, TimeUnit.MINUTES,
					new ArrayBlockingQueue<Runnable>(ARRAYBLOCKINGQUEUESIZE));
		}
		return privatePoolInstance;
	}
}
