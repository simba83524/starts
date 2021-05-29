package com.simba.system.manager.factory;

import com.simba.common.utils.SpringUtils;
import com.simba.system.entity.OperLog;
import com.simba.system.service.OperLogService;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author hekang
 */
public class AsyncFactory {


    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final OperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                SpringUtils.getBean(OperLogService.class).insertOperlog(operLog);
            }
        };
    }

}