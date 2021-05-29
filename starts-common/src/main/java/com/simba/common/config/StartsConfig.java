package com.simba.common.config;

import com.simba.common.properties.Xss;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenjun
 * @date 2021-05-23
 * @time 20:09
 * @Description:starts配置
 */

@Data
@Component
@ConfigurationProperties(prefix = "starts")
public class StartsConfig {
    private Xss xss;
    private List<String> whitelist;
}
