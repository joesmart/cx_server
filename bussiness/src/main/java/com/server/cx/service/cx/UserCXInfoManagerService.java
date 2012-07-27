package com.server.cx.service.cx;

import com.server.cx.dto.Result;
import com.server.cx.dto.UserCXInfo;
import com.server.cx.exception.SystemException;

import java.util.List;
import java.util.Map;

public interface UserCXInfoManagerService {

    /**
     * 处理客户端的用户彩像数据的添加
     *
     * @param result <p>
     *               客户端传递上来的数据.
     *               </p>
     * @return
     * @throws com.server.cx.exception.SystemException
     *
     */
    public String dealWithUserCXInfoAdding(Result result) throws SystemException;

    /**
     * 删除彩像数据
     * <p/>
     * If necessary, describe how it does and how to use it.
     * </P>
     *
     * @param id   要删除的彩像ID
     * @param imsi 用户的imsi号
     * @return
     * @throws com.server.cx.exception.SystemException
     *
     */
    public String deleteMGraphicStoreMode(String id, String imsi) throws SystemException;


    public List<UserCXInfo> retrieveUserCXInfos(Map<String, String> params);
}
