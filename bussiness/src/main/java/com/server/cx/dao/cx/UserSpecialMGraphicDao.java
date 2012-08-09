package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSpecialMGraphic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-7-24
 * Time: 下午3:57
 * FileName:UserCommonMGraphicDao
 */
public interface UserSpecialMGraphicDao extends JpaRepository<UserSpecialMGraphic, String>,
        JpaSpecificationExecutor<UserSpecialMGraphic> {

    public UserSpecialMGraphic findByUserInfoAndActive(UserInfo userInfo, Boolean active);
    public List<UserSpecialMGraphic> findByPhoneNosIsNotNull();
}
