package com.server.cx.dao.cx;

import com.server.cx.dao.cx.custom.UserFavoritesCustomDao;
import com.server.cx.entity.cx.UserFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:24
 * FileName:UserFavoritesDao
 */
public interface UserFavoritesDao extends JpaRepository<UserFavorites, String>,JpaSpecificationExecutor<UserFavorites>,UserFavoritesCustomDao {

}
