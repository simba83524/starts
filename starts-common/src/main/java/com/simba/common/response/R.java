package com.simba.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenjun
 * @Description 公共结果返回
 */

@Data
@ApiModel(value = "R", description = "返回对象")
public class R<T> {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回状态编码")
    private Integer code;

    @ApiModelProperty(value = "返回结果描述")
    private String message;

    @ApiModelProperty(value = "返回对象信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)//该注解是不参与序列化，为null的时候不传递给前台
    private T data;

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> R<T> ok() {
        R<T> response = new R<T>();
        ResCode success = ResCode.COMMON_SUCCESS;
        response.setSuccess(true);
        response.setMessage(success.getMessage());
        response.setCode(success.getCode());
        response.setData(null);
        return response;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> R<T> ok(String msg) {
        R<T> response = new R<T>();
        ResCode success = ResCode.COMMON_SUCCESS;
        response.setSuccess(true);
        response.setMessage(msg);
        response.setCode(success.getCode());
        response.setData(null);
        return response;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> R<T> ok(T obj) {
        R<T> response = new R<T>();
        ResCode success = ResCode.COMMON_SUCCESS;
        response.setSuccess(true);
        response.setMessage(success.getMessage());
        response.setCode(success.getCode());
        response.setData(obj);
        return response;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> R<T> fail(T obj) {
        R<T> response = new R<T>();
        ResCode fail = ResCode.COMMON_FAIL;
        response.setSuccess(false);
        response.setMessage(fail.getMessage());
        response.setCode(fail.getCode());
        response.setData(obj);
        return response;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> R<T> ok(ResCode rs,T obj) {
        R<T> response = new R<T>();
        response.setSuccess(true);
        response.setMessage(rs.getMessage());
        response.setCode(rs.getCode());
        response.setData(obj);
        return response;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> R<T> fail() {
        R<T> response = new R<T>();
        ResCode rs = ResCode.COMMON_FAIL;
        response.setSuccess(false);
        response.setMessage(rs.getMessage());
        response.setCode(rs.getCode());
        response.setData(null);
        return response;
    }
    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> R<T> fail(ResCode rs, T obj) {
        R<T> response = new R<T>();
        response.setSuccess(false);
        response.setMessage(rs.getMessage());
        response.setCode(rs.getCode());
        response.setData(obj);
        return response;
    }

    /**
     * 处理异常返回
     *
     * @param rs
     * @return
     */
    public static <T> R<T> fail(ResCode rs) {
        R<T> response = new R<T>();
        response.setSuccess(false);
        response.setMessage(rs.getMessage());
        response.setCode(rs.getCode());
        response.setData(null);
        return response;
    }

    /**
     * 处理异常返回
     *
     * @param msg
     * @return
     */
    public static <T> R<T> fail(String msg) {
        R<T> response = new R<T>();
        response.setSuccess(false);
        response.setMessage(msg);
        response.setCode(9999);
        response.setData(null);
        return response;
    }

    /**
     * 处理异常返回
     *
     * @param msg
     * @return
     */
    public static <T> R<T> fail(String msg,T obj) {
        R<T> response = new R<T>();
        response.setSuccess(false);
        response.setMessage(msg);
        response.setCode(9999);
        response.setData(obj);
        return response;
    }

    /**
     * 处理认证异常返回
     *
     * @param msg
     * @return
     */
    public static <T> R<T> unAuthentication(String msg,T obj) {
        R<T> response = new R<T>();
        response.setSuccess(false);
        response.setMessage(msg);
        response.setCode(3000);
        response.setData(obj);
        return response;
    }

    /**
     * 处理授权异常返回
     *
     * @param msg
     * @return
     */
    public static <T> R<T> unAuthorization(String msg,T obj) {
        R<T> response = new R<T>();
        response.setSuccess(false);
        response.setMessage(msg);
        response.setCode(3000);
        response.setData(obj);
        return response;
    }


    /**
     * 处理授权异常返回
     *
     * @param msg
     * @return
     */
    public static <T> R<T> unOAuth2(String msg,T obj) {
        R<T> response = new R<T>();
        response.setSuccess(false);
        response.setMessage(msg);
        response.setCode(3000);
        response.setData(obj);
        return response;
    }

    /**
     * 处理警告返回
     *
     * @param rs
     * @return
     */
    public static <T> R<T> warn(ResCode rs) {
        R<T> response = new R<T>();
        response.setSuccess(false);
        response.setCode(rs.getCode());
        response.setMessage(rs.getMessage());
        response.setData(null);
        return response;
    }
}
