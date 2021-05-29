package com.simba.common.constant;

/**
 * @author chenjun
 * @date 2021-05-17
 * @time 15:54
 * @Description: sql脚本
 */
public interface SqlsConstant {

    String OPER_LOG = "insert into sys_oper_log (type, title, method, user_agent, oper_name, client_id, oper_url, oper_ip, oper_addr, oper_param, status, error_msg, execute_time, oper_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
}
