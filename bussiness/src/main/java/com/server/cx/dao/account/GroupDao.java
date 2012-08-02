package com.server.cx.dao.account;

import com.server.cx.entity.account.Group;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 权限组对象的Dao interface.
 *
 * @author calvin
 */

public interface GroupDao extends PagingAndSortingRepository<Group, Long>, GroupDaoCustom {
}
