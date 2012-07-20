package com.server.cx.dao.cx;

import com.server.cx.entity.cx.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:00
 * FileName:GenericCategoryDao
 */
public interface GenericCategoryDao extends PagingAndSortingRepository<Category, Long> {
}
