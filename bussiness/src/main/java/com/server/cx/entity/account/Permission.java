package com.server.cx.entity.account;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Resource Base Access Control中的资源定义.
 *
 * @author calvin
 */
public enum Permission {

    USER_VIEW("user:view", "查看用戶"), USER_EDIT("user:edit", "修改用户"),

    GROUP_VIEW("group:view", "查看权限组"), GROUP_EDIT("group:edit", "修改权限组"),

    Table_VIEW("table:view", "查看台桌"), Table_EDIT("table:edit", "修改台桌"),

    MENU_VIEW("menu:view", "查看菜品"), MENU_EDIT("menu:edit", "修改菜品"),

    SALES_VIEW("sales:view", "查看销售统计"),

    MENUCATEGORY_VIEW("menucategory:view", "查看菜品种类"), MENUCATEGORY_EDIT("menucategory:edit", "修改菜品种类");

    private static Map<String, Permission> valueMap = Maps.newHashMap();

    public String value;
    public String displayName;

    static {
        for (Permission permission : Permission.values()) {
            valueMap.put(permission.value, permission);
        }
    }

    Permission(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public static Permission parse(String value) {
        return valueMap.get(value);
    }

    public String getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }
}
