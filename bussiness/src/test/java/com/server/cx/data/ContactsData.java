package com.server.cx.data;

import java.util.List;
import com.cl.cx.platform.dto.ContactInfoDTO;
import com.google.common.collect.Lists;
import com.server.cx.entity.cx.Contacts;
import com.server.cx.entity.cx.UserInfo;

public class ContactsData {
    public static List<Contacts> buildContacts() {
        List<Contacts> contacts = Lists.newArrayList();
        Contacts c1 = new Contacts();
        c1.setName("Wade");
        c1.setPhoneNo("15158118320");
        UserInfo info = new UserInfo();
        info.setImsi("156557612612121");
        info.setPhoneNo("18765432101");

        UserInfo selfInfo = new UserInfo();
        selfInfo.setImsi("156557612612122");
        selfInfo.setPhoneNo("18765432102");

        c1.setUserInfo(info);
        c1.setSelfUserInfo(selfInfo);
        contacts.add(c1);

        Contacts c2 = new Contacts();
        c2.setName("Bosh");
        c2.setPhoneNo("15158118321");
        info = new UserInfo();
        info.setImsi("156557612612123");
        info.setPhoneNo("18765432103");

        selfInfo = new UserInfo();
        selfInfo.setImsi("156557612612124");
        selfInfo.setPhoneNo("18765432104");

        c2.setUserInfo(info);
        c2.setSelfUserInfo(selfInfo);
        contacts.add(c2);

        return contacts;

    }

    public static ContactInfoDTO buildContactPeopleInfo() {
        ContactInfoDTO info = new ContactInfoDTO();
        info.setContactName("Steve Nash");
//        info.setPhoneNumList("1512581472,1512581481");
        info.setPhoneNo("1512581472");
        return info;
    }

    public static ContactInfoDTO buildContactPeopleInfo2() {
        ContactInfoDTO info = new ContactInfoDTO();
        info.setContactName("King Gavin");
//        info.setPhoneNumList("1512581122,1512581111");
        info.setPhoneNo("1512581122");
        return info;
    }
    
    public static ContactInfoDTO buildContactPeopleInfo3() {
        ContactInfoDTO info = new ContactInfoDTO();
        info.setContactName("Steve Nash");
        info.setPhoneNo("1512581481");
        return info;
    }
    
    public static ContactInfoDTO buildContactPeopleInfo4() {
        ContactInfoDTO info = new ContactInfoDTO();
        info.setContactName("King Gavin");
        info.setPhoneNo("1512581111");
        return info;
    }

    public static List<ContactInfoDTO> buildContactPeopleInfos() {
        List<ContactInfoDTO> contactPeopleInfos = Lists.newArrayList();
        ContactInfoDTO info1 = buildContactPeopleInfo();
        ContactInfoDTO info2 = buildContactPeopleInfo2();
        ContactInfoDTO info3 = buildContactPeopleInfo3();
        ContactInfoDTO info4 = buildContactPeopleInfo4();
        contactPeopleInfos.add(info1);
        contactPeopleInfos.add(info2);
        contactPeopleInfos.add(info3);
        contactPeopleInfos.add(info4);
        return contactPeopleInfos;
    }
}
