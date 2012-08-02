package com.server.cx.dao.cx;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.server.cx.dao.cx.custom.ContactsCustomDao;
import com.server.cx.entity.cx.Contacts;

/**
 * User: yanjianzou
 * Date: 12-7-25
 * Time: 下午4:21
 * FileName:ContactsDao
 */
public interface ContactsDao extends PagingAndSortingRepository<Contacts, Long>, ContactsCustomDao {
}
