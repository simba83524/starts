package com.simba.system.service;

import com.simba.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author chenjun
 * @since 2021-05-16
 */
@Service
public interface UserService extends IService<User> {

    User getUserByUsername(String username);
}
