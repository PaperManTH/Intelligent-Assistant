package com.paperman.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.paperman.domain.pojo.BboxUser;
import com.paperman.exception.user.UserNotExistsException;
import com.paperman.mapper.BboxUserMapper;
import com.paperman.security.domain.LoginUser;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Author thpaperman
 * @Description TODO
 * @Date 2026/1/5
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BboxUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        LambdaQueryWrapper<BboxUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BboxUser::getUserName, username);
        BboxUser bboxUser = userMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(bboxUser)) {
            throw new UserNotExistsException();
        }
        //TODO 权限控制访问
        return new LoginUser(bboxUser, null);
    }
}
