package com.may.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.may.blog.dao.mapper.SysUsrMapper;
import com.may.blog.dao.pojo.SysUser;
import com.may.blog.service.LoginService;
import com.may.blog.service.SysUserService;
import com.may.blog.vo.ErrorCode;
import com.may.blog.vo.LoginUserVo;
import com.may.blog.vo.Result;
import com.may.blog.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUsrMapper sysUsrMapper;

    @Autowired
    private LoginService loginService;

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUsrMapper.selectById(id);
        if (sysUser == null)
        {
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("艺哥");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        userVo.setId(String.valueOf(sysUser.getId()));
        return userVo;
    }

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUsrMapper.selectById(id);
        if (sysUser == null)
        {
            sysUser = new SysUser();
            sysUser.setNickname("艺哥");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
         LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(SysUser::getAccount,account);
         queryWrapper.eq(SysUser::getPassword,password);
         queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
         queryWrapper.last("limit 1");

         return sysUsrMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {

        /**
         * 1. token合法性校验
         *      是否为空，解析是否成功 redis是否存在
         * 2.如果校验失败，返回错误
         * 3.如果成功，返回对应的结果 LoginVo
         */
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null)
        {
            return Result.fail(ErrorCode.TOKEN_ILLEGAL.getCode(), ErrorCode.TOKEN_ILLEGAL.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        return sysUsrMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        //保存用户这 id会自动生成
        //这个地方 默认生成的id是 分布式id 雪花算法
        sysUsrMapper.insert(sysUser);

    }
}
