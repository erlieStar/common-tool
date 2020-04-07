package com.javashitang.tool.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncUtil {

    private static final Logger logger = LoggerFactory.getLogger(AsyncUtil.class);

    private static AsyncUtil instance;

    private ExecutorService executorService;

    private AsyncUtil(int maxPoolSize) {
        this.executorService = Executors.newFixedThreadPool(maxPoolSize);
    }

    public static AsyncUtil getInstance(int maxPoolSize) {
        if (instance == null) {
            synchronized (AsyncUtil.class) {
                if (instance == null) {
                    instance = new AsyncUtil(maxPoolSize);
                }
            }
        }
        return instance;
    }

    public static AsyncUtil getInstance() {
        return getInstance(128);
    }

    /**
     * 异步执行任务
     */
    public void commit(AsyncHandler asyncHandler, Object ... args) {
        this.executorService.execute(() -> {
            try {
                asyncHandler.execute(args);
            } catch (Exception e) {
                logger.error("threadpool execute error, args: {}", args, e);
            }
        });
    }
}
