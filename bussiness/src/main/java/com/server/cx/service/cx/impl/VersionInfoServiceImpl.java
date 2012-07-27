package com.server.cx.service.cx.impl;

import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.VersionInfoDao;
import com.server.cx.dao.cx.custom.UserInfoCustomDao;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.VersionInfo;
import com.server.cx.service.cx.VersionInfoService;
import com.server.cx.util.StringUtil;
import com.server.cx.util.business.ValidationUtil;
import com.server.cx.util.business.VersionInfoUtil;
import com.server.cx.xml.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("versionInfoService")
@Transactional
public class VersionInfoServiceImpl implements VersionInfoService {

    @Autowired
    private VersionInfoDao versionInfoDao;

    @Autowired
    private UserInfoCustomDao userInfoCustomDao;

    @Override
    public String checkIsTheLatestVersion(String imsi, String clientVersion) {
        UserInfo userInfo = userInfoCustomDao.getUserInfoByImsi(imsi);

        if (userInfo == null) {
            return StringUtil.generateXMLResultString(Constants.USER_DATA_ERROR_FLAG, "用户不存在");
        }

        if (!ValidationUtil.isVerionString(clientVersion)) {
            return StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "版本字符串格式不对");
        }

        List<VersionInfo> versionList = Lists.newArrayList(versionInfoDao.findAll());
        if (versionList != null && versionList.size() > 0) {
            VersionInfo serverVersionInfo = versionList.get(0);
            VersionInfoUtil versionUtil = new VersionInfoUtil(clientVersion, serverVersionInfo.getVersion());
            Result result = new Result();
            boolean isNeedUpgrade = versionUtil.isNeedToUpgrade();
            boolean isForceUpdate = versionUtil.isNeedToForceUpgrade();

            if (isNeedUpgrade) {
                result.setFlag(Constants.SERVER_HAVE_NEWVERION);
                result.setContent("服务器端软件版本最新");
                result.setForceUpdate(String.valueOf(isForceUpdate));
                result.setUrl(serverVersionInfo.getUrl());
            } else {
                result.setFlag(Constants.APP_IS_NEWEST);
                result.setContent("客户端APP版本为最新版本");
            }
            return StringUtil.generateXMLResultFromObject(result);
        } else {
            return StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "服务无最新APP的版本数据");
        }

    }

}
