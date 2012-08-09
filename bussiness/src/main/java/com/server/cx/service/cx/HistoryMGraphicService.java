package com.server.cx.service.cx;

import com.cl.cx.platform.dto.DataItem;
import com.server.cx.model.OperationResult;
import com.server.cx.entity.cx.UserInfo;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-7
 * Time: 下午2:51
 * FileName:HistoryMGraphicService
 */
public interface HistoryMGraphicService {
    public abstract List<DataItem> queryUserHistoryMGraphic(String imsi);
    public abstract List<DataItem> queryHistoryMGraphicsByUserInfo(UserInfo userInfo);
    public abstract OperationResult deleteHistoryMGraphic(String imsi,String historyMGraphicId);
}
