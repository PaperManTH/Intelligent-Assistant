package com.IntelligentAssistant.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author thpaperman
 * @Description 用户信息类
 * @DAY_NAME_FULL: 星期五
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Ia_user")
public class IaUser implements Serializable {
    /** 用户 ID **/
    @TableId(type = IdType.ASSIGN_ID)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime userLoginData;

    /** 账号密码最新更新时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime pwdUpdateTime;

    /**
     * 账号创建时间
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /** 账号更新时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /** 默认序列化版本号 **/
    @Serial
    private static final long serialVersionUID = 1L;

}
