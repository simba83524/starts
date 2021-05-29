package com.simba.common.properties;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Xss {

    /**
     * Xss开关
     */
    private Boolean enabled;

    /**
     * 排除字段
     */
    private List<String> excludeFields;

    /**
     * 排除路径
     */
    private List<String> excludeUrls;

}