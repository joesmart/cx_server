package com.server.cx.service.cx;

import static org.fest.assertions.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.cl.cx.platform.dto.ContactInfoDTO;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.ContactsDao;
import com.server.cx.data.ContactsData;
import com.server.cx.entity.cx.Contacts;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class ContactsServcieTest extends SpringTransactionalTestCase {
    public static final Logger LOGGER = LoggerFactory.getLogger(ContactsServcieTest.class);
    @Autowired
    private ContactsServcie contactsServcie;

    @Autowired
    private ContactsDao contactsDao;

    @Test
    public void should_upload_contacts() {
        String imsi = "13146001010";
        List<ContactInfoDTO> contactPeopleInfos = ContactsData.buildContactPeopleInfos();
        contactsServcie.uploadContacts(contactPeopleInfos, imsi);
        List<Contacts> contacts = (List<Contacts>) contactsDao.findAll();
        List<String> allPhones = Lists.newArrayList();
        for (ContactInfoDTO contactPeopleInfoDTO : contactPeopleInfos) {
            String phoneNo = contactPeopleInfoDTO.getPhoneNo();
            allPhones.add(phoneNo);
        }

        List<String> dbPhones = Lists.newArrayList();
        for (Contacts c : contacts) {
            dbPhones.add(c.getPhoneNo());
        }

        for (String phoneNo : allPhones) {
            assertThat(dbPhones.contains(phoneNo)).isEqualTo(true);
        }

    }

    @Test
    public void should_no_exist_imsi_exception() {
        String imsi = "131460010987";
        List<ContactInfoDTO> contactPeopleInfos = ContactsData.buildContactPeopleInfos();
        try {
            contactsServcie.uploadContacts(contactPeopleInfos, imsi);
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("用户未注册");
        }

    }
}
