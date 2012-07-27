package com.server.cx.util.business;

import java.util.Map;

import com.google.common.collect.Maps;

public enum StatusType {
    // 1:做饭,2:坐飞机,3:忙碌,4:睡觉,5:开会,6:开车,7:工作
    COOKING("做饭", 1), FLYING("坐飞机", 2), BUSY("忙碌", 3), SLEEPING("睡觉", 4), MEETING("开会", 5), DRIVEING("开车", 6), WORKING(
            "工作", 7), INCARS("坐车中", 8), CANTANSWER("无法接听", 9), EMPTY("空闲状态", 10);
    private static Map<Integer, StatusType> statusMap = Maps.newHashMap();

    static {
        StatusType[] statusTypes = values();
        for (StatusType status : statusTypes) {
            statusMap.put(status.getType(), status);
        }
    }

    private String name;
    private Integer type;

    StatusType(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static StatusType getStatusTypeByTypeNumber(Integer key) {
        StatusType status = statusMap.get(key);
        return status;
    }

    public static boolean isValidStatusType(Integer type) {
        boolean result = false;
        result = statusMap.containsKey(type);
        return result;
    }

}
