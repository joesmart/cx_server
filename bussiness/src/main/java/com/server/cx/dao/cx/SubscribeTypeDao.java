package com.server.cx.dao.cx;

import org.springframework.data.jpa.repository.JpaRepository;
import com.server.cx.dao.cx.custom.SubscribeTypeDaoCustom;
import com.server.cx.entity.cx.SubscribeType;


public interface SubscribeTypeDao extends JpaRepository<SubscribeType, Long>, SubscribeTypeDaoCustom {

}
