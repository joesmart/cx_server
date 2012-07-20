package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserFavorites;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:24
 * FileName:GenericUserFavoritesDao
 */
public interface GenericUserFavoritesDao extends PagingAndSortingRepository<UserFavorites,Long> {
}
