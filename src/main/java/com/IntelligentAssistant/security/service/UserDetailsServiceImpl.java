package com.IntelligentAssistant.security.service;

import com.IntelligentAssistant.domain.entity.IaUser;
import com.IntelligentAssistant.mapper.IaUserMapper;
import com.IntelligentAssistant.security.domain.LoginUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author thpaperman
 * @Description Spring Security 用户详情服务类
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IaUserMapper userMapper;

    /**
     * 根据用户名查询用户详情
     *
     * @param username 用户名
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException 用户不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        LambdaQueryWrapper<IaUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IaUser::getUserName, username);
        IaUser iaUser = userMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(iaUser)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new LoginUser(iaUser);
    }
}
