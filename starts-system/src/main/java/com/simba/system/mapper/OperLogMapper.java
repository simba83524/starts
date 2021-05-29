package com.simba.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simba.system.entity.OperLog;

/**
 * <p>
 * 操作日志记录 Mapper 接口
 * </p>
 *
 * @author chenjun
 * @since 2021-05-22
 */
public interface OperLogMapper extends BaseMapper<OperLog> {

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(OperLog operLog);
}
