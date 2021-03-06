package com.server.cx.dao.cx;

import com.server.cx.dao.cx.custom.UserCommonMGraphicCustomDao;
import com.server.cx.entity.cx.UserCommonMGraphic;
import com.server.cx.entity.cx.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-7-24
 * Time: 下午3:57
 * FileName:UserCommonMGraphicDao
 */
public interface UserCommonMGraphicDao extends JpaRepository<UserCommonMGraphic, String>,
                                                   JpaSpecificationExecutor<UserCommonMGraphic>,
                                                   UserCommonMGraphicCustomDao {

    public List<UserCommonMGraphic> findByUserInfoAndModeTypeAndCommon(UserInfo userInfo,Integer modeType,Boolean common);
}
