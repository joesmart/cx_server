package com.server.cx.dao.cx;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeGraphicItem;

public interface UserSubscribeGraphicItemDao extends JpaRepository<UserSubscribeGraphicItem, Long>,
    JpaSpecificationExecutor<UserSubscribeGraphicItem> {

    public List<UserSubscribeGraphicItem> findByUserInfoAndGraphicInfo(UserInfo userInfo, GraphicInfo graphicInfo);

}
