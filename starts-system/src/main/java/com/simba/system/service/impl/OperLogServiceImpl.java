package com.simba.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simba.system.entity.OperLog;
import com.simba.system.mapper.OperLogMapper;
import com.simba.system.service.OperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author chenjun
 * @since 2021-05-22
 */
@Service
public class OperLogServiceImpl extends ServiceImpl<OperLogMapper, OperLog> implements OperLogService {

    @Autowired
    private OperLogMapper operLogMapper;

    @Override
    public void insertOperlog(OperLog operLog) {
        operLogMapper.insertOperlog(operLog);
    }
}
