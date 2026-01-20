package com.IntelligentAssistant.security.domain;

import com.IntelligentAssistant.domain.entity.IaUser;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;

/**
 * @Author thpaperman
 * @Description 登录用户实现 security 登录用户
 * @Date 2026/1/5
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 用户 ID **/
    private Long userId;

    /** 用户 token **/
    private String token;

    /** 登录时间 **/
    private Long loginTime;

    /** 过期时间 **/
    private Long expireTime;

    /** 用户 **/
    private IaUser user;

    public LoginUser(IaUser user)
    {
        this.user = user;
    }

    public LoginUser(Long userId, IaUser user)
    {
        this.userId = userId;
        this.user = user;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword()
    {
        return user.getUserPassword();
    }

    @Override
    public String getUsername()
    {
        return user.getUserName();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     */
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     */
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled()
    {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }
}
