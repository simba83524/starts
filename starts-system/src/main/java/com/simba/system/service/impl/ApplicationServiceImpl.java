package com.simba.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simba.system.entity.Application;
import com.simba.system.mapper.ApplicationMapper;
import com.simba.system.service.ApplicationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 应用管理 服务实现类
 * </p>
 *
 * @author chenjun
 * @since 2021-05-26
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

}
