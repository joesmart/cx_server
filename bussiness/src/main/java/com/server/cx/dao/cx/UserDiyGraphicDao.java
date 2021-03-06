package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserDiyGraphic;
import com.server.cx.entity.cx.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * User: yanjianzou
 * Date: 12-8-22
 * Time: 下午4:54
 * FileName:UserDiyGraphicDao
 */
public interface UserDiyGraphicDao extends JpaRepository<UserDiyGraphic, String>,JpaSpecificationExecutor<UserDiyGraphic> {
    public UserDiyGraphic findByUserInfo(UserInfo userInfo);
    public UserDiyGraphic findByUserInfoOrderByAuditStatusDescCreatedOnDesc(UserInfo userInfo);
}
