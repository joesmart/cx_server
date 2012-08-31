package com.server.cx.service.cx.impl;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.cl.cx.platform.dto.MGraphicDTO;
import com.google.common.base.Preconditions;
import com.server.cx.dao.cx.GraphicInfoDao;
import com.server.cx.dao.cx.HistoryMGraphicDao;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.HistoryMGraphic;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.model.ActionBuilder;
import com.server.cx.service.cx.GraphicInfoService;
import com.server.cx.util.business.AuditStatus;
import com.server.cx.util.business.ValidationUtil;

/**
 * User: yanjianzou
 * Date: 12-8-6
 * Time: 下午1:59
 * FileName:MGraphicService
 */
@Component
@Transactional
public class CheckAndHistoryMGraphicService extends UserCheckService {
    @Autowired
    protected HistoryMGraphicDao historyMGraphicDao;
    @Autowired
    protected GraphicInfoDao graphicInfoDao;
    @Autowired
    protected GraphicInfoService graphicInfoService;
    protected GraphicInfo graphicInfo;
    
    protected String historyMGraphicId;

    @Autowired
    protected ActionBuilder actionBuilder;

    protected void checkMGraphicIdMustBeNotExists(MGraphicDTO mGraphicDTO) {
        if (mGraphicDTO.getId() != null) {
            throw new CXServerBusinessException("调用一般彩像添加接口错误,Id字段应该为空");
        }
    }

    protected void mGraphicIdMustBeExists(MGraphicDTO mGraphicDTO) {
        if (mGraphicDTO.getId() == null) {
            throw new CXServerBusinessException("调用一般彩像添加接口错误,Id字段不应该为空");
        }
    }

    protected void checkParameters(String imsi, MGraphicDTO mGraphicDTO) {
        Preconditions.checkNotNull(imsi, "imsi编号为空");
        Preconditions.checkNotNull(mGraphicDTO, "传输对象为空");
    }

    protected void checkMGraphicDTOPhoneNosMustBeNull(MGraphicDTO mGraphicDTO) {
        if (mGraphicDTO.getPhoneNos() != null && mGraphicDTO.getPhoneNos().size() > 0) {
            throw new CXServerBusinessException("调用一般彩像添加接口错误,不应该设定号码");
        }
    }

    protected void checkMGraphicDTOPhoneNosMustBeNotNull(MGraphicDTO mGraphicDTO) {
        if (mGraphicDTO.getPhoneNos() == null && mGraphicDTO.getPhoneNos().size() <= 0) {
            throw new CXServerBusinessException("调用一般彩像添加接口错误,应该设定号码");
        }
    }


    protected void historyPreviousUserCommonMGraphic(MGraphic mGraphic) {
        if (mGraphic == null) return;
        HistoryMGraphic historyMGraphic = new HistoryMGraphic();
        GraphicInfo graphicInfo = mGraphic.getGraphicResource().getGraphicInfo();
        historyMGraphic.setGraphicInfo(graphicInfo);
        historyMGraphic.setUserInfo(userInfo);
        historyMGraphic.setName(mGraphic.getName());
        historyMGraphic.setSignature(mGraphic.getSignature());
        historyMGraphicDao.save(historyMGraphic);
    }

    protected String judgeString(String name, String defaultName) {
        return StringUtils.isEmpty(name) ? defaultName : name;
    }

    protected void getAndCheckGraphicInfo(MGraphicDTO mGraphicDTO) {
        graphicInfo = graphicInfoDao.findOne(mGraphicDTO.getGraphicInfoId());
        Preconditions.checkNotNull(graphicInfo, "图像对象不存在");
        if (!AuditStatus.PASSED.equals(graphicInfo.getAuditStatus())) {
            throw new CXServerBusinessException("图像审核中,或审核未通过");
        }
    }

    protected void checkAndInitializeContext(String imsi, MGraphicDTO mGraphicDTO) {
        checkParameters(imsi, mGraphicDTO);
        checkAndSetUserInfoExists(imsi);
        getAndCheckGraphicInfo(mGraphicDTO);
    }

    protected  void checkAndInitializeUserInfo(String imsi){
        ValidationUtil.checkParametersNotNull(imsi);
        checkAndSetUserInfoExists(imsi);
    }

    protected void updateMGraphicNameAndSignature(MGraphicDTO mGraphicDTO, MGraphic mgraphic) {
        mgraphic.setName(judgeString(mGraphicDTO.getName(), graphicInfo.getName()));
        mgraphic.setSignature(judgeString(mGraphicDTO.getSignature(), graphicInfo.getSignature()));
    }

    protected void updateMGraphicNameAndSignatureInEditMode(MGraphicDTO mGraphicDTO,MGraphic mgraphic){
        mgraphic.setName(mGraphicDTO.getName());
        mgraphic.setSignature(mGraphicDTO.getSignature());
    }
    
    protected boolean existHistoryMGraphic(GraphicInfo graphicInfo, UserInfo userInfo) {
        List<HistoryMGraphic> historyMGraphics = historyMGraphicDao.findByGraphicInfoAndUserInfo(graphicInfo, userInfo);
        if(historyMGraphics == null || historyMGraphics.isEmpty()) {
            return false;
        }
        historyMGraphicId = historyMGraphics.get(0).getId();
        return true;
    }
}
