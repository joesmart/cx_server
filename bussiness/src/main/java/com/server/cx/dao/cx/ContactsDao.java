package com.server.cx.dao.cx;

import com.server.cx.dao.cx.custom.ContactsCustomDao;
import com.server.cx.entity.cx.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-25
 * Time: 下午4:21
 * FileName:ContactsDao
 */
public interface ContactsDao extends PagingAndSortingRepository<Category, Long>, ContactsCustomDao {
}
