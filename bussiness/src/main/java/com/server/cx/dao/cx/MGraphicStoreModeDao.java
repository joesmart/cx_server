package com.server.cx.dao.cx;

import com.server.cx.dao.cx.custom.MGraphicStoreModeCustomDao;
import com.server.cx.entity.cx.MGraphicStoreMode;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-24
 * Time: 下午3:57
 * FileName:MGraphicStoreModeDao
 */
public interface MGraphicStoreModeDao extends PagingAndSortingRepository<MGraphicStoreMode, String>, MGraphicStoreModeCustomDao {
}
