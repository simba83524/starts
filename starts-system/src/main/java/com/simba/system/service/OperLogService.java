package com.simba.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simba.system.entity.OperLog;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author chenjun
 * @since 2021-05-22
 */
public interface OperLogService extends IService<OperLog> {

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(OperLog operLog);
}
