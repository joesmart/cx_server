package com.server.cx.dao.cx;

import com.server.cx.dao.cx.custom.GraphicInfoCustomDao;
import com.server.cx.entity.cx.GraphicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 上午9:38
 * FileName:GraphicInfoDao
 */
public interface GraphicInfoDao extends JpaRepository<GraphicInfo, String>,JpaSpecificationExecutor<GraphicInfo>,GraphicInfoCustomDao {
}
