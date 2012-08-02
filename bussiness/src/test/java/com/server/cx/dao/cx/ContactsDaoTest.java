package com.server.cx.dao.cx;

import static org.fest.assertions.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.data.ContactsData;
import com.server.cx.data.UserInfoData;
import com.server.cx.entity.cx.Contacts;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
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
}
