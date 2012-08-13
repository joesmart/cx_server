
package com.server.cx.dao.cx;

import com.server.cx.entity.cx.HolidayType;
import com.server.cx.entity.cx.UserHolidayMGraphic;
import com.server.cx.entity.cx.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserHolidayMGraphicDao extends JpaRepository<UserHolidayMGraphic, String>,
    JpaSpecificationExecutor<UserHolidayMGraphic> {

    public List<UserHolidayMGraphic> findByUserInfoAndHolidayType(UserInfo userInfo, HolidayType holidayType);
    public List<UserHolidayMGraphic> findByUserInfoAndModeTypeAndCommon(UserInfo userInfo,Integer modeType,Boolean common);
    public List<UserHolidayMGraphic> findByUserInfo(UserInfo userInfo);
}

