package com.server.cx.dao.cx;

import com.server.cx.dao.cx.custom.UserFavoritesCustomDao;
import com.server.cx.entity.cx.UserFavorites;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:24
 * FileName:UserFavoritesDao
 */
public interface UserFavoritesDao extends PagingAndSortingRepository<UserFavorites, Long>, UserFavoritesCustomDao {
}
