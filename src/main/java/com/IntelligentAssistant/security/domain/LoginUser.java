package com.paperman.security.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.paperman.domain.pojo.BboxUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;

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

    /** 团队 ID **/
    private Long teamId;

    /** 用户 token **/
    private String token;

    /** 登录时间 **/
    private Long loginTime;

    /** 过期时间 **/
    private Long expireTime;

    /** 登录 IP **/
    private String ipaddr;

    /** 登录地点 **/
    private String loginLocation;

    /** 浏览器 **/
    private String browser;

    /** 操作系统 **/
    private String os;

    /** 权限 **/
    private Set<String> permissions;

    /** 用户 **/
    private BboxUser user;

    public LoginUser(BboxUser user, Set<String> permissions)
    {
        this.user = user;
        this.permissions = permissions;
    }

    public LoginUser(Long userId, Long teamId, BboxUser user, Set<String> permissions)
    {
        this.userId = userId;
        this.teamId = teamId;
        this.user = user;
        this.permissions = permissions;
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
