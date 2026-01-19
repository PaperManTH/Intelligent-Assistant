package com.IntelligentAssistant.domain.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author thpaperman
 * @Description 用户信息类
 * @DAY_NAME_FULL: 星期五
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ia_user")
public class IaUser implements Serializable {
    /** 用户 ID **/
    @TableId
    private String userId;

    /** 用户账号 **/
    private String userName;

    /** 用户性别 **/
    private String userSex;

    /** 用户密码 **/
    private String userPassword;

    /** 账号状态 **/
    private String userStatus;

    /** 用户最后登录时间 **/
    private String userLoginData;

    /** 账号密码最新更新时间 **/
    private String pwdUpdTime;

    /** 默认序列化版本号 **/
    @Serial
    private static final long serialVersionUID = 1L;

}
