package com.afagoal.task.token;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by BaoCai on 18/6/4.
 * Description:
 */
public class TokenTaskHolder {

    public final static int DEFAULT_PAGE_SIZE = 500;

    public final static ThreadPoolExecutor TASK_EXECUTOR = new ThreadPoolExecutor(4, 8, 60,
            TimeUnit.SECONDS, new LinkedBlockingDeque<>(500));

}
