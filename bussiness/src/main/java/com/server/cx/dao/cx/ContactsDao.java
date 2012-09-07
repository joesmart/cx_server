package com.server.cx.dao.cx;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.server.cx.dao.cx.custom.ContactsCustomDao;
import com.server.cx.entity.cx.Contacts;
import com.server.cx.entity.cx.UserInfo;

/**
 * User: yanjianzou
 * Date: 12-7-25
 * Time: 下午4:21
 * FileName:ContactsDao
 */
public interface ContactsDao extends PagingAndSortingRepository<Contacts, Long>, ContactsCustomDao {

    List<Contacts> findBySelfUserInfo(UserInfo UserInfo);
}
