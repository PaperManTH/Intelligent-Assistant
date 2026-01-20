package com.IntelligentAssistant.domain;

import com.IntelligentAssistant.domain.entity.IaUser;

/**
 * @Author thpaperman
 * @Description 用户上下文
 * @Date 2026/1/19
 * @DAY_NAME_FULL: 星期一
 * @Version 1.0
 */

public class UserContext {

    private static final ThreadLocal<IaUser> currentUser = new ThreadLocal<>();

    /**
     * 设置当前用户
     *
     * @param user 当前用户
     */
    public static void setCurrentUser(IaUser user) {
        currentUser.set(user);
    }

    /**
     * 获取当前用户
     * @return 当前用户
     */
    public static IaUser getCurrentUser() {
        return currentUser.get();
    }

    public static String getUserId() { return currentUser.get().getUserId(); }
    /**
     * 清除当前用户
     */
    public static void clear() {
        currentUser.remove();
    }
}