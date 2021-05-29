package com.simba.api;


import com.simba.common.response.R;
import com.simba.system.entity.User;
import com.simba.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author chenjun
 * @since 2021-05-16
 */
@Slf4j
@Api(value = "用户操作")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户信息")
    @RequestMapping("/getUserInfo")
    public R getUserInfo(){
        log.info("--------------测试信息--------------");
        User user = userService.getById(1);
        log.info("-----------查询到用户信息：{}",user);
        return R.ok(user);
    }

}