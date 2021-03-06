package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.VersionInfoDTO;
import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.VersionInfoDao;
import com.server.cx.entity.cx.VersionInfo;
import com.server.cx.service.cx.VersionInfoService;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.business.ValidationUtil;
import com.server.cx.util.business.VersionInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("versionInfoService")
@Transactional
public class VersionInfoServiceImpl implements VersionInfoService {

    @Autowired
    private VersionInfoDao versionInfoDao;

    @Override
    public VersionInfoDTO checkIsTheLatestVersion(String clientVersion) {
        if (!ValidationUtil.isVersionString(clientVersion)) {
            return ObjectFactory.buildVersionInfoDTO(Constants.ERROR_FLAG, "版本字符串格式不对");
        }

        List<VersionInfo> versionList = Lists.newArrayList(versionInfoDao.findAll());
        if (versionList != null && versionList.size() > 0) {
            VersionInfo serverVersionInfo = versionList.get(0);
            VersionInfoUtil versionUtil = new VersionInfoUtil(clientVersion, serverVersionInfo.getVersion());
            boolean isNeedUpgrade = versionUtil.isNeedToUpgrade();
            boolean isForceUpdate = versionUtil.isNeedToForceUpgrade();

            if (isNeedUpgrade) {
                return ObjectFactory.buildVersionInfoDTO(Constants.SERVER_HAVE_NEW_VERSION, "服务器端软件版本最新",
                    String.valueOf(isForceUpdate), serverVersionInfo.getUrl());
            } else {
                return ObjectFactory.buildVersionInfoDTO(Constants.APP_IS_NEWEST, "客户端APP版本为最新版本");
            }
        } else {
            return ObjectFactory.buildVersionInfoDTO(Constants.ERROR_FLAG, "服务无最新APP的版本数据");
        }

    }
}
