package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.GraphicInfoDao;
import com.server.cx.dao.cx.spec.GraphicInfoSpecifications;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.service.cx.GraphicInfoService;
import com.server.cx.service.util.BusinessFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午5:35
 * FileName:GraphicInfoServiceImpl
 */
@Service("graphicInfoService")
@Transactional
public class GraphicInfoServiceImpl extends  BasicService implements GraphicInfoService {
    @Autowired
    private GraphicInfoDao graphicInfoDao;

    @Autowired
    private BusinessFunctions businessFunctions;
    @Override
    public DataPage findGraphicInfoDataPageByCategoryId(final String imsi, Long categoryId, Integer offset, Integer limit) {
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.categoryTypeGraphicInfo(categoryId), pageRequest);
        List<GraphicInfo> graphicInfoList = page.getContent();
        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, graphicInfoList);
        String queryCondition = "categoryId=" + categoryId;
        return generateDataPage(imsi, offset, limit, page, graphicInfoItemList, queryCondition);
    }


    private List<DataItem> transformToGraphicItemList(final String imsi, List<GraphicInfo> graphicInfoList) {
        return Lists.transform(graphicInfoList,businessFunctions.graphicInfoTransformToGraphicInfoItem(imsi));
    }

    @Override
    public DataPage findHotGraphicInfoByDownloadNum(String imsi, Integer offset, Integer limit) {
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "useCount","createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.hotCategoryTypeGraphicInfo(), pageRequest);
        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, page.getContent());
        String condition = "hot=true";
        return generateDataPage(imsi, offset, limit, page, graphicInfoItemList, condition);
    }



    @Override
    public DataPage findRecommendGraphicAndPagination(String imsi, Integer offset, Integer limit){
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "useCount","createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.recommendGraphicInfo(), pageRequest);
        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, page.getContent());
        String condition = "recommend=true";
        return generateDataPage(imsi, offset, limit, page, graphicInfoItemList, condition);
    }

    private DataPage generateDataPage(String imsi, Integer offset, Integer limit, Page page, List<DataItem> graphicInfoItemList, String condition) {
        DataPage dataPage = new DataPage();
        dataPage.setLimit(page.getSize());
        dataPage.setOffset(page.getNumber());
        dataPage.setTotal(page.getTotalPages());
        dataPage.setItems(graphicInfoItemList);
        dataPage.setHref(generatePageURL(imsi,offset,limit, condition));

        if (offset > 0) {
            int previousOffset = offset - 1;
            dataPage.setPrevious(generatePageURL(imsi, previousOffset, limit, condition));
        }
        if (offset + 1 < page.getTotalPages()) {
            int nextOffset = offset + 1;
            dataPage.setNext(generatePageURL(imsi, nextOffset, limit, condition));
        }
        dataPage.setFirst(generatePageURL(imsi,0,limit, condition));
        dataPage.setLast(generatePageURL(imsi, (dataPage.getTotal() - 1), limit, condition));
        return dataPage;
    }

    private String generatePageURL(String imsi, int offset, Integer limit, String queryCondition) {
        return baseHostAddress + restURL + imsi + "/graphicInfos?" + queryCondition + "&offset=" + offset + "&limit=" + limit;
    }

    @Override
    public boolean updateGraphicInfoUseCount(GraphicInfo graphicInfo) {
        Preconditions.checkNotNull(graphicInfo);
        Preconditions.checkNotNull(graphicInfo.getId());
        graphicInfo.setUseCount(graphicInfo.getUseCount() !=null?graphicInfo.getUseCount()+1:1);
        GraphicInfo afterSavedGraphicInfo = graphicInfoDao.save(graphicInfo);
        if(afterSavedGraphicInfo.getId()!= null){
            return  true;
        }else {
            return  false;
        }
    }
}
