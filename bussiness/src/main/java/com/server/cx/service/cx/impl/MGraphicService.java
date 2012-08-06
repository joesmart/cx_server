package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.google.common.base.Preconditions;
import com.server.cx.dao.cx.GraphicInfoDao;
import com.server.cx.dao.cx.HistoryMGraphicDao;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.HistoryMGraphic;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.service.cx.GraphicInfoService;
import com.server.cx.util.business.AuditStatus;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: yanjianzou
 * Date: 12-8-6
 * Time: 下午1:59
 * FileName:MGraphicService
 */
@Component
@Transactional
public class MGraphicService extends UserCheckService {
    @Autowired
    protected HistoryMGraphicDao historyMGraphicDao;
    @Autowired
    protected GraphicInfoDao graphicInfoDao;
    @Autowired
    protected GraphicInfoService graphicInfoService;
    protected GraphicInfo graphicInfo;

    protected void mGraphicIdMustBeNotExists(MGraphicDTO mGraphicDTO) {
        if(mGraphicDTO.getId() != null){
            throw new CXServerBusinessException("调用一般彩像添加接口错误,Id字段应该为空");
        }
    }

    protected void mGraphicIdMustBeExists(MGraphicDTO mGraphicDTO) {
        if(mGraphicDTO.getId() == null){
            throw new CXServerBusinessException("调用一般彩像添加接口错误,Id字段不应该为空");
        }
    }

    protected void checkParameters(String imsi, MGraphicDTO mGraphicDTO) {
        Preconditions.checkNotNull(imsi, "imsi编号为空");
        Preconditions.checkNotNull(mGraphicDTO,"传输对象为空");

        if(mGraphicDTO.getPhoneNos()!= null && mGraphicDTO.getPhoneNos().size()>0){
            throw new CXServerBusinessException("调用一般彩像添加接口错误,不应该设定号码");
        }
    }

    protected void historyPreviousUserCommonMGraphic(MGraphic mGraphic) {
        if(mGraphic == null) return;
        HistoryMGraphic historyMGraphic = new HistoryMGraphic();
        historyMGraphic.setGraphicInfo(mGraphic.getGraphicInfo());
        historyMGraphic.setUserInfo(userInfo);
        historyMGraphic.setName(mGraphic.getName());
        historyMGraphic.setSignature(mGraphic.getSignature());
        historyMGraphicDao.save(historyMGraphic);
    }

    protected String getGraphicInfoName(MGraphicDTO mGraphicDTO) {
        return StringUtils.isEmpty(mGraphicDTO.getName())? graphicInfo.getName():mGraphicDTO.getName();
    }

    protected void getAndCheckGraphicInfo(MGraphicDTO mGraphicDTO) {
        graphicInfo  = graphicInfoDao.findOne(mGraphicDTO.getGraphicInfoId());
        Preconditions.checkNotNull(graphicInfo, "图像对象不存在");
        if(!AuditStatus.PASSED.equals(graphicInfo.getAuditStatus())){
            throw new CXServerBusinessException("图像审核中,或审核未通过");
        }
    }
}
