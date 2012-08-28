package com.server.cx.service.cx;

import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.NotSubscribeTypeException;

public interface UserSubscribeGraphicItemService {
    public void subscribeGraphicItem(String imsi, String graphicInfoId);

    public void subscribeGraphicItem(UserInfo userInfo, GraphicInfo graphicInfo);

    public void checkUserSubscribeGraphicItem(UserInfo userInfo, String graphicInfoId) throws NotSubscribeTypeException;

    public void deleteSubscribeItem(String imsi, String graphicInfoId);
}
