package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.ContactInfoDTO;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.ContactsDao;
import com.server.cx.dao.cx.UserCommonMGraphicDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.Contacts;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.exception.SystemException;
import com.server.cx.model.CXInfo;
import com.server.cx.model.Result;
import com.server.cx.model.UserCXInfo;
import com.server.cx.service.cx.ContactsService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.StringUtil;
import com.server.cx.util.business.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("contactsServcie")
@Transactional
@Scope(value = "request",proxyMode = ScopedProxyMode.INTERFACES)
public class ContactsServiceImpl implements ContactsService {
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private UserCommonMGraphicDao mgraphicDaoUserCommon;

    @Autowired
    private BusinessFunctions businessFunctions;

    private UserInfo userInfo;
    private List<Contacts> contactsList;
    private List<String> mobiles;

    @Override
    @Transactional(readOnly = false)
    public void uploadContacts(List<ContactInfoDTO> contactPeopleInfos, String imsi) throws SystemException {
        ValidationUtil.checkParametersNotNull(imsi, contactPeopleInfos);
        checkUserInfo(imsi);
        convertContactPeopleListToContactsList(contactPeopleInfos);
        mobiles = contactsDao.retrieveExistsMobiles(userInfo.getId(), mobiles);
        List<Contacts> newContacts = Lists.newArrayList(Iterators.filter(contactsList.iterator(),
            new Predicate<Contacts>() {
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
                UserInfo tempUserInfo;
                for (Contacts contact : newContacts) {
                    tempUserInfo = phoneNoAndImsiMap.get(contact.getPhoneNo());
                    if (tempUserInfo != null) {
                        contact.setSelfUserInfo(tempUserInfo);
                    }
                }
            }
            contactsDao.batchInsertContacts(newContacts);
        }
    }

    private void checkUserInfo(String imsi) {
        userInfoDao.getUserInfoByImsi(imsi);
        userInfo = userInfoDao.getUserInfoByImsi(imsi);
        if (null == userInfo) {
            StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "用户未注册");
            throw new CXServerBusinessException("用户未注册");
        }
    }

    private void convertContactPeopleListToContactsList(List<ContactInfoDTO> contactPeopleInfos) {
        Preconditions.checkState(contactPeopleInfos!=null&&contactPeopleInfos.size() >0,"上传联系人为空");
        contactsList = Lists.newArrayList();
        mobiles = Lists.newArrayList();

        for (ContactInfoDTO temp : contactPeopleInfos) {
            String phoneNo = temp.getPhoneNo();
            Contacts contacts = new Contacts();
            contacts.setName(temp.getContactName());
            contacts.setUserInfo(userInfo);
            contacts.setPhoneNo(phoneNo);
            contactsList.add(contacts);
            mobiles.add(phoneNo);
        }
        mobiles = Lists.newArrayList(Sets.newHashSet(mobiles).iterator());
    }
    
    @Override
    public List<Contacts> queryCXAppContactsByImsi(String imsi) throws SystemException {
        ValidationUtil.checkParametersNotNull(imsi);
        UserInfo userInfo = userInfoDao.findByImsi(imsi);
        List<Contacts> contacts = contactsDao.getContactsByUserIdAndSelfUserInfoNotNull(userInfo.getId());
        return contacts;
    }

    
    @Override
    public String retrieveContactUserCXInfo(String imsi) throws SystemException {
        //        Preconditions.checkNotNull(imsi);
        //        checkUserInfo(imsi);
        //        List<Contacts> contactsList = contactsDao.getContactsByUserId(userInfo.getId());
        //        List<UserCommonMGraphic> userCommonMGraphics = mgraphicDaoUserCommon.getAllContactsMGraphicStoreModes(userInfo.getId());
        //
        //        Map<String, UserCXInfo> mgraphicMap = Maps.newHashMap();
        //        for (UserCommonMGraphic tempMgraphicUserCommon : userCommonMGraphics) {
        //            if (tempMgraphicUserCommon != null)
        //                mgraphicMap.put(tempMgraphicUserCommon.getUserInfo().getId(), tempMgraphicUserCommon.convertMGraphicStoreModeToUserCXInfo());
        //        }
        //
        //        List<ContactPeopleInfoDTO> contactPeopleInfosList = Lists.newArrayList();
        //        UserCXInfo tempUserCXInfo;
        //        ContactPeopleInfoDTO contactPeopleInfo;
        //        for (Contacts tempContacts : contactsList) {
        //            if (tempContacts.getSelfUserInfo() != null) {
        //                tempUserCXInfo = mgraphicMap.get(tempContacts.getSelfUserInfo().getId());
        //                if (tempUserCXInfo != null) {
        //                    contactPeopleInfo = new ContactPeopleInfoDTO();
        //                    contactPeopleInfo.setUserCXInfo(tempUserCXInfo);
        //                    contactPeopleInfo.setContactName(tempContacts.getName());
        //                    contactPeopleInfo.setPhoneNumList(tempContacts.getPhoneNo());
        //                    contactPeopleInfosList.add(contactPeopleInfo);
        //                }
        //            }
        //        }
        //
        //        Result result = new Result();
        //        result.setContactPeopleInfos(contactPeopleInfosList);
        //        dealResult = StringUtil.generateXMLResultFromObject(result);
        //        return dealResult;
        return null;
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
