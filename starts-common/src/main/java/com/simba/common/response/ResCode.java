package com.simba.common.response;

/**
 * @author simba.chen
 * @Description 枚举这个就类似静态类, 目的是指定返回的规范
 * 规定:
 * #200表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 * #4001～4999 区间表示业务错误
 * #5001～5999 区间表示部门错误
 * #9001～9999 区间表示运行时异常
 * #后面对什么的操作自己在这里注明就行了
 */
public enum ResCode implements CustomizeResCode {
    /* 成功 */
    COMMON_SUCCESS(1111, "操作成功"),

    /* 默认失败 */
    COMMON_FAIL(9999, "未知错误"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "您的登录已经超时或者已经在另一台机器登录，您被迫下线"),
    USER_ACCOUNT_OR_PASSWORD_ERROR(2010, "账号或者密码不正确"),

    /* 授权错误 */
    NO_PERMISSION(3001, "没有访问权限"),
    OAUTH2EXCEPTION_URI(3002, "error_uri"),
    OAUTH2EXCEPTION_INVALID_REQUEST(3003,"invalid_request"),
    OAUTH2EXCEPTION_INVALID_CLIENT(3004,"invalid_client"),
    OAUTH2EXCEPTION_INVALID_GRANT(3005,"invalid_grant"),
    OAUTH2EXCEPTION_UNAUTHORIZED_CLIENT(3006,"unauthorized_client"),
    OAUTH2EXCEPTION_UNSUPPORTED_GRANT_TYPE(3007,"unsupported_grant_type"),
    OAUTH2EXCEPTION_INVALID_SCOPE(3008,"invalid_scope"),
    OAUTH2EXCEPTION_INSUFFICIENT_SCOPE(3009,"insufficient_scope"),
    OAUTH2EXCEPTION_INVALID_TOKEN(3010,"invalid_token"),
    OAUTH2EXCEPTION_REDIRECT_URI_MISMATCH(3011,"redirect_uri_mismatch"),
    OAUTH2EXCEPTION_UNSUPPORTED_RESPONSE_TYPE(3012,"unsupported_response_type"),
    OAUTH2EXCEPTION_ACCESS_DENIED(3013,"access_denied"),

    /*部门错误*/
    DEPARTMENT_NOT_EXIST(4001, "部门不存在"),
    DEPARTMENT_ALREADY_EXIST(4002, "部门已存在"),

    /*运行时异常*/
    ARITHMETIC_EXCEPTION(9001, "算数异常"),
    NULL_POINTER_EXCEPTION(9002, "空指针异常"),
    ARRAY_INDEX_OUTOfBOUNDS_EXCEPTION(9003, "数组越界");

    private Integer code;

    private String message;

    ResCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}