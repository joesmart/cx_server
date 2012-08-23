package com.server.cx.dao.cx;

import org.springframework.data.jpa.repository.JpaRepository;
import com.server.cx.dao.cx.custom.UserSubscribeTypeCustom;
import com.server.cx.entity.cx.UserSubscribeType;

public interface UserSubscribeTypeDao extends JpaRepository<UserSubscribeType, Long>, UserSubscribeTypeCustom {

}
