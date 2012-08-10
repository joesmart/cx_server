package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserHolidayMGraphic;
import com.server.cx.entity.cx.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-10
 * Time: обнГ1:40
 * FileName:UserHolidayMGraphicDao
 */
public interface UserHolidayMGraphicDao extends JpaRepository<UserHolidayMGraphic, String>,JpaSpecificationExecutor<UserHolidayMGraphic> {
    public List<UserHolidayMGraphic> findByUserInfoAndModeTypeAndCommon(UserInfo userInfo,Integer modeType,Boolean common);
}
