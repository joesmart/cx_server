package com.server.cx.util.business;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

public class VersionInfoUtil {

    private boolean isNeedToForceUpgrade = false;

    private boolean isNeedToUpgrade = false;

    public boolean isNeedToForceUpgrade() {
        return isNeedToForceUpgrade;
    }

    public void setNeedToForceUpgrade(boolean isNeedToForceUpgrade) {
        this.isNeedToForceUpgrade = isNeedToForceUpgrade;
    }

    public boolean isNeedToUpgrade() {
        return isNeedToUpgrade;
    }

    public void setNeedToUpgrade(boolean isNeedToUpgrade) {
        this.isNeedToUpgrade = isNeedToUpgrade;
    }


    public VersionInfoUtil() {

    }

    public VersionInfoUtil(String clientVersion, String serverVersion) {
        this.versionInfoCompare(clientVersion, serverVersion);
    }

    public void versionInfoCompare(String clientVersion, String serverVersion) {

        if (serverVersion.equals(clientVersion)) {
            this.isNeedToForceUpgrade = false;
            this.isNeedToUpgrade = false;
            return;
        }

        List<String> clientSplitedVersionStringList = changeStringToList(clientVersion);
        List<String> serverSplitedVersionList = changeStringToList(serverVersion);
        if (serverSplitedVersionList.size() != clientSplitedVersionStringList.size()) {
            this.isNeedToUpgrade = true;
            this.isNeedToForceUpgrade = true;
        } else {
            int i = 0;
            for (; i < serverSplitedVersionList.size(); i++) {
                int serverVersionNo = Integer.parseInt(serverSplitedVersionList.get(i));
                int clientVersioNo = Integer.parseInt(clientSplitedVersionStringList.get(i));
                if (i == 0) {
                    if (serverVersionNo > clientVersioNo) {
                        this.isNeedToForceUpgrade = true;
                        this.isNeedToUpgrade = true;
                        break;
                    } else if (serverVersionNo < clientVersioNo) {
                        this.isNeedToForceUpgrade = false;
                        this.isNeedToUpgrade = false;
                        break;
                    }
                } else if (serverVersionNo > clientVersioNo) {
                    this.isNeedToForceUpgrade = false;
                    this.isNeedToUpgrade = true;
                    break;
                }
            }

        }
    }

    private List<String> changeStringToList(String versionString) {
        return Lists.newArrayList(Splitter.on(".").split(versionString));
    }
}
