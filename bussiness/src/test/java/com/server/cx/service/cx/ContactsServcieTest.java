package com.server.cx.service.cx;

import java.util.List;

import com.cl.cx.platform.dto.ContactPeopleInfoDTO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.constants.Constants;
import com.server.cx.data.ContactsData;
import com.cl.cx.platform.dto.UploadContactDTO;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class ContactsServcieTest extends SpringTransactionalTestCase {
    public static final Logger LOGGER = LoggerFactory.getLogger(ContactsServcieTest.class);
    @Autowired
    private ContactsServcie contactsServcie;

    @Test
    public void should_upload_contacts() {
        String imsi = "13146001010";
        List<ContactPeopleInfoDTO> contactPeopleInfos = ContactsData.buildContactPeopleInfos();
        UploadContactDTO uploadContactDTO = contactsServcie.uploadContacts(contactPeopleInfos, imsi);
        assertThat(uploadContactDTO.getFlag()).isEqualTo(Constants.SUCCESS_FLAG);
        
    }

    @Test
    public void should_no_exist_imsi_exception() {
        String imsi = "131460010987";
        List<ContactPeopleInfoDTO> contactPeopleInfos = ContactsData.buildContactPeopleInfos();
        try {
            contactsServcie.uploadContacts(contactPeopleInfos, imsi);
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("用户未注册");
        }

    }
}
