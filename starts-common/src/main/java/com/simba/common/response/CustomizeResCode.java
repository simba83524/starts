package com.simba.common.response;

public interface CustomizeResCode {

    /**
     * 获取响应状态码
     *
     * @return 响应状态码
     */
    Integer getCode();

    /**
     * 获取响应信息
     *
     * @return 响应信息
     */
    String getMessage();
}
