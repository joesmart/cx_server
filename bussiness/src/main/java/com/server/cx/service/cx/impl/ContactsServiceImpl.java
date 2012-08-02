package com.server.cx.service.cx.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.ContactsDao;
import com.server.cx.dao.cx.MGraphicStoreModeDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dto.CXInfo;
import com.server.cx.dto.ContactPeopleInfo;
import com.server.cx.dto.Result;
import com.cl.cx.platform.dto.UploadContactDTO;
import com.server.cx.dto.UserCXInfo;
import com.server.cx.entity.cx.Contacts;
import com.server.cx.entity.cx.MGraphicStoreMode;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.ContactsServcie;
import com.server.cx.service.cx.UserCXInfoManagerService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.RestSender;
import com.server.cx.util.StringUtil;
import com.server.cx.util.business.ValidationUtil;

@Service("contactsServcie")
@Transactional
public class ContactsServiceImpl implements ContactsServcie {
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private MGraphicStoreModeDao mgraphicStoreModeDao;
    @Autowired
    private UserCXInfoManagerService userCXInfoManagerService;
    @Autowired
    @Qualifier("cxinfosQueryIdRestSender")
    private RestSender restSender;

    @Autowired
    private BusinessFunctions businessFunctions;

    private UserInfo userInfo;
    private String dealResult = "";
    private List<Contacts> contactsList;
    private List<String> mobiles;

    @Override
    @Transactional(readOnly=false)
    public UploadContactDTO uploadContacts(List<ContactPeopleInfo> contactPeopleInfos, String imsi) throws SystemException {
        ValidationUtil.checkParametersNotNull(imsi, contactPeopleInfos);
        checkUserInfo(imsi);
        convertContactPeopleListToCotactsList(contactPeopleInfos);
        mobiles = contactsDao.retrieveExistsMobiles(userInfo.getId(), mobiles);
        List<Contacts> newContacts = Lists.newArrayList(Iterators.filter(contactsList.iterator(), new Predicate<Contacts>() {
            @Override
            public boolean apply(Contacts input) {
                if (mobiles == null || (mobiles != null && mobiles.size() == 0)) {
                    return true;
                } else {
                    if (mobiles.contains(input.getPhoneNo())) {
                        mobiles.remove(input.getPhoneNo());
                        return false;
                    } else {
                        return true;
                    }
                }

            }
        }));
        Map<String, UserInfo> phoneNoAndImsiMap = null;
        if (mobiles != null && mobiles.size() > 0) {
            List<UserInfo> userInfos = userInfoDao.getUserInfosByPhoneNos(mobiles);
            phoneNoAndImsiMap = Maps.uniqueIndex(userInfos, businessFunctions.getUserPhoneNoFunction());
        }

        if (newContacts != null && newContacts.size() > 0) {
            if (phoneNoAndImsiMap != null) {
                UserInfo tempUserInfo = null;
                for (Contacts contact : newContacts) {
                    tempUserInfo = phoneNoAndImsiMap.get(contact.getPhoneNo());
                    if (tempUserInfo != null) {
                        contact.setSelfUserInfo(tempUserInfo);
                    }
                }
            }
            contactsDao.batchInsertContacts(newContacts);
        }
        return ObjectFactory.buildUploadContactDTO(Constants.SUCCESS_FLAG, "操作成功");
    }

    private void checkUserInfo(String imsi) {
        userInfoDao.getUserInfoByImsi(imsi);
        userInfo = userInfoDao.getUserInfoByImsi(imsi);
        if (null == userInfo) {
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "用户未注册");
            throw new CXServerBusinessException("用户未注册");
        }
    }

    private void convertContactPeopleListToCotactsList(List<ContactPeopleInfo> contactPeopleInfos) {
        contactsList = Lists.newArrayList();
        mobiles = Lists.newArrayList();
        for (ContactPeopleInfo temp : contactPeopleInfos) {
            List<String> phoneNoList = Lists.newArrayList(Splitter.on(",").split(temp.getPhoneNumList()));
            for (String phoneNo : phoneNoList) {
                Contacts contacts = new Contacts();
                contacts.setName(temp.getContactName());
                contacts.setUserInfo(userInfo);
                contacts.setPhoneNo(phoneNo);
                contactsList.add(contacts);
                mobiles.add(phoneNo);
            }
        }
        mobiles = Lists.newArrayList(Sets.newHashSet(mobiles).iterator());
    }

    @Override
    public String retrieveContactUserCXInfo(String imsi) throws SystemException {
        Preconditions.checkNotNull(imsi);
        checkUserInfo(imsi);
        List<Contacts> contactsList = contactsDao.getContactsByUserId(userInfo.getId());
        List<MGraphicStoreMode> mGraphicStoreModes = mgraphicStoreModeDao.getAllContactsMGraphicStoreModes(userInfo.getId());

        Map<String, UserCXInfo> mgraphicMap = Maps.newHashMap();
        for (MGraphicStoreMode tempMgraphicStoreMode : mGraphicStoreModes) {
            if (tempMgraphicStoreMode != null)
                mgraphicMap.put(tempMgraphicStoreMode.getUserInfo().getId(), tempMgraphicStoreMode.convertMGraphicStoreModeToUserCXInfo());
        }

        List<ContactPeopleInfo> contactPeopleInfosList = Lists.newArrayList();
        UserCXInfo tempUserCXInfo;
        ContactPeopleInfo contactPeopleInfo;
        for (Contacts tempContacts : contactsList) {
            if (tempContacts.getSelfUserInfo() != null) {
                tempUserCXInfo = mgraphicMap.get(tempContacts.getSelfUserInfo().getId());
                if (tempUserCXInfo != null) {
                    contactPeopleInfo = new ContactPeopleInfo();
                    contactPeopleInfo.setUserCXInfo(tempUserCXInfo);
                    contactPeopleInfo.setContactName(tempContacts.getName());
                    contactPeopleInfo.setPhoneNumList(tempContacts.getPhoneNo());
                    contactPeopleInfosList.add(contactPeopleInfo);
                }
            }
        }

        Result result = new Result();
        result.setContactPeopleInfos(contactPeopleInfosList);
        dealResult = StringUtil.generateXMLResultFromObject(result);
        return dealResult;
    }

    @Override
    public String copyContactUserCXInfo(String imsi, String cxInfoId) throws SystemException {
        List<String> resourceIdsList = Lists.newArrayList(cxInfoId);
        Map<String, CXInfo> cxInfosMap = restSender.getCXInfoMap(resourceIdsList);
        CXInfo cxinfo = cxInfosMap.get(cxInfoId);
        Result result = makeupResultObject(imsi, cxinfo);
        String xmlResult = userCXInfoManagerService.dealWithUserCXInfoAdding(result);
        return xmlResult;
    }

    private Result makeupResultObject(String imsi, CXInfo cxinfo) {
        UserCXInfo userCXInfo = new UserCXInfo();
        userCXInfo.setImsi(imsi);
        userCXInfo.setAuditPass(true);
        userCXInfo.setType(3);
        userCXInfo.setModeType(0);
        userCXInfo.setCxInfo(cxinfo);
        List<UserCXInfo> userCXInfos = Lists.newArrayList();
        userCXInfos.add(userCXInfo);
        Result result = new Result();
        result.setUserCXInfos(userCXInfos);
        return result;
    }

}
