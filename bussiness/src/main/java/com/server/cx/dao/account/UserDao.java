package com.server.cx.dao.account;

import com.server.cx.entity.account.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 用户对象的Dao interface.
 * 
 * @author calvin
 */
public interface UserDao extends PagingAndSortingRepository<User, Long> {

	User findByLoginName(String loginName);
}
