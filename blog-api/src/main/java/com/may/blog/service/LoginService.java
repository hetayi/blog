package com.may.blog.service;

import com.may.blog.dao.pojo.SysUser;
import com.may.blog.vo.Result;
import com.may.blog.vo.params.LoginParam;


public interface LoginService {
    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
