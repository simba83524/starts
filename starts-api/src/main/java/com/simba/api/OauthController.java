package com.simba.api;

import com.nimbusds.jose.jwk.JWKSet;
import com.simba.common.enums.BusinessType;
import com.simba.common.response.R;
import com.simba.system.log.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * @author chenjun
 * @date 2021-05-19
 * @time 19:22
 * @Description: 登录授权
 */
@Api(value = "登录认证")
@RestController
@Slf4j
@RequestMapping("/oauth")
public class OauthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private JWKSet jwkSet;

    @Log(title = "系统认证", businessType = BusinessType.GRANT)
    @ApiOperation(value = "授权接口")
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public R<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        log.info("====类名：{}，方法名：{}，身份信息：{}，参数信息：{}",this.getClass().getName(),Thread.currentThread().getStackTrace()[1].getMethodName(),principal,parameters);
        return R.ok(oAuth2AccessToken);
    }


    @Log(title = "获取公钥",businessType = BusinessType.GRANT)
    @ApiOperation(value = "获取公钥")
    @GetMapping(value = "/keys", produces = "application/json; charset=UTF-8")
    public R keys() {
        return R.ok(jwkSet);
    }
}
