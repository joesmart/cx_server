
package com.server.cx.dao.cx;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.server.cx.entity.cx.HolidayType;
import com.server.cx.entity.cx.UserHolidayMGraphic;
import com.server.cx.entity.cx.UserInfo;

public interface UserHolidayMGraphicDao extends JpaRepository<UserHolidayMGraphic, String>,
    JpaSpecificationExecutor<UserHolidayMGraphic> {

    public List<UserHolidayMGraphic> findByUserInfoAndHolidayType(UserInfo userInfo, HolidayType holidayType);
    public List<UserHolidayMGraphic> findByUserInfoAndModeTypeAndCommon(UserInfo userInfo,Integer modeType,Boolean common);
}

