package com.server.cx.http;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpService {
	// private Handler responseHandler;
	private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			5, 8, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	private static final HttpService httpService = new HttpService();

	private HttpService() {
	}

	/**
	 * 
	 * <p>
	 * Title: getInstance
	 * </p>
	 * <p>
	 * Description:get a HttpService Object
	 * </p>
	 * 
	 * @return HttpService
	 */
	public static HttpService getInstance() {
		return httpService;
	}

	/**
	 * 
	 * <p>
	 * Title: executThread
	 * </p>
	 * <p>
	 * Description:to execute a thread into a thread pool and run it
	 * </p>
	 * 
	 * @param parser
	 * @param handler
	 * @param url
	 */
    public void putTask(Command command) {
//        new Thread(new HttpTask(command)).start();
        new HttpTask(command).run();
//        HttpTask httpTask = new HttpTask(command);
//        threadPoolExecutor.execute(httpTask);
    }
}
