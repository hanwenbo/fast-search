package com.github.search.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class UniqueIDUtils {
	private static final Logger logger = LoggerFactory.getLogger(UniqueIDUtils.class);

	// 取IP地址最后一段作为多机分隔段
	private static long SCF_SERVER_ID = 255L;

	private UniqueIDUtils(){}
	
	static {
		try {
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			String[] nums = hostAddress.split("\\.");
			SCF_SERVER_ID = Long.parseLong(nums[3]);
		} catch (Exception ex) {
			logger.error("UniqueIDUtils init Exception, Default SCF_SERVER_ID = " + SCF_SERVER_ID, ex);
		}

	}

	private static final long UniqueID_BEGIN_TIME = 1388505600000L;

	// unique id 提前生成后保存在Queue里
	private static Queue<Long> uniqueIdQueue = new ConcurrentLinkedQueue<Long>();

	private static final int SCALE_CEILING = 6;
	private static final int SCALE_FLOOR = 1;
	private static final int DEFAULT_SIZE_DIVIDE = 10;
	private static final int DEFAULT_SIZE_CEILING = 12;

	private static int sizeDivide = DEFAULT_SIZE_DIVIDE;
	private static int sizeCeiling = DEFAULT_SIZE_CEILING + 5;

	private static Queue<Integer> scaleQueue = new ConcurrentLinkedQueue<Integer>();
	private static int totalScale = 0;

	private static int scale = SCALE_FLOOR;

	private static long CURRENT_MILLIS = 0L;
	private static int lastSize = 0;
	private static long lastStamp = 0L;

	private static ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

	// unique id generate task
	private static Callable<Integer> uidGenerateTask = new Callable<Integer>() {

		@Override public Integer call() {

			if (UniqueIDUtils.uniqueIdQueue.size() < UniqueIDUtils.sizeDivide) {

				if (UniqueIDUtils.lastStamp > 0) {
					//非第一轮执行时，根据近期生成速率确定指标：scale、sizeDivide、sizeCeiling
					reDetermineIndicator();
				}

				// 将uid存量补充到>=sizeCeiling
				while (UniqueIDUtils.uniqueIdQueue.size() < UniqueIDUtils.sizeCeiling) {
					UniqueIDUtils.generateNewId();
				}

				// 记录快照，用于计算每毫秒生成的uid容量
				lastStamp = System.currentTimeMillis();
				lastSize = UniqueIDUtils.uniqueIdQueue.size();

			}

			// 目前没有用call的执行结果，暂时返回此轮id生成前的存量
			return UniqueIDUtils.uniqueIdQueue.size();
		}

		/**
		 * 根据近期生成速率确定指标：scale、sizeDivide、sizeCeiling
		 */
		private void reDetermineIndicator() {

			long velocity = ((UniqueIDUtils.lastSize - UniqueIDUtils.uniqueIdQueue.size()) << 2) / (
					System.currentTimeMillis() - UniqueIDUtils.lastStamp);

			int tempScale = UniqueIDUtils.scale;
			if (velocity > 1) {
				while (tempScale > UniqueIDUtils.SCALE_FLOOR && velocity < (1 << tempScale)) {
					tempScale--;
				}
				while (tempScale < UniqueIDUtils.SCALE_CEILING && velocity > (1 << tempScale)) {
					tempScale++;
				}
			} else {
				tempScale = UniqueIDUtils.SCALE_FLOOR;
			}

			UniqueIDUtils.scaleQueue.add(tempScale);
			totalScale += tempScale;
			if (UniqueIDUtils.scaleQueue.size() > 3) {
				totalScale -= UniqueIDUtils.scaleQueue.poll();
			}

			if (UniqueIDUtils.scale != UniqueIDUtils.totalScale / UniqueIDUtils.scaleQueue.size()) {
				scale = UniqueIDUtils.totalScale / UniqueIDUtils.scaleQueue.size();
				sizeDivide = UniqueIDUtils.DEFAULT_SIZE_DIVIDE << UniqueIDUtils.scale;
				sizeCeiling = (UniqueIDUtils.DEFAULT_SIZE_CEILING << UniqueIDUtils.scale) + 5;
				//					UniqueIDUtils.logger.info("UniqueIDUtils.scale={}, UniqueIDUtils.sizeDivide={}, UniqueIDUtils.sizeCeiling={}, uniqueIdQueue.size={}", new Object[]{UniqueIDUtils.scale, UniqueIDUtils.sizeDivide, UniqueIDUtils.sizeCeiling, UniqueIDUtils.uniqueIdQueue.size()});
			}
		}
	};

	static {
		exec.schedule(uidGenerateTask, 0L, TimeUnit.MILLISECONDS);
	}

	/**
	 * 获取主键ID
	 *
	 * @return
	 * @throws Exception
	 */
	public static long getUniqueID() {

		if (uniqueIdQueue.size() < sizeDivide) {
			exec.schedule(uidGenerateTask, 0L, TimeUnit.MILLISECONDS);
		}

		Long uid = uniqueIdQueue.poll();
		while (uid == null) {
			logger.info("The uniqueIdQueue is empty! scale={},sizeCeiling={}", scale, sizeCeiling);
			generateNewId();
			uid = uniqueIdQueue.poll();
		}

		return uid.longValue();
	}

	private static synchronized void generateNewId() {

		if (scale > SCALE_CEILING) {
			scale = SCALE_CEILING;
		}
		long destID = System.currentTimeMillis() - UniqueID_BEGIN_TIME;
		if (destID <= CURRENT_MILLIS) {
			destID = ++CURRENT_MILLIS;
		}
		else {
			CURRENT_MILLIS = destID;
		}

		destID = (destID << 8) | SCF_SERVER_ID;
		destID = destID << SCALE_CEILING;
		for (int i = 1 << scale; i > 0; i--) {
			uniqueIdQueue.add(destID++);
		}
	}


}


