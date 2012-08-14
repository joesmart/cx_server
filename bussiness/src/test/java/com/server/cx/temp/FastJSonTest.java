package com.server.cx.temp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cl.cx.platform.dto.ContactInfoDTO;
import com.cl.cx.platform.dto.ContactsDTO;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-14
 * Time: 上午10:40
 * FileName:FastJSonTest
 */
public class FastJSonTest {

    @Test
    public void testWriteClassName() {
        ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
        contactInfoDTO.setContactName("abc");
        contactInfoDTO.setPhoneNo("123123123");

        ContactsDTO contactsDTO = new ContactsDTO();
        List<ContactInfoDTO> list = Lists.newArrayList();
        list.add(contactInfoDTO);

        contactsDTO.setContactInfos(list);

        String jsonText = JSON.toJSONString(contactsDTO, SerializerFeature.PrettyFormat,SerializerFeature.WriteClassName);
        System.out.println(jsonText);
    }

}
