package com.simba.system.mapper;

import com.simba.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author chenjun
 * @since 2021-05-16
 */
public interface UserMapper extends BaseMapper<User> {

    User getUserByUsername(String username);
}