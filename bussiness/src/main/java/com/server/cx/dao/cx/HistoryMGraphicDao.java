package com.server.cx.dao.cx;

import com.server.cx.entity.cx.HistoryMGraphic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午4:29
 * FileName:HistoryMGraphicDao
 */
public interface HistoryMGraphicDao extends JpaRepository<HistoryMGraphic, String>,JpaSpecificationExecutor<HistoryMGraphic> {
}
