package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserCustomMGraphic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * User: yanjianzou
 * Date: 12-8-14
 * Time: 下午1:18
 * FileName:UserCustomMGraphicDao
 */
public interface UserCustomMGraphicDao extends JpaRepository<UserCustomMGraphic, String>,JpaSpecificationExecutor<UserCustomMGraphic> {

}
