package com.server.cx.dao.cx;

import com.server.cx.entity.cx.StatusType;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserStatusMGraphic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-9
 * Time: 下午4:53
 * FileName:UserStatusMGraphic
 */
public interface UserStatusMGraphicDao extends JpaRepository<UserStatusMGraphic, String>,
        JpaSpecificationExecutor<UserStatusMGraphic> {

    public List<UserStatusMGraphic> findByUserInfoAndStatusType(UserInfo userInfo,StatusType statusType);

    public List<UserStatusMGraphic> findByUserInfoAndModeType(UserInfo userInfo, int modeType);

    public List<UserStatusMGraphic> findByUserInfoAndValidDateAndModeTyp(UserInfo userInfo,Date today, int modeType);

}
