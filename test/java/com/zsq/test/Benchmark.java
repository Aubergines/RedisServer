package com.zsq.test;

import com.zsq.redis.IdGenerator;
import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.CountDownLatch;

/**
 * 同时开启多个线程每个线程一次性获取若干个ID
 */
public class Benchmark {
	public static void main(String[] args) throws InterruptedException {
		Benchmark benchmark = new Benchmark();
		benchmark.test();
	}

	public void test() throws InterruptedException {
		int threadCount = 20;
		final int genCount = 100;
		StopWatch watch = new StopWatch();
		
		final CountDownLatch latch = new CountDownLatch(threadCount);

		final IdGenerator idGenerator = IdGenerator.builder()
				.addHost("fce3758b2e0af6cbf8fea4d42b379cd0dc374418")
				.build();
		
		watch.start();
		for (int i = 0; i < threadCount; ++i) {
			Thread thread = new Thread() {
				public void run() {
					for (int j = 0; j < genCount; ++j) {
						idGenerator.next("test", j);
					}
					latch.countDown();
				}
			};
			thread.start();
		}

		latch.await();
		watch.stop();

		System.err.println("time:" + watch);
		System.err.println("speed:" + genCount * threadCount / (watch.getTime() / 1000.0));
	}
}
