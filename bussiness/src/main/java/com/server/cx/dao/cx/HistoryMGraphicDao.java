package com.server.cx.dao.cx;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.HistoryMGraphic;
import com.server.cx.entity.cx.UserInfo;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午4:29
 * FileName:HistoryMGraphicDao
 */
public interface HistoryMGraphicDao extends JpaRepository<HistoryMGraphic, String>,JpaSpecificationExecutor<HistoryMGraphic> {

    public List<HistoryMGraphic> findByGraphicInfoAndUserInfo(GraphicInfo graphicInfo, UserInfo userInfo);
}
