package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.google.common.base.Preconditions;
import com.server.cx.dao.cx.GraphicInfoDao;
import com.server.cx.dao.cx.HistoryMGraphicDao;
import com.server.cx.dao.cx.UserCommonMGraphicDao;
import com.server.cx.dto.OperationResult;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.HistoryMGraphic;
import com.server.cx.entity.cx.UserCommonMGraphic;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.service.cx.UserCommonMGraphicService;
import com.server.cx.util.business.AuditStatus;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午2:59
 * FileName:UserCommonMGraphicServiceImpl
 */
@Service(value = "userCommonMGraphicService")
public class UserCommonMGraphicServiceImpl extends UserCheckService implements UserCommonMGraphicService {

    @Autowired
    private GraphicInfoDao graphicInfoDao;

    @Autowired
    private UserCommonMGraphicDao userCommonMGraphicDao;

    @Autowired
    private HistoryMGraphicDao historyMGraphicDao;
    private GraphicInfo graphicInfo;



    private void userCommonMGraphicIdMustBeNotExists(MGraphicDTO mGraphicDTO) {
        if(mGraphicDTO.getId() != null){
            throw new CXServerBusinessException("调用一般彩像添加接口错误,Id字段应该为空");
        }
    }

    private void userCommonMGraphicIdMustBeExists(MGraphicDTO mGraphicDTO) {
        if(mGraphicDTO.getId() == null){
            throw new CXServerBusinessException("调用一般彩像添加接口错误,Id字段应该为空");
        }
    }

    private void checkAndInitializeContext(String imsi, MGraphicDTO mGraphicDTO) {
        checkParameters(imsi, mGraphicDTO);
        checkAndSetUserInfoExists(imsi);
        getAndCheckGraphicInfo(mGraphicDTO);
    }

    private void createAndSaveNewUserCommonMGraphic(MGraphicDTO mGraphicDTO) {
        UserCommonMGraphic userCommonMGraphic = new UserCommonMGraphic();
        saveOrUpdateUserCommonMGraphic(mGraphicDTO, userCommonMGraphic);
    }

    private void saveOrUpdateUserCommonMGraphic(MGraphicDTO mGraphicDTO, UserCommonMGraphic userCommonMGraphic) {
        userCommonMGraphic.setActive(true);
        userCommonMGraphic.setName(getGraphicInfoName(mGraphicDTO));
        userCommonMGraphic.setGraphicInfo(graphicInfo);
        userCommonMGraphic.setUserInfo(userInfo);
        userCommonMGraphic.setSignature(mGraphicDTO.getSignature());
        userCommonMGraphicDao.save(userCommonMGraphic);
    }

    private void historyPreviousUserCommonMGraphic(MGraphicDTO mGraphicDTO) {
        UserCommonMGraphic previousUserCommonMGraphic = userCommonMGraphicDao.findByUserInfoAndActive(userInfo,true);
        HistoryMGraphic historyMGraphic = new HistoryMGraphic();
        historyMGraphic.setGraphicInfo(graphicInfo);
        historyMGraphic.setUserInfo(userInfo);
        historyMGraphic.setName(getGraphicInfoName(mGraphicDTO));
        historyMGraphic.setSignature(mGraphicDTO.getSignature());
        historyMGraphicDao.save(historyMGraphic);
        userCommonMGraphicDao.delete(previousUserCommonMGraphic);
    }

    private String getGraphicInfoName(MGraphicDTO mGraphicDTO) {
        return StringUtils.isEmpty(mGraphicDTO.getName())? graphicInfo.getName():mGraphicDTO.getName();
    }

    private void checkParameters(String imsi, MGraphicDTO mGraphicDTO) {
        Preconditions.checkNotNull(imsi, "imsi编号为空");
        Preconditions.checkNotNull(mGraphicDTO,"传输对象为空");

        if(mGraphicDTO.getPhoneNos()!= null && mGraphicDTO.getPhoneNos().size()>0){
            throw new CXServerBusinessException("调用一般彩像添加接口错误,不应该设定号码");
        }



    }

    private void getAndCheckGraphicInfo(MGraphicDTO mGraphicDTO) {
        graphicInfo  = graphicInfoDao.findOne(mGraphicDTO.getId());
        Preconditions.checkNotNull(graphicInfo, "图像对象不存在");
        if(!AuditStatus.PASSED.equals(graphicInfo.getAuditStatus())){
            throw new CXServerBusinessException("图像审核中,或审核未通过");
        }
    }

    @Override
    public OperationResult createUserCommonMGraphic(String imsi, MGraphicDTO mGraphicDTO) throws RuntimeException{
        checkAndInitializeContext(imsi, mGraphicDTO);
        userCommonMGraphicIdMustBeNotExists(mGraphicDTO);

        historyPreviousUserCommonMGraphic(mGraphicDTO);
        createAndSaveNewUserCommonMGraphic(mGraphicDTO);

        return new OperationResult("createUserCommonMGraphic","success");
    }

    @Override
    public OperationResult editUserCommonMGraphic(String imsi, MGraphicDTO mGraphicDTO) {
        checkAndInitializeContext(imsi, mGraphicDTO);
        userCommonMGraphicIdMustBeExists(mGraphicDTO);
        UserCommonMGraphic userCommonMGraphic = userCommonMGraphicDao.findOne(mGraphicDTO.getId());
        saveOrUpdateUserCommonMGraphic(mGraphicDTO,userCommonMGraphic);
        checkAndSetUserInfoExists(imsi);

        return new OperationResult("editUserCommonMGraphic","success");
    }

    @Override
    public OperationResult disableUserCommonMGraphic(String imsi, MGraphicDTO mGraphicDTO) {
        checkAndInitializeContext(imsi,mGraphicDTO);
        userCommonMGraphicIdMustBeExists(mGraphicDTO);
        historyPreviousUserCommonMGraphic(mGraphicDTO);
        return new OperationResult("disableUserCommonMGraphic","success");
    }
}
