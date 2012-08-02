package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
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
        final String baseHref = baseHostAddress + restURL + imsi + "/graphicInfos?categoryId=" + categoryId + "&offset=" + offset + "&limit=" + limit;
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.categoryTypeGraphicInfo(categoryId), pageRequest);
        List<GraphicInfo> graphicInfoList = page.getContent();

        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, graphicInfoList);

        DataPage dataPage = new DataPage();
        dataPage.setLimit(page.getSize());
        dataPage.setOffset(page.getNumber());
        dataPage.setTotal(page.getTotalPages());
        dataPage.setItems(graphicInfoItemList);
        dataPage.setHref(baseHref);
        if (offset > 0) {
            int previousOffset = offset - 1;
            dataPage.setPrevious(baseHostAddress + restURL + imsi + "/graphicInfos?categoryId=" + categoryId + "&offset=" + previousOffset + "&limit=" + limit);
        }
        if (offset + 1 < page.getTotalPages()) {
            int nextOffset = offset + 1;
            dataPage.setNext(baseHostAddress + restURL + imsi + "/graphicInfos?categoryId=" + categoryId + "&offset=" + nextOffset + "&limit=" + limit);
        }
        dataPage.setFirst(baseHostAddress + restURL + imsi + "/graphicInfos?categoryId=" + categoryId + "&offset=0&limit=" + limit);
        dataPage.setLast(baseHostAddress + restURL + imsi + "/graphicInfos?categoryId=" + categoryId + "&offset=" + (dataPage.getTotal() - 1) + "&limit=" + limit);
        return dataPage;
    }

    private List<DataItem> transformToGraphicItemList(final String imsi, List<GraphicInfo> graphicInfoList) {
        return Lists.transform(graphicInfoList,businessFunctions.graphicInfoTransformToGraphicInfoItem(imsi));
    }

    @Override
    public DataPage findHotGraphicInfoByDownloadNum(String imsi, Integer offset, Integer limit) {
        final String baseHref = baseHostAddress + restURL + imsi + "/graphicInfos?hot=true&offset=" + offset + "&limit=" + limit;
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "useCount","createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.hotCategoryTypeGraphicInfo(), pageRequest);

        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, page.getContent());
        DataPage dataPage = new DataPage();
        dataPage.setLimit(page.getSize());
        dataPage.setOffset(page.getNumber());
        dataPage.setTotal(page.getTotalPages());
        dataPage.setItems(graphicInfoItemList);
        dataPage.setHref(baseHref);
        if (offset > 0) {
            int previousOffset = offset - 1;
            dataPage.setPrevious(baseHostAddress + restURL + imsi + "/graphicInfos?hot=true&offset=" + previousOffset + "&limit=" + limit);
        }
        if (offset + 1 < page.getTotalPages()) {
            int nextOffset = offset + 1;
            dataPage.setNext(baseHostAddress + restURL + imsi + "/graphicInfos?hot=true&offset=" + nextOffset + "&limit=" + limit);
        }
        dataPage.setFirst(baseHostAddress + restURL + imsi + "/graphicInfos?hot=true&offset=0&limit=" + limit);
        dataPage.setLast(baseHostAddress + restURL + imsi + "/graphicInfos?hot=true&offset=" + (dataPage.getTotal() - 1) + "&limit=" + limit);
        return dataPage;
    }
    @Override
    public DataPage findRecommendGraphicAndPagination(String imsi, Integer offset, Integer limit){
        final String baseHref = baseHostAddress + restURL + imsi + "/graphicInfos?recommend=true&offset=" + offset + "&limit=" + limit;
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "useCount","createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.recommendGraphicInfo(), pageRequest);

        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, page.getContent());
        DataPage dataPage = new DataPage();
        dataPage.setLimit(page.getSize());
        dataPage.setOffset(page.getNumber());
        dataPage.setTotal(page.getTotalPages());
        dataPage.setItems(graphicInfoItemList);
        dataPage.setHref(baseHref);
        if (offset > 0) {
            int previousOffset = offset - 1;
            dataPage.setPrevious(baseHostAddress + restURL + imsi + "/graphicInfos?recommend=true&offset=" + previousOffset + "&limit=" + limit);
        }
        if (offset + 1 < page.getTotalPages()) {
            int nextOffset = offset + 1;
            dataPage.setNext(baseHostAddress + restURL + imsi + "/graphicInfos?recommend=true&offset=" + nextOffset + "&limit=" + limit);
        }
        dataPage.setFirst(baseHostAddress + restURL + imsi + "/graphicInfos?recommend=true&offset=0&limit=" + limit);
        dataPage.setLast(baseHostAddress + restURL + imsi + "/graphicInfos?recommend=true&offset=" + (dataPage.getTotal() - 1) + "&limit=" + limit);
        return dataPage;
    }
}
