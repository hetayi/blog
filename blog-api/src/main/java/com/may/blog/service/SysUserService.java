package com.may.blog.service;

import com.may.blog.dao.pojo.SysUser;
import com.may.blog.vo.Result;
import com.may.blog.vo.UserVo;

public interface SysUserService {

    UserVo findUserVoById(Long id);

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token 查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据用户查找账户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);
}
