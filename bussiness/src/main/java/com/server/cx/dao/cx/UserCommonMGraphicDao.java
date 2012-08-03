package com.server.cx.dao.cx;

import com.server.cx.dao.cx.custom.UserCommonMGraphicCustomDao;
import com.server.cx.entity.cx.UserCommonMGraphic;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-24
 * Time: 下午3:57
 * FileName:UserCommonMGraphicDao
 */
public interface UserCommonMGraphicDao extends PagingAndSortingRepository<UserCommonMGraphic, String>, UserCommonMGraphicCustomDao {
}
