package com.server.cx.dao.cx;

import com.server.cx.dao.cx.custom.MGraphicCustomDao;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserSpecialMGraphic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-7
 * Time: 下午12:43
 * FileName:MGraphicDao
 */
public interface MGraphicDao extends JpaRepository<MGraphic, String>,JpaSpecificationExecutor<MGraphic>,MGraphicCustomDao {
    public List<UserSpecialMGraphic> findByPhoneNosIsNotNull();
}
