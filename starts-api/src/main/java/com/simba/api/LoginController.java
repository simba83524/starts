package com.simba.api;

import com.nimbusds.jose.jwk.JWKSet;
import com.simba.auth.config.KeyConfig;
import com.simba.common.enums.BusinessType;
import com.simba.common.response.R;
import com.simba.system.entity.User;
import com.simba.system.log.annotation.Log;
import com.simba.system.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * @author chenjun
 * @date 2021-05-18
 * @time 14:50
 * @Description: 登录控制
 */
@Api(value = "登录控制")
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWKSet jwkSet;

    @Log(title = "获取当前用户",businessType = BusinessType.OTHER)
    @ApiOperation(value = "获取当前用户")
    @RequestMapping("/getCurrentUser")
    public R getCurrentUser(HttpServletRequest request, Authentication authentication){
        String header = request.getHeader("Authorization");
        String token =  header.substring(header.indexOf("Bearer")+7);
        log.debug("授权信息Authentication: {}",authentication.getPrincipal());
        Object object = Jwts.parser()
                .setSigningKey(KeyConfig.getVerifierKey())
                .parseClaimsJws(token)
                .getBody();
        return R.ok(authentication.getAuthorities());
    }

    @Log(title = "用户信息",businessType = BusinessType.OTHER)
    @ApiOperation(value = "用户信息")
    @RequestMapping("/view")
    public R hello(HttpServletRequest request, Authentication authentication){
        String header = request.getHeader("Authorization");
        String token =  header.substring(header.indexOf("Bearer")+7);
        Claims claims = Jwts.parser()
                .setSigningKey(KeyConfig.getVerifierKey())
                .parseClaimsJws(token)
                .getBody();
        String username = claims.get("user_name").toString();
        log.debug("用户名：{},公钥：{}",username,KeyConfig.getVerifierKey());
        User user = userService.getUserByUsername(username);
        user.setPassword(null);
        return R.ok(claims);
    }
}