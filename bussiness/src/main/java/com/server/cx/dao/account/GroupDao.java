package com.server.cx.dao.account;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.server.cx.entity.account.Group;

/**
 * 权限组对象的Dao interface.
 *
 * @author calvin
 */

public interface GroupDao extends PagingAndSortingRepository<Group, Long>, GroupDaoCustom {
}
