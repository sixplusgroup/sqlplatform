package com.example.sqlexercise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AsyncThreadPoolConfig {
    /**
     * 若不使用自定义线程池，@Async会使用SimpleAsyncTaskExecutor
     * 它不是严格意义上的线程池，达不到线程复用的功能。
     */
    private static final int MAX_POOL_SIZE = 10;
    private static final int CORE_POOL_SIZE = 5;
    private static final int TASK_NUM = 5;
    private static final int ACTIVE_TIME = 60;

    @Bean("asyncTaskExecutor")
    public AsyncTaskExecutor asyncTaskExecutor(){
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        asyncTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        asyncTaskExecutor.setQueueCapacity(TASK_NUM);
        asyncTaskExecutor.setKeepAliveSeconds(ACTIVE_TIME);
        asyncTaskExecutor.setThreadNamePrefix("async-task-thread-pool");
        asyncTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //CallerRunsPolicy策略：当线程池没有处理能力的时候，该策略会直接在execute方法的调用线程中
        //运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }

}
