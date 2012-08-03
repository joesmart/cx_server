package com.server.cx.dao.cx;

import com.server.cx.data.ContactsData;
import com.server.cx.data.UserInfoData;
import com.server.cx.entity.cx.Contacts;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class ContactsDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private ContactsDao contactsDao;
    
    @Test
    public void test_retrive_exists_mobiles() {
        String userId = "1";
        List<String> phoneNos = UserInfoData.buildPhoneNos();
        List<String> mobiles = contactsDao.retrieveExistsMobiles(userId, phoneNos);
        assertThat(mobiles.size()).isEqualTo(1);
        assertThat(mobiles.get(0)).isEqualTo("1512581471");
    }
    
    @Test
    public void test_batch_insert_contacts() {
        List<Contacts>  beforeInsertContacts = (List<Contacts>) contactsDao.findAll(new Sort(Direction.ASC, "id"));
        int beforeSize = beforeInsertContacts.size();
        List<Contacts> contacts = ContactsData.buildContacts();
        contactsDao.batchInsertContacts(contacts);
        int afterSize = ((List<Contacts>)contactsDao.findAll()).size();
        assertThat(afterSize - beforeSize).isEqualTo(2);
    }
    
    @Test
    public void test_getContactsByUserIdAndSelfUserInfoNotNull() {
        List<Contacts> contacts = contactsDao.getContactsByUserIdAndSelfUserInfoNotNull("1");
        assertThat(contacts.size()).isEqualTo(3);
        assertThat(contacts.get(0).getName()).isEqualTo("Lebron James");
    }
}
